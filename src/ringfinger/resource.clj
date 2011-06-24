(ns ringfinger.resource
  (:use (ringfinger core db output util)
        ring.util.response,
        hiccup.core,
        valip.core,
        [clojure.contrib.string :only [as-str, split, substring?]]))

(defn- qsformat [req]
  (let [fmt (get (:query-params req) "format")]
    (if fmt
      (str "?format=" fmt)
      nil)))

(defmacro respond [req status data res action]
  `(render (getoutput (first
      (filter identity (list
                          (qsformat ~req)
                          (get (:headers ~req) "accept") ; it must be lowercase!
                         ))) ~res ~action) ~status ~data))

(defmacro qs [a] `(keyword (str "$" ~a)))

(defn- param-to-query
  ([q] {(keyword (first q)) (apply param-to-query (rest q))})
  ([k v] {(qs k) v})
  ([k kk v] {(qs k) {(qs kk) v}})) ; that's for speed
; db doesnt't handle deeper stuff anyway

(defn params-to-query
  ([qp] (apply merge (map params-to-query (keys qp) (vals qp))))
  ([q v] (if (substring? "_" q) (param-to-query (flatten (list (split #"_" q) (typeify v)))) nil)))

(defn resource [collname options & validations]
  (let [store (:store options)
        pk (:pk options)
        coll (keyword collname)
        fields (let [v (group-by first validations)]
                 (zipmap (keys v) (map (fn [a] (apply merge (map (fn [b] (:html (meta (let [va (second b)] `(var ~va))))) a))) (vals v))))
        i_validate (fn [req data yep nope] (let [result (apply validate data validations)]
                      (if (= result nil) (yep) (nope result))))
        i_get_one  (fn [matches] (get_one store coll {pk (typeify (:pk matches))}))
        i_redirect (fn [req form] (redirect (str "/" collname "/" (get form pk) (qsformat req))))]
    (defview collname "index" (fn [stuff]
      (let [data (:data stuff) errors (:errors stuff) fieldnames (keys fields)
            urlbase (str "/" collname "/")]
        (str "<!DOCTYPE html>" (html [:html
          [:head [:title (str collname " / index")]
                 [:style default-style]]
          [:body
            [:h1 collname]
            [:form {:method "post" :action ""}
              (form-fields fields data errors [:div] [:div {:class "error"}] :placeholder)
              [:button {:type "submit"} "Add"]]
            [:table
              [:tr (map (fn [a] [:th a]) fieldnames)]
              (map (fn [e] [:tr
                 (map (fn [a] [:td (get e a)]) fieldnames)
                 [:td [:a {:href (str urlbase (get e pk))} "edit"]]
                 [:td [:a {:href (str urlbase (get e pk) "?_method=delete")} "delete"]]
              ]) data)]
          ]])))))
    (defview collname "get" (fn [stuff]
      (let [data (:data stuff) errors (:errors stuff)]
        (str "<!DOCTYPE html>" (html [:html
          [:head [:title (str collname " / " (get data pk))]
                 [:style default-style]]
          [:body
            [:h1 [:a {:href (str "/" collname)} collname] (str " / " (get data pk))]
            [:form {:method "post" :action (str "/" collname "/" (get data pk) "?_method=put")}
              (form-fields fields data errors [:div] [:div {:class "error"}] :label)
           [:button {:type "submit"} "Save"]]]])))))
    (defview collname "not-found" (fn [stuff]
      (str "<!DOCTYPE html>" (html [:html
        [:head [:title (str collname " / not found")]
               [:style default-style]]
        [:body [:h1 "Not found :-("]]]))))
  (list
    (route (str "/" collname)
      {:get (fn [req matches]
              (respond req 200 {:data (get_many store coll (params-to-query (:query-params req)))} collname "index"))
       :post (fn [req matches]
               (let [form (keywordize (:form-params req))]
                 (i_validate req form
                   (fn []
                     (create store coll (typeize form))
                     (i_redirect req form))
                   (fn [errors]
                     (respond req 400 {:data (get_many store coll (params-to-query (:query-params req)))
                                       :errors errors} collname "index")))))})
    (route (str "/" collname "/:pk")
      {:get (fn [req matches]
              (let [entry (i_get_one matches)]
                (if entry
                  (respond req 200 {:data entry} collname "get")
                  (respond req 404 {} collname "not-found"))))
       :put (fn [req matches]
              (let [form (keywordize (:form-params req))
                    entry (i_get_one matches)
                    updated-entry (merge entry form)]
                (i_validate req updated-entry
                  (fn []
                    (update store coll entry (typeize form))
                    (i_redirect req form))
                  (fn [errors]
                    (respond req 400 {:data updated-entry :errors errors} collname "get")))))
       :delete (fn [req matches]
                 (delete store coll (i_get_one matches))
                 (redirect (str "/" collname)))}))))

(defn defresource [collname options & validations]
  (intern *ns* (symbol collname) (apply resource collname options validations)))
