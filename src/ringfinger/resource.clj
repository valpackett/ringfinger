(ns ringfinger.resource
  (:use ringfinger.core, ringfinger.db,
        ring.util.response,
        clojure.contrib.json, [clojure.contrib.string :only [as-str]]))

(defn- keywordize [a]
  (zipmap (map (fn [b] (keyword b)) (keys a)) (vals a)))

(defn- qsformat [req]
  (let [format (first (select-keys (:query-params req) ["format"]))]
    (if format
      (str "?" (first format) "=" (get format 1))
      "")))

(defn defresource [coll options fields]
  (defroute (str "/" (as-str coll))
    {;:get (fn [req matches]
            ; TODO: index
    ;        )
     :post (fn [req matches]
             (let [form (keywordize (:form-params req))]
               (create (:store options) coll form)
               (redirect (str "/" (as-str coll) "/" (get form (:pk options)) (qsformat req)))
               ))})
  (defroute (str "/" (as-str coll) "/:pk")
    {:get (fn [req matches]
            {:status 200
             :headers {"Content-Type" "application/json"}
             :body (json-str (get_one (:store options) coll (:pk options) (:pk matches)))}) ; TODO: DRY this
     :put (fn [req matches]
            (let [form (keywordize (:form-params req))]
              (update (:store options) coll (get_one (:store options) coll (:pk options) (:pk matches)) form)
              (redirect (str "/" (as-str coll) "/" (get form (:pk options)) (qsformat req)))))
     :delete (fn [req matches]
               (delete (:store options) coll (get_one (:store options) coll (:pk options) (:pk matches)))
               {:status  200
                :headers {"Content-Type" "application/json"}
                :body "{}"})}))
