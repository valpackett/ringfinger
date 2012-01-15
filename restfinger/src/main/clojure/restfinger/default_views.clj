(ns #^{:skip-wiki true} restfinger.default-views
  (:use formfinger.field-helpers,
        corefinger.core,
        toolfinger,
        inflections.core,
        (hiccup core page-helpers)))

(defn p-flash []
  (if-let [f (:flash *request*)]
    [:div.flash f]))

(defn p-csrftoken []
  [:input {:type "hidden" :name "csrftoken" :value (:csrf-token *request*)}])

(defn p-user []
  [:div.user
   (if-let [u (get-in *request* [:user :username])]
     (str "Logged in as " u ".")
     "Not logged in.")])

(defn default-index [{:keys [collname pk fieldhtml urlbase newdata errors data actions]}]
  (let [fieldnames (keys fieldhtml)
        urlbase    (str urlbase "/")]
    (html5 [:html
      [:head [:title (str collname " / index")]
             [:style default-style]]
      [:body
        [:h1 collname]
        (p-user)
        (p-flash)
        [:form.res {:method "post" :action ""}
          (form-fields fieldhtml newdata errors [:div] [:div.error] :label)
          (p-csrftoken)
          [:button {:type "submit"} "Add"]]
        [:table
          [:tr (map (fn [a] [:th a]) fieldnames)]
          (map (fn [e] [:tr
             (map (fn [a] [:td (get e a)]) fieldnames)
             [:td [:a {:href (str urlbase (get e pk))} "edit"]]
             [:td [:a {:href (str urlbase (get e pk) "?_method=delete")} "delete"]]
             (map (fn [a] [:td [:a {:href (str urlbase (get e pk) "?_action=" a)} a]]) (keys actions))
          ]) data)]
       (capitalize (nice-count (count data) "entry")) ". "
       (if-env "development"
               [:a {:href (str urlbase "_create_fakes")} "Add some example data"] nil)
      ]])))

(defn default-get [{:keys [data collname pk urlbase fieldhtml errors]}]
  (html5 [:html
    [:head [:title (str collname " / " (get data pk))]
           [:style default-style]]
    [:body
      [:h1 [:a {:href urlbase} collname] (str " / " (get data pk))]
      (p-user)
      (p-flash)
      [:form.res {:method "post" :action (str urlbase "/" (get data pk) "?_method=put")}
        (form-fields fieldhtml data errors [:div] [:div.error] :label)
        (p-csrftoken)
        [:button {:type "submit"} "Save"]]]]))

(defn default-not-found [stuff]
  (html5 [:html
    [:head [:title (str (:collname stuff) " / not found")]
           [:style default-style]]
    [:body [:h1 "Not found :-("]
           (p-user)
           [:div.cb]]]))
