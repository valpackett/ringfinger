(ns ringfinger.resource
  (:use ringfinger.core, ringfinger.db, ringfinger.output
        ring.util.response,
        valip.core, valip.predicates,
        [clojure.contrib.string :only [as-str]]))

(defmacro normalize [a]
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

(defn defresource [coll options & validations]
  (let [store (:store options)
        pk (:pk options)
        collname (as-str coll)
        i_validate (fn [req data yep] (let [result (apply validate data validations)] (if (= result nil) (yep) (respond req 400 result))))
        i_get_one  (fn [matches] (get_one store coll pk (:pk matches)))
        i_redirect (fn [req form] (redirect (str "/" collname "/" (get form pk) (qsformat req))))]
  (defroute (str "/" collname)
    {;:get (fn [req matches]
            ; TODO: index
    ;        )
     :post (fn [req matches]
             (let [form (normalize (:form-params req))]
               (i_validate req form
                 (fn []
                   (create store coll form)
                   (i_redirect req form)))))})
  (defroute (str "/" collname "/:pk")
    {:get (fn [req matches]
             (respond req 200 (i_get_one matches)))
     :put (fn [req matches]
            (let [form (normalize (:form-params req))
                  entry (i_get_one matches)]
              (i_validate req (merge entry form)
                (fn []
                  (update store coll entry form)
                  (i_redirect req form)))))
     :delete (fn [req matches]
               (delete store coll (i_get_one matches))
               (respond req 200 {}))})))
