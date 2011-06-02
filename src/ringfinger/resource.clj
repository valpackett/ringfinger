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

(defn defresource [collname options fields]
  (let [store (:store options)
        pk (:pk options)
        collname (as-str coll)
        i_get_one (fn [matches] (get_one store coll pk) (:pk matches)))
        i_redirect (fn [req form] (redirect (str "/" collname "/" (get form pk)) (qsformat req))))]
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
            {:status 200
             :headers {"Content-Type" "application/json"}
             :body (json-str (i_get_one matches))})
     :put (fn [req matches]
            (let [form (keywordize (:form-params req))]
              (update store coll (i_get_one matches) form)
              (i_redirect req form)))
     :delete (fn [req matches]
               (delete store coll (i_get_one matches))
               {:status  200
                :headers {"Content-Type" "application/json"}
                :body "{}"})})))
