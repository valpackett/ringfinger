(ns ringfinger.default-views
  (:use (ringfinger util),
        (hiccup core page-helpers)))

(defn default-index [stuff]
  (let [data       (:data stuff)
        collname   (:collname stuff)
        pk         (:pk stuff)
        fields     (:fields stuff)
        fieldnames (keys fields)
        urlbase    (str "/" collname "/")]
    (html5 [:html
      [:head [:title (str collname " / index")]
             [:style default-style]]
      [:body
        [:h1 collname]
        (if (:flash stuff) [:div {:class "flash"} (:flash stuff)])
        [:form {:method "post" :action ""}
          (form-fields fields data (:errors stuff) [:div] [:div {:class "error"}] :placeholder)
          [:button {:type "submit"} "Add"]]
        [:table
          [:tr (map (fn [a] [:th a]) fieldnames)]
          (map (fn [e] [:tr
             (map (fn [a] [:td (get e a)]) fieldnames)
             [:td [:a {:href (str urlbase (get e pk))} "edit"]]
             [:td [:a {:href (str urlbase (get e pk) "?_method=delete")} "delete"]]
          ]) data)]
      ]])))

(defn default-get [stuff]
  (let [data     (:data stuff)
        collname (:collname stuff)
        pk       (:pk stuff)]
    (html5 [:html
      [:head [:title (str collname " / " (get data pk))]
             [:style default-style]]
      [:body
        [:h1 [:a {:href (str "/" collname)} collname] (str " / " (get data pk))]
        (if (:flash stuff) [:div {:class "flash"} (:flash stuff)])
        [:form {:method "post" :action (str "/" collname "/" (get data pk) "?_method=put")}
          (form-fields (:fields stuff) data (:errors stuff) [:div] [:div {:class "error"}] :label)
       [:button {:type "submit"} "Save"]]]])))

(defn default-not-found [stuff]
  (html5 [:html
    [:head [:title (str (:collname stuff) " / not found")]
           [:style default-style]]
    [:body [:h1 "Not found :-("]]]))
