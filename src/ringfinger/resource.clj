(ns ringfinger.resource
  "This module saves your time by writing all the Create/Read/Update/Delete
  boilerplate for you. Flash messages, validation, inserting example data,
  customization via hooks, actions and channels -- you name it, this module does it."
  (:use (ringfinger core db output util field-helpers default-views)
        valip.core,
        lamina.core))

(def regexps {:format #"\.?[a-zA-Z]*"})

(defmacro dotformat [matches] `(if-let [fmt# (:format ~matches)] fmt#))

(defmacro respond [req matches status headers data custom default]
  `(render
     (getoutput
       (first
         (filter identity
            [(get-in ~req [:headers "accept"]) ; must be lowercase!
             (:format ~matches)
             ~default])) ~custom)
   ~status ~headers ~data))

(defn resource
  "Creates a list of two routes (/url-prefix+collname.format and
  /url-prefix+collname/pk.format) for RESTful Create/Read/Update/Delete
  of entries in collname.
  Also, while in development environment, you can create example data using faker,
  like this: /url-prefix+collname.format/_insert_fakes?count=100 (the default count is 5).
  Accepted options:
   :db -- database (required!)
   :pk -- primary key (required!)
   :url-prefix -- a part of the URL before the collname, default is /
   :owner-field -- if you want entries to be owned by users, name of the field which should hold usernames
   :default-dboptions -- default database options (:query, :sort) for the index page
   :whitelist -- allowed extra fields (not required)
   :forbidden-methods -- a collection of methods to disallow (:index, :create, :read, :update, :delete)
   :views -- map of HTML views (:index, :get, :not-found)
   :flash -- map of flash messages (:created, :updated, :deleted, :forbidden),
             can be either strings or callables expecting a single arg (the entry)
   :hooks -- map of hooks (:data (on both create and update), :create, :update, :view), must be callables expecting
             the entry and returning it (with modifications you want). Hooks receive data with correct
             types, so eg. dates/times are org.joda.time.DateTime's and you can mess with them using clj-time
             Tip: compose hooks with comp
   :channels -- map of Lamina channels (:create, :update, :delete). Ringfinger will publish events
                to these channels so you could, for example, push updates to clients in real time,
                enqueue long-running jobs, index changes with a search engine, etc.
   :actions -- map of handlers for custom actions (callables accepting [req matches entry default-data])
               on resource entries, called by visiting /url-prefix+collname/pk?_action=action"
  [collname options & fields]
  ; biggest let EVAR?
  (let [db (:db options)
        pk (:pk options)
        owner-field (:owner-field options)
        default-dboptions (:default-dboptions options {})
        coll (keyword collname)
        urlbase (str (:url-prefix options "/") collname)
        fieldhtml (html-from-fields fields)
        valds (validations-from-fields fields)
        fakers (fakers-from-fields fields)
        fields-data-pre-hook  (data-pre-hook-from-fields  fields)
        fields-data-post-hook (data-post-hook-from-fields fields)
        fields-get-hook (get-hook-from-fields fields)
        req-fields (required-fields-of fields)
        blank-entry (zipmap req-fields (repeat ""))
        default-entry (defaults-from-fields fields)
        whitelist (let [w (concat (:whitelist options (list)) (keys fieldhtml))]
        ; cut off :csrftoken, don't allow users to store everything
                    (concat w (map #(keyword (str (name %) "_slug")) w)))
        actions (let [o (:actions options [])]
                  (zipmap (map name (keys o)) (vals o)))
        default-data (pack-to-map coll db collname pk fieldhtml actions urlbase)
        html-index (html-output (get-in options [:index :views] default-index) default-data)
        html-get   (html-output (get-in options [:get   :views] default-get) default-data)
        html-not-found (html-output (get-in options [:not-found :views] default-not-found) default-data)
        flash-created (:created (:flash options) #(str "Created: " (get % pk)))
        flash-updated (:updated (:flash options) #(str "Saved: "   (get % pk)))
        flash-deleted (:deleted (:flash options) #(str "Deleted: " (get % pk)))
        flash-forbidden (:forbidden (:flash options) "Forbidden.")
        forbidden (:forbidden-methods options [])
        s-channels [:create :update :delete]
        channels (zipmap s-channels
                   (map #(let [c (get-in options [:channels %])]
                           (if c (fn [msg] (enqueue c msg)) (fn [msg]))) s-channels))
        ; --- functions ---
        clear-form #(select-keys % whitelist)
        user-data-hook (get-in options [:hooks :data]    identity)
        user-post-hook (get-in options [:hooks :create]  identity)
        user-put-hook  (get-in options [:hooks :update]  identity)
        user-get-hook  (get-in options [:hooks :read]    identity)
        post-hook #(-> % clear-form fields-data-pre-hook user-data-hook user-post-hook fields-data-post-hook)
        put-hook  #(-> % clear-form fields-data-pre-hook user-data-hook user-put-hook  fields-data-post-hook)
        get-hook  #(-> % user-get-hook fields-get-hook)
        i-validate (fn [req data yep nope]
                     (let [ks (filter #(or (haz? req-fields %) (not (or (= (get data %) "") (nil? (get data %))))) (keys data))
                           result (apply validate (select-keys data ks)
                                         (filter #(haz? ks (first %)) valds))]
                       (if (= result nil) (yep) (nope result))))
        i-get-one  #(get-one db coll {:query {pk (typeify (:pk %))}})
        i-redirect (fn [req matches form flash status]
                     {:status  status
                      :headers {"Location" (str urlbase "/" (get form pk) (dotformat matches))}
                      :flash   (call-or-ret flash form)
                      :body    ""})
        i-get-dboptions (if owner-field
                      #(assoc-in (or (params-to-dboptions (:query-params %)) default-dboptions) [:query owner-field] (get-in % [:user :username]))
                      #(or (params-to-dboptions (:query-params %)) default-dboptions))
        i-respond-404 (fn [req matches]
                        (respond req matches 404 {}
                                 {:req req}
                                 {"html" html-not-found}
                                 "html"))
        if-allowed  (if owner-field
                      ; [req entry yep]
                       #(if (= (get-in %1 [:user :username]) (get %2 owner-field))
                          (%3)
                          (if (from-browser? %1)
                            {:status  302
                             :headers {"Location" urlbase}
                             :flash   (call-or-ret flash-forbidden %2)
                             :body    ""}
                            {:status  403
                             :headers {"Content-Type" "text/plain"}
                             :body    "Forbidden"}))
                       #(%3))
        process-new  (if owner-field
                       ; [req form]
                       ; adds creator's username if there's an owner-field
                       #(assoc (post-hook %2) owner-field (get-in %1 [:user :username]))
                       #(post-hook %2))
        if-not-forbidden #(if (not (haz? forbidden %1)) %2 method-na-handler)]
     (list
       (route (str urlbase ":format")
         {:get (if-not-forbidden :index
                  (fn [req matches]
                    (respond req matches 200 {"Link" (str "<" urlbase "/{" (name pk) "}.{format}>; rel=\"entry\"")}
                          {:req  req
                           :data (map get-hook (get-many db coll (i-get-dboptions req)))}
                          {"html" html-index}
                          "html")))
          :post (if-not-forbidden :create (fn [req matches]
                  (let [form  (keywordize (:form-params req))
                        entry (process-new req form)]
                    (i-validate req (merge blank-entry form)
                      (fn []
                        ((:create channels) entry)
                        (create db coll entry)
                        (i-redirect req matches entry flash-created (if (from-browser? req) 302 201)))
                      (fn [errors]
                        (respond req matches 400 {}
                                 {:data (map get-hook (get-many db coll (i-get-dboptions req)))
                                  :newdata form
                                  :req req
                                  :errors errors}
                                 {"html" html-index}
                                 "html"))))))} regexps)
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
         {:get (fn [req matches]
                 (if-let [entry (i-get-one matches)]
                   (if-let [action (get actions (get-in req [:query-params "_action"] ""))]
                     (action req matches entry default-data)
                     (if-not-forbidden :read
                       (respond req matches 200 {}
                                {:data (get-hook entry)
                                 :req req}
                                {"html" html-get}
                              "html")))
                 (i-respond-404 req matches)))
          :put (if-not-forbidden :update (fn [req matches]
                 (let [form (keywordize (:form-params req))
                       orig (i-get-one matches)
                       diff (put-hook (merge default-entry form))
                       merged (merge orig diff)]
                   (if-allowed req orig
                     (fn []
                       (i-validate req form
                         (fn []
                           ((:update channels) merged)
                           (update db coll orig diff)
                           (i-redirect req matches merged flash-updated 302))
                         (fn [errors]
                           (respond req matches 400 {}
                                    {:data (merge orig form) ; with form! so users can correct errors
                                     :req req
                                     :errors errors}
                                    {"html" html-get}
                                    "html"))))))))
          :delete (if-not-forbidden :delete (fn [req matches]
                    (if-let [entry (i-get-one matches)]
                      (if-allowed req entry
                        (fn []
                          ((:delete channels) entry)
                          (delete db coll entry)
                          {:status  302
                           :headers {"Location" urlbase}
                           :flash   (call-or-ret flash-deleted entry)
                           :body    nil}))
                      (i-respond-404 req matches))))} regexps))))

(defmacro defresource [nname options & fields]
  ; dirty magic
  (intern *ns* nname
    (let [nnname (str nname)]
      (eval `(resource ~nnname ~options ~@fields)))))
