(ns ringfinger.resource
  (:use (ringfinger core db output util default-views)
        ring.util.response,
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

(defn resource [collname options & validations]
  (let [store (:store options)
        pk (:pk options)
        coll (keyword collname)
        default-query (or (:default-query options) {})
        fields (let [v (group-by first validations)]
                 (zipmap (keys v) (map (fn [a] (apply merge (map #(:html (second %)) a))) (vals v))))
        valds (map #(assoc % 1 (:clj (second %))) validations)
        default-data {:collname collname :pk pk :fields fields}
        html-index (html-output (or (:index (:views options)) default-index) default-data)
        html-get   (html-output (or (:get   (:views options)) default-get) default-data)
        html-not-found (html-output (or (:not-found (:views options)) default-not-found) default-data)
        i-validate (fn [req data yep nope] (let [result (apply validate data valds)]
                      (if (= result nil) (yep) (nope result))))
        i-get-one  (fn [matches] (get-one store coll {pk (typeify (:pk matches))}))
        i-redirect (fn [req form] (redirect (str "/" collname "/" (get form pk) (qsformat req))))]
     (list
       (route (str "/" collname)
         {:get (fn [req matches]
                 (respond req 200 {:data (get-many store coll (or (params-to-query (:query-params req)) default-query))} html-index))
          :post (fn [req matches]
                  (let [form (keywordize (:form-params req))]
                    (i-validate req form
                      (fn []
                        (create store coll (typeize form))
                        (i-redirect req form))
                      (fn [errors]
                        (respond req 400 {:data (get-many store coll (or (params-to-query (:query-params req)) default-query))
                                          :errors errors} html-index)))))})
       (route (str "/" collname "/:pk")
         {:get (fn [req matches]
                 (let [entry (i-get-one matches)]
                   (if entry
                     (respond req 200 {:data entry} html-get)
                     (respond req 404 {} html-not-found))))
          :put (fn [req matches]
                 (let [form (keywordize (:form-params req))
                       entry (i-get-one matches)
                       updated-entry (merge entry form)]
                   (i-validate req updated-entry
                     (fn []
                       (update store coll entry (typeize form))
                       (i-redirect req form))
                     (fn [errors]
                       (respond req 400 {:data updated-entry :errors errors} html-get)))))
          :delete (fn [req matches]
                    (delete store coll (i-get-one matches))
                    (redirect (str "/" collname)))}))))

(defn defresource [collname options & validations]
  (intern *ns* (symbol collname) (apply resource collname options validations)))
