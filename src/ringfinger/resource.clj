(ns ringfinger.resource
  (:use ringfinger.core, ringfinger.db, ringfinger.output, ringfinger.util
        ring.util.response,
        valip.core, valip.predicates, ; FIXME: don't need predicates here?
        [clojure.contrib.string :only [as-str, split, substring?]]
        [clojure.contrib.seq    :only [includes?]]))

(defmacro normalize [a]
  `(zipmap (map (fn [b#] (keyword b#)) (keys ~a)) (vals ~a)))

; oh snap
(defmacro typeize [a]
  `(zipmap (keys ~a) (map typeify (vals ~a))))

(defn- qsformat [req]
  (let [fmt (first (select-keys (:query-params req) ["format"]))]
    (if fmt
      (str "?" (first fmt) "=" (get fmt 1))
      nil)))

(defmacro respond [req status data]
  `(render (getoutput (first (filter identity (list
                                                (qsformat ~req)
                                                (get (:headers ~req) "accept"))))) ~status ~data))

(defmacro qs [a] `(keyword (str "$" ~a)))

(defn- param-to-query
  ([q] {(keyword (first q)) (apply param-to-query (rest q))})
  ([k v] {(qs k) v})
  ([k kk v] {(qs k) {(qs kk) v}})) ; that's for speed
; db doesnt't handle deeper stuff anyway

(defn params-to-query
  ([qp] (apply merge (map params-to-query (keys qp) (vals qp))))
  ([q v] (if (substring? "_" q) (param-to-query (flatten (list (split #"_" q) (typeify v)))) nil)))

(defn defresource [coll options & validations]
  (let [store (:store options)
        pk (:pk options)
        collname (as-str coll)
        fields (map first validations)
        i_validate (fn [req data yep] (let [result (apply validate data validations)] (if (= result nil) (yep) (respond req 400 result))))
        i_get_one  (fn [matches] (get_one store coll {pk (:pk matches)}))
        i_redirect (fn [req form] (redirect (str "/" collname "/" (get form pk) (qsformat req))))]
  (defroute (str "/" collname)
    {:get (fn [req matches]
            (respond req 200 (get_many store coll (params-to-query (:query-params req)))))
     :post (fn [req matches]
             (let [form (normalize (:form-params req))]
               (i_validate req form
                 (fn []
                   (create store coll (typeize form))
                   (i_redirect req form)))))})
  (defroute (str "/" collname "/:pk")
    {:get (fn [req matches]
             (respond req 200 (i_get_one matches)))
     :put (fn [req matches]
            (let [form (normalize (:form-params req))
                  entry (i_get_one matches)]
              (i_validate req (merge entry form)
                (fn []
                  (update store coll entry (typeize form))
                  (i_redirect req form)))))
     :delete (fn [req matches]
               (delete store coll (i_get_one matches))
               (respond req 200 {}))})))
