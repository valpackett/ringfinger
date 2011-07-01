(ns ringfinger.resource
  (:use (ringfinger core db output util default-views)
        valip.core,
        [clojure.contrib.string :only [as-str, split, substring?]]))

(defn- qsformat [req]
  (let [fmt (get (:query-params req) "format")]
    (if fmt
      (str "?format=" fmt)
      nil)))

(defmacro respond [req status data html]
  `(render (getoutput (first
      (filter identity (list
                          (qsformat ~req)
                          (get (:headers ~req) "accept") ; it must be lowercase!
                         ))) ~html) ~status ~data))

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
        coll (keyword collname)
        default-query (:default-query options {})
        fields (fields-from-validations validations)
        valds (map #(assoc % 1 (:clj (second %))) validations)
        default-data {:collname collname :pk pk :fields fields}
        html-index (html-output (:index (:views options) default-index) default-data)
        html-get   (html-output (:get   (:views options) default-get) default-data)
        html-not-found (html-output (:not-found (:views options) default-not-found) default-data)
        flash-created (:created (:flash options) #(str "Created: " (get % pk)))
        flash-updated (:updated (:flash options) #(str "Saved: "   (get % pk)))
        flash-deleted (:deleted (:flash options) #(str "Deleted: " (get % pk)))
        i-validate (fn [req data yep nope] (let [result (apply validate data valds)]
                      (if (= result nil) (yep) (nope result))))
        i-get-one  (fn [matches] (get-one store coll {pk (typeify (:pk matches))}))
        i-redirect (fn [req form flash]
                     {:status  302
                      :headers {"Location" (str "/" collname "/" (get form pk) (qsformat req))}
                      :flash   (call-flash flash form)
                      :body    nil})]
     (list
       (route (str "/" collname)
         {:get (fn [req matches]
                 (respond req 200
                          {:flash (:flash req)
                           :data  (get-many store coll (or (params-to-query (:query-params req)) default-query))}
                          {"html" html-index}))
          :post (fn [req matches]
                  (let [form (keywordize (:form-params req))]
                    (i-validate req form
                      (fn []
                        (create store coll (typeize form))
                        (i-redirect req form flash-created))
                      (fn [errors]
                        (respond req 400
                                 {:data   (get-many store coll (or (params-to-query (:query-params req)) default-query))
                                  :flash  (:flash req)
                                  :errors errors}
                                 {"html" html-index})))))})
       (route (str "/" collname "/:pk")
         {:get (fn [req matches]
                 (let [entry (i-get-one matches)]
                   (if entry
                     (respond req 200
                              {:data  entry
                               :flash (:flash req)}
                              {"html" html-get})
                     (respond req 404
                              {:flash (:flash req)}
                              {"html" html-not-found}))))
          :put (fn [req matches]
                 (let [form (keywordize (:form-params req))
                       entry (i-get-one matches)
                       updated-entry (merge entry form)]
                   (i-validate req updated-entry
                     (fn []
                       (update store coll entry (typeize form))
                       (i-redirect req form flash-updated))
                     (fn [errors]
                       (respond req 400
                                {:data   updated-entry
                                 :flash  (:flash req)
                                 :errors errors}
                                {"html" html-get})))))
          :delete (fn [req matches]
                    (let [inst (i-get-one matches)]
                      (delete store coll inst)
                      {:status  302
                       :headers {"Location" (str "/" collname)}
                       :flash   (call-flash flash-deleted inst)
                       :body    nil}))}))))

(defmacro defresource [nname options & validations]
  ; dirty magic
  (intern *ns* nname
    (let [nnname (str nname)]
      (eval `(resource ~nnname ~options ~@validations)))))
