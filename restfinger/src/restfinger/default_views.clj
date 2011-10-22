(ns #^{:skip-wiki true} restfinger.default-views
  (:use formfinger.field-helpers,
        corefinger.core,
        toolfinger,
        inflections.core,
        (hiccup core page-helpers)))

(defn p-flash [stuff]
  (if-let [f (:flash *request*)]
    [:div.flash f]))

(defn p-csrftoken [stuff]
  [:input {:type "hidden" :name "csrftoken" :value (:csrf-token *request*)}])

(defn p-user [stuff]
  [:div.user
   (if-let [u (get-in *request* [:user :username])]
     (str "Logged in as " u ".")
     "Not logged in.")])

(defn default-index [stuff]
  (let [collname   (:collname stuff)
        pk         (:pk stuff)
        fields     (:fieldhtml stuff)
        fieldnames (keys fields)
        urlbase    (str (:urlbase stuff) "/")]
    (html5 [:html
      [:head [:title (str collname " / index")]
             [:style default-style]]
      [:body
        [:h1 collname]
        (p-user stuff)
        (p-flash stuff)
        [:form.res {:method "post" :action ""}
          (form-fields fields (:newdata stuff) (:errors stuff) [:div] [:div.error] :label)
          (p-csrftoken stuff)
          [:button {:type "submit"} "Add"]]
        [:table
          [:tr (map (fn [a] [:th a]) fieldnames)]
          (map (fn [e] [:tr
             (map (fn [a] [:td (get e a)]) fieldnames)
             [:td [:a {:href (str urlbase (get e pk))} "edit"]]
             [:td [:a {:href (str urlbase (get e pk) "?_method=delete")} "delete"]]
             (map (fn [a] [:td [:a {:href (str urlbase (get e pk) "?_action=" a)} a]]) (keys (:actions stuff)))
          ]) (:data stuff))]
       (capitalize (nice-count (count (:data stuff)) "entry")) ". "
       (if-env "development"
               [:a {:href (str urlbase "_create_fakes")} "Add some example data"] nil)
      ]])))

(defn default-get [stuff]
  (let [data     (:data stuff)
        collname (:collname stuff)
        pk       (:pk stuff)]
    (html5 [:html
      [:head [:title (str collname " / " (get data pk))]
             [:style default-style]]
      [:body
        [:h1 [:a {:href (:urlbase stuff)} collname] (str " / " (get data pk))]
        (p-user stuff)
        (p-flash stuff)
        [:form.res {:method "post" :action (str (:urlbase stuff) "/" (get data pk) "?_method=put")}
          (form-fields (:fieldhtml stuff) data (:errors stuff) [:div] [:div.error] :label)
          (p-csrftoken stuff)
          [:button {:type "submit"} "Save"]]]])))

(defn default-not-found [stuff]
  (html5 [:html
    [:head [:title (str (:collname stuff) " / not found")]
           [:style default-style]]
    [:body [:h1 "Not found :-("]
           (p-user stuff)
           [:div.cb]
    ]]))
