(ns #^{:skip-wiki true} ringfinger.default-views
  (:use (ringfinger field-helpers util)
        (hiccup core page-helpers)))

(defn p-flash [stuff]
  (if-let [f (get-in stuff [:req :flash])]
    [:div {:class "flash"} f]))

(defn p-csrftoken [stuff]
  [:input {:type "hidden" :name "csrftoken" :value (get-in stuff [:req :csrf-token])}])

(defn default-index [stuff]
  (let [collname   (:collname stuff)
        pk         (:pk stuff)
        fields     (:fields stuff)
        fieldnames (keys fields)
        urlbase    (str "/" collname "/")]
    (html5 [:html
      [:head [:title (str collname " / index")]
             [:style default-style]]
      [:body
        [:h1 collname]
        (p-flash stuff)
        [:form {:method "post" :action ""}
          (form-fields fields (:newdata stuff) (:errors stuff) [:div] [:div {:class "error"}] :placeholder)
          (p-csrftoken stuff)
          [:button {:type "submit"} "Add"]]
        [:table
          [:tr (map (fn [a] [:th a]) fieldnames)]
          (map (fn [e] [:tr
             (map (fn [a] [:td (get e a)]) fieldnames)
             [:td [:a {:href (str urlbase (get e pk))} "edit"]]
             [:td [:a {:href (str urlbase (get e pk) "?_method=delete")} "delete"]]
          ]) (:data stuff))]
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
        (p-flash stuff)
        [:form {:method "post" :action (str "/" collname "/" (get data pk) "?_method=put")}
          (form-fields (:fields stuff) data (:errors stuff) [:div] [:div {:class "error"}] :label)
          (p-csrftoken stuff)
          [:button {:type "submit"} "Save"]]]])))

(defn default-not-found [stuff]
  (html5 [:html
    [:head [:title (str (:collname stuff) " / not found")]
           [:style default-style]]
    [:body [:h1 "Not found :-("]]]))
