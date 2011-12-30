(ns restfinger.core
  "This module saves your time by writing all the Create/Read/Update/Delete
  boilerplate for you. Flash messages, validation, inserting example data,
  customization via hooks, actions and channels -- you name it, this module does it."
  (:use (restfinger output default-views),
        formfinger.field-helpers,
        corefinger.core,
        basefinger.core,
        toolfinger,
        valip.core,
      lamina.core))

(def regexps {:format #"\.?[a-zA-Z]*"})

(defn resource
  "Creates a list of two routes (/url-prefix+collname.format and
  /url-prefix+collname/pk.format) for RESTful Create/Read/Update/Delete
  of entries in collname.
  Also, while in development environment, you can create example data using faker,
  like this: /url-prefix+collname.format/_create_fakes?count=100 (the default count is 5).
  Accepted options:
   :db -- database (required!)
   :pk -- primary key (required!)
   :url-prefix -- a part of the URL before the collname, default is /
   :owner-field -- if you want entries to be owned by users, name of the field which should hold user ids
   :owner-only -- if you want entries to be private
   :default-dboptions -- default database options (:query, :sort) for the index page
   :whitelist -- allowed extra fields (not required)
   :forbidden-methods -- a collection of methods to disallow (:index, :create, :read, :update, :delete)
   :public-methods -- if :owner-field is specified, a collection of methods
                      to allow for everyone, not just the owner. default is [:read]
   :default-format -- 'html' by default, change to 'json' if you don't want html at all
   :views -- map of HTML views (:index, :get, :not-found)
   :flash -- map of flash messages (:created, :updated, :deleted, :forbidden),
             can be either strings or callables expecting a single arg (the entry)
   :middleware -- map of extended middleware (:all, :index, :create, :read, :update, :delete)
   :hooks -- map of hooks (:data (on both create and update), :create, :update, :view), must be callables expecting
             the entry and returning it (with modifications you want). Hooks receive data with correct
             types, so eg. dates/times are org.joda.time.DateTime's and you can mess with them using clj-time
             Tip: compose hooks with comp
   :channels -- map of Lamina channels (:create, :update, :delete). Ringfinger will publish events
                to these channels so you could, for example, push updates to clients in real time,
                enqueue long-running jobs, index changes with a search engine, etc.
   :actions -- map of handlers for custom actions (callables accepting [req matches entry default-data])
               on resource entries, called by visiting /url-prefix+collname/pk?_action=action
   :per-page-default -- default amount of entried per page, set to nil to disable pagination"
  [collname {:keys [db pk owner-field default-dboptions url-prefix whitelist default-format
                    middleware actions views forbidden-methods public-methods flash channels hooks
                    per-page-default owner-only]
             :or {db nil pk nil owner-field nil
                  default-dboptions {} url-prefix "/"
                  whitelist nil actions []
                  default-format "html"
                  views {:index default-index
                         :get default-get
                         :not-found default-not-found}
                  forbidden-methods []
                  public-methods [:read]
                  flash nil
                  middleware {} channels {} hooks {}
                  per-page-default 20
                  owner-only false
                  }} & fields]
  {:pre [(not (nil? db))
         (not (nil? pk))]}
  (let [coll (keyword collname)
        urlbase (str url-prefix collname)
        fieldhtml (html-from-fields fields)
        valds (validations-from-fields fields)
        fakers (fakers-from-fields fields)
        req-fields (required-fields-of fields)
        blank-entry (zipmap req-fields (repeat ""))
        default-entry (defaults-from-fields fields)
        whitelist (let [w (concat (or whitelist (filter identity (list owner-field))) (keys fieldhtml))]
        ; cut off :csrftoken, don't allow users to store everything
                    (concat w (map #(keyword (str (name %) "_slug")) w)))
        actions (zipmap (map name (keys actions)) (vals actions))
        default-data (pack-to-map coll db collname pk fieldhtml actions urlbase)
        html-index (html-output (:index views) default-data)
        html-get   (html-output (:get   views) default-data)
        html-not-found (html-output (:not-found views) default-data)
        s-channels [:create :update :delete]
        channels (zipmap s-channels
                   (map #(let [c (get channels %)]
                           (if c
                             (do (assert (= (class c) lamina.core.channel.Channel))
                                 (fn [msg] (enqueue c msg)))
                             (fn [msg]))) s-channels))
        flash (or flash {:created #(str "Created: " (get % pk))
                         :updated #(str "Saved: "   (get % pk))
                         :deleted #(str "Deleted: " (get % pk))
                         :forbidden "Forbidden."})
        hooks (merge (zipmap [:data :create :update :read] (repeat identity)) hooks)
        middleware (merge (zipmap [:all :index :create :read :update :delete]
                                  (repeat #(fn [req matches] (% req matches)))) middleware)
        ; --- functions ---
        clear-form #(select-keys % whitelist)
        fields-data-pre-hook  (data-pre-hook-from-fields  fields)
        fields-data-post-hook (data-post-hook-from-fields fields)
        post-hook (comp fields-data-post-hook (:data hooks) (:create hooks) fields-data-pre-hook clear-form)
        put-hook  (comp fields-data-post-hook (:data hooks) (:update hooks) fields-data-pre-hook clear-form)
        get-hook  (comp (get-hook-from-fields fields) (:read hooks))
        i-redirect (fn [req matches form flash status]
                     {:status  status
                      :headers {"Location" (str urlbase "/" (get form pk) (dotformat matches))}
                      :flash   (call-or-ret flash form)
                      :body    ""})
        i-get-dboptions (let [base (fn [req] (or (params-to-dboptions (:query-params req)) default-dboptions))
                              ownd (if (and owner-field owner-only)
                                     (fn [req] (assoc-in (base req) [:query owner-field] (get-in req [:user :id])))
                                     base)
                              pagd (if per-page-default
                                     (fn
                                       ([req]
                                         (if-let [per-page (:per-page req)]
                                           (-> (ownd req)
                                               (assoc :limit per-page)
                                               (assoc :skip (* per-page (- (:page req) 1))))
                                           (ownd req)))
                                       ([req skip-limit] (ownd req)))
                                     ownd)]
                          pagd)
        respond (fn [req matches status data view]
                  (-> (filter identity
                              [(get-in req [:headers "accept"])
                               (:format matches)
                               default-format])
                      first
                      (getoutput {"html" view})
                      (render status data)))
        if-allowed  (if owner-field
                      (fn [req entry method yep]
                        (if (or (haz? public-methods method)
                                (= (get-in req [:user :id]) (get entry owner-field)))
                          (yep)
                          (if (from-browser? req)
                            {:status  302
                             :headers {"Location" urlbase}
                             :flash   (call-or-ret (:forbidden flash) entry)
                             :body    ""}
                            {:status  403
                             :headers {"Content-Type" "text/plain"}
                             :body    "Forbidden"})))
                       (fn [req entry method yep] (yep)))
        make-processor (fn [hook]
                         (if owner-field
                         ; [req form]
                         ; adds creator's id if there's an owner-field
                           #(assoc (hook %2) owner-field (get-in %1 [:user :id]))
                           #(hook %2)))
        process-new (make-processor post-hook)
        process-replace (make-processor put-hook)
        parse-int #(if (string? %) (Integer/parseInt %) nil)
        ewrap-pagination (if per-page-default
                           #(fn [req matches]
                              (let [fmt (fn [rel page] (format "<%s%s>; rel=\"%s\"" (:uri req) (alter-query-params req {"page" page}) rel))
                                    page (or (parse-int (get (:query-params req) "page")) 1)
                                    per-page (or (parse-int (get (:query-params req) "per_page")) per-page-default)
                                    last-page (int (Math/ceil (/ (get-count db coll (i-get-dboptions req true)) per-page)))]
                                (assoc-in
                                  (% (merge req (pack-to-map page per-page)) matches)
                                  [:headers "Link"]
                                  (apply str (interpose ", " (concat
                                    (if (= page 1) [] [(fmt "first" 1) (fmt "prev" (- page 1))])
                                    (if (= page last-page) [] [(fmt "next" (+ page 1)) (fmt "last" last-page)])))))))
                           #(fn [req matches] (% req matches)))
        ewrap-forbidden #(if (not (haz? forbidden-methods %2)) %1 method-na-handler)
        ewrap-instance  #(fn [req matches]
                           (if-let [inst (get-one db coll {:query {pk (typeify (:pk matches))}})]
                             (if-allowed req inst
                                         (let [method (:request-method req)]
                                           (case method :get :read :put :update method))
                                         (fn [] (% (assoc req :inst true) matches inst)))
                             (respond req matches 404 {:req req} html-not-found)))
        ewrap-form (fn [handler use-blank]
                     (fn [req matches]
                       (let [form (keywordize (:form-params req))
                             data (if use-blank (merge blank-entry form) form)
                             ks (filter #(or (haz? req-fields %) (not (or (= (get data %) "") (nil? (get data %))))) (keys data))]
                         (handler req matches form
                            (apply validate (select-keys data ks) (filter #(haz? ks (first %)) valds))))))
        ]
     ; FIXME: on Clojure 1.3 we can add a custom message
     (with-out-str ; asserts don't work without doing something such as printing!!
       (prn (map #(assert (haz? (arities %) 1)) (vals hooks)))
       (prn (map #(assert (haz? (arities (% (fn [r m] {}))) 2)) (vals middleware)))
       (prn (map #(assert (haz? (arities %) 4)) (vals actions))))
     (merge (pack-to-map db pk coll channels urlbase) {:routes (list
       (route (str urlbase ":format")
         {:get (-> (fn [req matches]
                     (respond req matches 200
                        {:req  req
                         :data (map get-hook (get-many db coll (i-get-dboptions req)))}
                        html-index))
                   ((:index middleware))
                   ((:all middleware))
                   (ewrap-pagination)
                   (ewrap-forbidden :index))
          :post (-> (fn [req matches form errors]
                      (if errors
                        (respond req matches 400
                           {:data (map get-hook (get-many db coll (i-get-dboptions req)))
                            :newdata form :req req :errors errors} html-index)
                        (let [entry (process-new req form)]
                          ((:create channels) entry)
                          (create db coll entry)
                          (i-redirect req matches entry (:created flash) (if (from-browser? req) 302 201)))))
                    (ewrap-form true)
                    ((:create middleware))
                    ((:all middleware))
                    (ewrap-forbidden :create))
          } regexps)
       (if-env "development"
         (route (str urlbase "/_create_fakes")
           {:get (fn [req matches]
                   (create-many db coll
                     (take (Integer/parseInt (get-in req [:query-params "count"] "5"))
                           (repeatedly (fn [] (process-new req (zipmap (keys fakers) (map #(last (take (rand-int 1000) %)) (vals fakers))))))))
                   {:status  302
                    :headers {"Location" (str urlbase (dotformat matches))}
                    :body    nil})})
         nil)
       (route (str urlbase "/:pk:format")
         (-> (fn [req matches inst]
               ((method-dispatch-handler
                 {:get (-> (fn [req matches]
                             (if-let [action (get actions (get-in req [:query-params "_action"] ""))]
                               (action req matches inst default-data)
                               (respond req matches 200
                                  {:data (get-hook inst)
                                   :req req}
                                  html-get)))
                           ((:read middleware))
                           (ewrap-forbidden :read))
                  :put (-> (fn [req matches form errors]
                             (if errors
                               (respond req matches 400
                                  {:data form :req req :errors errors} html-get)
                               (let [form (process-replace req form)]
                                 ((:update channels) form)
                                 (update db coll inst form true)
                                 (i-redirect req matches form (:updated flash) 302))))
                             (ewrap-form false)
                             ((:update middleware))
                             (ewrap-forbidden :update))
                  :patch (-> (fn [req matches form errors]
                                (if errors
                                  (respond req matches 400
                                     {:data (merge inst form) :req req :errors errors} html-get)
                                  (let [diff (put-hook (merge default-entry form))
                                        merged (merge inst diff)]
                                    ((:update channels) merged)
                                    (update db coll inst diff false)
                                    (i-redirect req matches merged (:updated flash) 302))))
                                (ewrap-form false)
                                ((:update middleware))
                                (ewrap-forbidden :update))
                   :delete (-> (fn [req matches]
                                 ((:delete channels) inst)
                                 (delete db coll inst)
                                 {:status  302
                                  :headers {"Location" urlbase}
                                  :flash   (call-or-ret (:deleted flash) inst)
                                  :body    nil})
                               ((:delete middleware))
                               (ewrap-forbidden :delete))
                   }) req matches))
               ewrap-instance
               ((:all middleware))) regexps))})))

(defmacro defresource [nname options & fields]
  ; dirty magic
  (intern *ns* nname
    (let [nnname (str nname)]
      (eval `(resource ~nnname ~options ~@fields)))))
