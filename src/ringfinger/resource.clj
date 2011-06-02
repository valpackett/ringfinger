(ns ringfinger.resource
  (:use ringfinger.core, ringfinger.db, ringfinger.output
        ring.util.response,
        [clojure.contrib.string :only [as-str]]))

(defmacro keywordize [a]
  `(zipmap (map (fn [b#] (keyword b#)) (keys ~a)) (vals ~a)))

(defn- qsformat [req]
  (let [fmt (first (select-keys (:query-params req) ["format"]))]
    (if fmt
      (str "?" (first fmt) "=" (get fmt 1))
      nil)))

(defmacro respond [req status data]
  `(render (getoutput (first (filter identity (list
                                                (qsformat ~req)
                                                (get (:headers ~req) "accept"))))) ~status ~data))

(defn defresource [coll options fields]
  (let [store (:store options)
        pk (:pk options)
        collname (as-str coll)
        i_get_one (fn [matches] (get_one store coll pk (:pk matches)))
        i_redirect (fn [req form] (redirect (str "/" collname "/" (get form pk) (qsformat req))))]
  (defroute (str "/" collname)
    {;:get (fn [req matches]
            ; TODO: index
    ;        )
     :post (fn [req matches]
             (let [form (keywordize (:form-params req))]
               (create store coll form)
               (i_redirect req form)))})
  (defroute (str "/" collname "/:pk")
    {:get (fn [req matches]
             (respond req 200 (i_get_one matches)))
     :put (fn [req matches]
            (let [form (keywordize (:form-params req))]
              (update store coll (i_get_one matches) form)
              (i_redirect req form)))
     :delete (fn [req matches]
               (delete store coll (i_get_one matches))
               (respond req 200 {}))})))
