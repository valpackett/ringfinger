(ns ringfinger.resource
  (:use (ringfinger core db output util default-views)
        valip.core,
        [clojure.contrib.string :only [as-str, split, substring?]]))

(defn- qsformat [req]
  (let [fmt (get (:query-params req) "format")]
    (if fmt
      (str "?format=" fmt)
      nil)))

(defmacro respond [req status data outputs default]
  `(render
     (getoutput
       (first
         (filter identity
           (list
             (qsformat ~req)
             (get (:headers ~req) "accept") ; it must be lowercase!
             ~default
           )))
     ~outputs)
   ~status ~data))

(defmacro qs [a] `(keyword (str "$" ~a)))

(defn- param-to-query
  ([q] {(keyword (first q)) (apply param-to-query (rest q))})
  ([k v] {(qs k) v})
  ([k kk v] {(qs k) {(qs kk) v}})) ; that's for speed
; db doesnt't handle deeper stuff anyway

(defn params-to-query
  ([qp] (if (empty? qp) nil (apply merge (map params-to-query (keys qp) (vals qp)))))
  ([q v] (if (substring? "_" q) (param-to-query (flatten (list (split #"_" q) (typeify v)))) nil)))

(defmacro call-flash [flash inst]
  `(if (ifn? ~flash)
     (~flash ~inst)
     ~flash))

(defn resource [collname options & validations]
  ; biggest let EVAR?
  (let [store (:store options)
        pk (:pk options)
        owner-field (:owner-field options)
        default-query (:default-query options {})
        coll (keyword collname) ; TODO: custom prefix
        fields (fields-from-validations validations)
        valds (map #(assoc % 1 (:clj (second %))) validations) ; only clojure validators, no html
        whitelist (concat (:whitelist options (list)) (keys fields)) ; cut off :csrftoken, don't allow users to store everything
        default-data {:collname collname :pk pk :fields fields}
        html-index (html-output (:index (:views options) default-index) default-data)
        html-get   (html-output (:get   (:views options) default-get) default-data)
        html-not-found (html-output (:not-found (:views options) default-not-found) default-data)
        flash-created (:created (:flash options) #(str "Created: " (get % pk)))
        flash-updated (:updated (:flash options) #(str "Saved: "   (get % pk)))
        flash-deleted (:deleted (:flash options) #(str "Deleted: " (get % pk)))
        flash-forbidden (:forbidden (:flash options) "Forbidden.")
        ; --- functions ---
        clear-form #(select-keys (typeize %) whitelist)
        user-data-hook (get-in options [:hooks :data] identity)
        user-post-hook (get-in options [:hooks :post] identity)
        user-put-hook  (get-in options [:hooks :put]  identity)
        data-hook #(-> % clear-form user-data-hook)
        post-hook #(-> % data-hook  user-post-hook)
        put-hook  #(-> % data-hook  user-put-hook)
        i-validate (fn [req data yep nope] (let [result (apply validate data valds)]
                      (if (= result nil) (yep) (nope result))))
        i-get-one  #(get-one store coll {pk (typeify (:pk %))})
        i-redirect (fn [req form flash status]
                     {:status  status
                      :headers {"Location" (str "/" collname "/" (get form pk) (qsformat req))}
                      :flash   (call-flash flash form)
                      :body    nil})
        i-get-query (if owner-field
                      #(assoc (or (params-to-query (:query-params %)) default-query) owner-field (get-in % [:user :username]))
                      #(or (params-to-query (:query-params %)) default-query))
        if-allowed  (if owner-field
                      ; [req entry yep]
                       #(if (= (get-in %1 [:user :username]) (get %2 owner-field))
                          (%3)
                          (if (from-browser? %1)
                            {:status  302
                             :headers {"Location" (str "/" collname)}
                             :flash   (call-flash flash-forbidden %2)
                             :body    nil}
                            {:status  403
                             :headers {"Content-Type" "text/plain"}
                             :body    "Forbidden"}))
                       #(%3))
        process-new  (if owner-field
                       ; [req form]
                       #(assoc (post-hook %2) owner-field (get-in %1 [:user :username]))
                       #(post-hook %2))]
     (list
       (route (str "/" collname)
         {:get (fn [req matches]
                 (respond req 200
                          {:flash (:flash req)
                           :csrf-token (:csrf-token req)
                           :data  (get-many store coll (i-get-query req))}
                          {"html" html-index}
                          "html"))
          :post (fn [req matches]
                  (let [form (keywordize (:form-params req))]
                    (i-validate req form
                      (fn []
                        (create store coll (process-new req form))
                        (i-redirect req form flash-created 201))
                      (fn [errors]
                        (respond req 400
                                 {:data   (get-many store coll (i-get-query req))
                                  :csrf-token (:csrf-token req)
                                  :flash  (:flash req)
                                  :errors errors}
                                 {"html" html-index}
                                 "html")))))})
       (route (str "/" collname "/:pk")
         {:get (fn [req matches]
                 (let [entry (i-get-one matches)]
                   (if entry
                     (respond req 200
                              {:data  entry
                               :csrf-token (:csrf-token req)
                               :flash (:flash req)}
                              {"html" html-get}
                              "html")
                     (respond req 404
                              {:flash (:flash req)}
                              {"html" html-not-found}
                              "html"))))
          :put (fn [req matches]
                 (let [form (keywordize (:form-params req))
                       entry (i-get-one matches)
                       updated-entry (merge entry form)]
                   (if-allowed req entry
                     (fn []
                       (i-validate req updated-entry
                         (fn []
                           (update store coll entry (put-hook form))
                           (i-redirect req form flash-updated 302))
                         (fn [errors]
                           (respond req 400
                                    {:data   updated-entry
                                     :flash  (:flash req)
                                     :csrf-token (:csrf-token req)
                                     :errors errors}
                                    {"html" html-get}
                                    "html")))))))
          :delete (fn [req matches]
                    (let [entry (i-get-one matches)]
                      (if-allowed req entry
                        (fn []
                          (delete store coll entry)
                          {:status  302
                           :headers {"Location" (str "/" collname)}
                           :flash   (call-flash flash-deleted entry)
                           :body    nil}))))}))))

(defmacro defresource [nname options & validations]
  ; dirty magic
  (intern *ns* nname
    (let [nnname (str nname)]
      (eval `(resource ~nnname ~options ~@validations)))))
