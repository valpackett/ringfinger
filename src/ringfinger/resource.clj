(ns ringfinger.resource
  (:use (ringfinger core db output util fields default-views)
        valip.core,
        lamina.core,
        [clojure.contrib.string :only [as-str, split, substring?]]))

(defn- qsformat [req]
  (let [fmt (get-in req [:query-params "format"])]
    (if fmt (str "?format=" fmt) nil)))

(defmacro respond [req status data outputs default]
  `(render
     (getoutput
       (first
         (filter identity
           (list
             (qsformat ~req)
             (get-in ~req [:headers "accept"]) ; it must be lowercase!
             ~default
           )))
     ~outputs)
   ~status ~data))

(defmacro qs [a] `(keyword (str "$" ~a)))

(defn- param-to-dboptions
  ([q] {(keyword (first q)) (apply param-to-dboptions (rest q))})
  ([k v] {(keyword k) v})
  ([k kk v] {(keyword k) {(qs kk) v}})
  ([k kk kkk v] {(keyword k) {(qs kk) {(qs kkk) v}}})
  ([k kk kkk kkkk v] {(keyword k) {(qs kk) {(qs kkk) {(qs kkkk) v}}}}))
; yeah, that's a mess, but a really fast mess!

(defn params-to-dboptions
  "Turns ring query-params into db options
  eg. {'query_field_ne' 3, 'sort_field' -1}
  becomes {:query {:field {:$ne 3}}, :sort {:field -1}}"
  ([qp] (if (empty? qp) nil (apply merge (map params-to-dboptions (keys qp) (vals qp)))))
  ([q v] (if (substring? "_" q) (param-to-dboptions (flatten (list (split #"_" q) (typeify v)))) nil)))

(defmacro call-flash
  "If a flash message is a string, returns it. If it's a callable,
  calls it with inst and returns the result"
  [flash inst]
  `(if (ifn? ~flash)
     (~flash ~inst)
     ~flash))

(defn resource
  "Creates a list of two routes (/url-prefix+collname and /url-prefix+collname/pk) for
  RESTful Create/Read/Update/Delete of entries in collname
  Accepted options:
   :db -- database (required!)
   :pk -- primary key (required!)
   :url-prefix -- a part of the URL before the collname, default is /
   :owner-field -- if you want entries to be owned by users, name of the field which should hold usernames
   :default-dboptions -- default database options (:query, :sort) for index pages
   :whitelist -- allowed extra fields (not required, not validated, automatically created, etc.)
   :views -- map of HTML views :index, :get and :not-found
   :flash -- map of flash messages :created, :updated, :deleted and :forbidden,
             can be either strings or callables expecting a single arg (the entry)
   :hooks -- map of hooks :data (on both create and update), :create, :update and :view, must be callables expecting
             the entry and returning it (with modifications you want). Hooks receive data with correct
             types, so eg. dates/times are org.joda.time.DateTime's and you can mess with them using clj-time
             Tip: compose hooks with ->
   :channels -- map of Lamina channels :create, :update and :delete for subscribing to these events"
  [collname options & fields]
  ; biggest let EVAR?
  (let [store (:db options)
        pk (:pk options)
        owner-field (:owner-field options)
        default-dboptions (:default-dboptions options {})
        coll (keyword collname)
        urlbase (str (:url-prefix options "/") collname)
        fieldhtml (html-from-fields fields)
        valds (validations-from-fields fields)
        fields-data-hook (data-hook-from-fields fields)
        fields-get-hook (get-hook-from-fields fields)
        req-fields (required-fields-of fields)
        blank-entry (zipmap req-fields (repeat ""))
        whitelist (let [w (concat (:whitelist options (list)) (keys fieldhtml))] ; cut off :csrftoken, don't allow users to store everything
                    (concat w (map #(keyword (as-str % "_slug")) w)))
        default-data {:collname collname :pk pk :fields fieldhtml}
        html-index (html-output (get-in options [:index :views] default-index) default-data)
        html-get   (html-output (get-in options [:get   :views] default-get) default-data)
        html-not-found (html-output (get-in options [:not-found :views] default-not-found) default-data)
        flash-created (:created (:flash options) #(str "Created: " (get % pk)))
        flash-updated (:updated (:flash options) #(str "Saved: "   (get % pk)))
        flash-deleted (:deleted (:flash options) #(str "Deleted: " (get % pk)))
        flash-forbidden (:forbidden (:flash options) "Forbidden.")
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
        post-hook #(-> % clear-form fields-data-hook user-data-hook user-post-hook)
        put-hook  #(-> % clear-form fields-data-hook user-data-hook user-put-hook)
        get-hook  #(-> % user-get-hook fields-get-hook)
        i-validate (fn [req data yep nope]
                     (let [ks (filter #(or (boolean (some #{%} req-fields)) (not (or (= (get data %) "") (nil? (get data %))))) (keys data))
                           result (apply validate (select-keys data ks)
                                         (filter #(boolean (some #{(first %)} ks)) valds))]
                       (if (= result nil) (yep) (nope result))))
        i-get-one  #(get-one store coll {:query {pk (typeify (:pk %))}})
        i-redirect (fn [req form flash status]
                     {:status  status
                      :headers {"Location" (str urlbase "/" (get form pk) (qsformat req))}
                      :flash   (call-flash flash form)
                      :body    ""})
        i-get-dboptions (if owner-field
                      #(assoc-in (or (params-to-dboptions (:query-params %)) default-dboptions) [:query owner-field] (get-in % [:user :username]))
                      #(or (params-to-dboptions (:query-params %)) default-dboptions))
        if-allowed  (if owner-field
                      ; [req entry yep]
                       #(if (= (get-in %1 [:user :username]) (get %2 owner-field))
                          (%3)
                          (if (from-browser? %1)
                            {:status  302
                             :headers {"Location" urlbase}
                             :flash   (call-flash flash-forbidden %2)
                             :body    ""}
                            {:status  403
                             :headers {"Content-Type" "text/plain"}
                             :body    "Forbidden"}))
                       #(%3))
        process-new  (if owner-field
                       ; [req form]
                       ; adds creator's username if there's an owner-field
                       #(assoc (post-hook %2) owner-field (get-in %1 [:user :username]))
                       #(post-hook %2))]
     (list
       (route urlbase
         {:get (fn [req matches]
                 (respond req 200
                          {:req  req
                           :data (map get-hook (get-many store coll (i-get-dboptions req)))}
                          {"html" html-index}
                          "html"))
          :post (fn [req matches]
                  (let [form  (merge blank-entry (keywordize (:form-params req)))
                        entry (typeize (process-new req form))]
                    (i-validate req form
                      (fn []
                        ((:create channels) entry)
                        (create store coll entry)
                        (i-redirect req entry flash-created (if (from-browser? req) 302 201)))
                      (fn [errors]
                        (respond req 400
                                 {:data (map get-hook (get-many store coll (i-get-dboptions req)))
                                  :newdata form
                                  :req req
                                  :errors errors}
                                 {"html" html-index}
                                 "html")))))})
       (route (str urlbase "/:pk")
         {:get (fn [req matches]
                 (let [entry (i-get-one matches)]
                   (if entry
                     (respond req 200
                              {:data (get-hook entry)
                               :req req}
                              {"html" html-get}
                              "html")
                     (respond req 404
                              {:req req}
                              {"html" html-not-found}
                              "html"))))
          :put (fn [req matches]
                 (let [form (keywordize (:form-params req))
                       orig (i-get-one matches)
                       diff (typeize (put-hook form))
                       merged (merge orig diff)]
                   (if-allowed req orig
                     (fn []
                       (i-validate req form
                         (fn []
                           ((:update channels) merged)
                           (update store coll orig diff)
                           (i-redirect req merged flash-updated 302))
                         (fn [errors]
                           (respond req 400
                                    {:data (merge orig form) ; with form! so users can correct errors
                                     :req req
                                     :errors errors}
                                    {"html" html-get}
                                    "html")))))))
          :delete (fn [req matches]
                    (let [entry (i-get-one matches)]
                      (if-allowed req entry
                        (fn []
                          ((:delete channels) entry)
                          (delete store coll entry)
                          {:status  302
                           :headers {"Location" urlbase}
                           :flash   (call-flash flash-deleted entry)
                           :body    nil}))))}))))

(defmacro defresource [nname options & fields]
  ; dirty magic
  (intern *ns* nname
    (let [nnname (str nname)]
      (eval `(resource ~nnname ~options ~@fields)))))
