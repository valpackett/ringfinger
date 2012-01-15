(ns #^{:skip-wiki true} authfinger.default-views
  (:use formfinger.core,
        corefinger.core,
        toolfinger,
        (hiccup core page-helpers)))

(defn p-flash [f]
  (if-let [f (or f (:flash *request*))]
    [:div.flash f]))

(def auth-demo-views
  {:login
   (fn [{:keys [action form data errors urlb flash]}]
     (html5
       [:html
         [:head [:title "Log in"]
                [:style default-style]]
         [:body
           [:h1 "Log in"]
           (p-flash flash)
           [:form {:method "post" :action action}
             (render {:style :placeholder} form data errors)
             [:button {:type "submit"} "Log in!"]
             [:a.altact {:href (str urlb "signup")} "or sign up"]]]]))
   :signup
   (fn [{:keys [action form data errors urlb flash]}]
     (html5
       [:html
         [:head [:title "Sign up"]
                [:style default-style]]
         [:body
           [:h1 "Sign up"]
           (p-flash flash)
           [:form {:method "post" :action action}
           (render {:style :placeholder} form data errors)
           [:button {:type "submit"} "Sign up!"]
           [:a.altact {:href (str urlb "login")} "or log in"]]]]))
   :confirm
   (fn [{:keys [flash]}]
     (html5
       [:html
         [:head [:title "Confirm"]
                [:style default-style]]
         [:body
           [:h1 "Confirm"]
           (p-flash flash)
           "Check your email."]]))})

(defn demo-mail-template [data]
  (str "Welcome! To activate your account, click this link: " (:url data)))
