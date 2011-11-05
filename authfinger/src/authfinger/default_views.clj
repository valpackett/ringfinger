(ns #^{:skip-wiki true} authfinger.default-views
  (:use formfinger.field-helpers,
        corefinger.core,
        toolfinger,
        (hiccup core page-helpers)))

(defn p-flash [stuff]
  (if-let [f (or (:flash stuff) (:flash *request*))]
    [:div.flash f]))

(defn p-csrftoken [stuff]
  [:input {:type "hidden" :name "csrftoken" :value (:csrf-token *request*)}])

(def auth-demo-views
  {:login  (fn [stuff] (let [errs (:errors stuff)] (html5 [:html
    [:head [:title "Log in"]
           [:style default-style]]
    [:body
     [:h1 "Log in"]
     (p-flash stuff)
     [:form {:method "post" :action (:action stuff)}
      (form-fields (:fields stuff) (:data stuff) (:errors stuff) [:div] [:div.error] :placeholder)
      (p-csrftoken stuff)
      [:button {:type "submit"} "Log in!"]
      [:a.altact {:href (str (:urlb stuff) "signup")} "or sign up"]
     ]]])))
   :signup (fn [stuff] (html5 [:html
    [:head [:title "Sign up"]
           [:style default-style]]
    [:body
     [:h1 "Sign up"]
     (p-flash stuff)
     [:form {:method "post" :action (:action stuff)}
      (form-fields (:fields stuff) (:data stuff) (:errors stuff) [:div] [:div.error] :placeholder)
      (p-csrftoken stuff)
      [:button {:type "submit"} "Sign up!"]
      [:a.altact {:href (str (:urlb stuff) "login")} "or log in"]
      ]]]))
   :confirm (fn [stuff] (html5 [:html
    [:head [:title "Confirm"]
           [:style default-style]]
    [:body
     [:h1 "Confirm"]
     (p-flash stuff)
     "Check your email."
     ]]))
   })

(defn demo-mail-template [data]
  (str "Welcome! To activate your account, click this link: " (:url data)))
