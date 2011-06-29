(ns ringfinger.auth-handler
  (:use (ringfinger auth core util validation), ringfinger.db.inmem,
        valip.core)
  (:require [clojure.contrib.string :as cstr]
            [hiccup.page-helpers    :as hic]))

(defn get-action [req nm]
  (str (:uri req)
       (let [hdrs (:headers req)
             dmn  (str (cstr/as-str (:scheme req)) "://" (get hdrs "host"))
             rf   (or (get hdrs "referer") "")]
         (if (cstr/substring? dmn rf)
           (str "?" nm "=" (cstr/drop (count dmn) rf))
           ""))))

(def auth-demo-views
  {:login  (fn [stuff] (let [errs (:errors stuff)] (hic/html5 [:html
    [:head [:title "Log in"]
           [:style default-style]]
    [:body
     [:h1 "Log in"]
     (if (:flash stuff) [:div {:class "flash"} (:flash stuff)])
     [:form {:method "post" :action (:action stuff)}
      (form-fields (:fields stuff) (:data stuff) (:errors stuff) [:div] [:div {:class "error"}] :placeholder)
      [:button {:type "submit"} "Log in!"]]]])))
   :signup (fn [stuff] (hic/html5 [:html
    [:head [:title "Sign up"]
           [:style default-style]]
    [:body
     [:h1 "Sign up"]
     (if (:flash stuff) [:div {:class "flash"} (:flash stuff)])
     [:form {:method "post" :action (:action stuff)}
      (form-fields (:fields stuff) (:data stuff) (:errors stuff) [:div] [:div {:class "error"}] :placeholder)
      [:button {:type "submit"} "Sign up!"]
      ]]]))})

(defn auth-handlers [custom-options]
  (let [options  (merge {:url-base    "/auth/"
                         :views       auth-demo-views
                         :flash       {:login-success  "Welcome back!"
                                       :login-invalid  "Wrong username/password."
                                       :signup-success "Welcome!"
                                       :logout         "Good bye!"}
                         :fixed-salt  "ringfingerFTW"
                         :redir-to    "/"
                         :redir-param "redirect"
                         :db          inmem
                         :coll        :ringfinger_auth
                         :validations (list [:username (required)     "Shouldn't be empty"]
                                            [:username (alphanumeric) "Should be alphanumeric"]
                                            [:password (required)     "Shouldn't be empty"]
                                            [:password (minlength 6)  "Should be at least 6 characters"])} custom-options)
        views    (:views       options)
        flash    (:flash       options)
        fixed-s  (:fixed-salt  options)
        url-base (:url-base    options)
        redir-to (:redir-to    options)
        redir-p  (:redir-param options)
        db       (:db          options)
        coll     (:coll        options)
        valds    (:validations options)
        hvalds   (map #(assoc % 1 (:clj (second %))) valds)
        fields   (fields-from-validations valds)
        if-not-user (fn [req cb]
                      (if (:user req)
                        {:status  302
                         :headers {"Location" (or (get (:query-params req) redir-p) redir-to)}
                         :body    nil}
                        cb))]
    (list
      (route (str url-base "login")
        {:get (fn [req m]
                (if-not-user req
                  {:status  200
                   :headers {"Content-Type" "text/html; encoding=utf-8"}
                   :body    ((:login views) {:errors {}
                                             :data   {}
                                             :fields fields
                                             :flash  (:flash req)
                                             :action (get-action req redir-p)})}))
         :post (fn [req m]
                 (if-not-user req
                   (let [form (keywordize (:form-params req))
                         fval (apply validate form hvalds)
                         user (get-user db coll (:username form) (:password form) fixed-s)]
                     (if (nil? fval)
                       (if (nil? user)
                         {:status  400
                          :headers {"Content-Type" "text/html; encoding=utf-8"}
                          :body    ((:login views) {:errors {}
                                                    :data   (merge form {:password nil})
                                                    :fields fields
                                                    :flash  (:login-invalid flash)
                                                    :action (get-action req redir-p)})}
                         {:status  302
                          :headers {"Location" (or (get (:query-params req) redir-p) redir-to)}
                          :session {:username (:username user)}
                          :flash   (:login-success flash)
                          :body    nil})
                       {:status  400
                        :headers {"Content-Type" "text/html; encoding=utf-8"}
                        :body    ((:login views) {:errors fval
                                                  :data   form
                                                  :fields fields
                                                  :flash  (:flash req)
                                                  :action (get-action req redir-p)})}))))})
      (route (str url-base "logout")
        {:get (fn [req m]
                {:status  302
                 :headers {"Location" (or (get (:query-params req) redir-p) redir-to)}
                 :session {:username nil}
                 :flash   (:logout flash)
                 :body    nil})})
      (route (str url-base "signup")
        {:get (fn [req m]
                (if-not-user req
                  {:status  200
                   :headers {"Content-Type" "text/html; encoding=utf-8"}
                   :body    ((:signup views) {:errors {}
                                              :data   {}
                                              :fields fields
                                              :flash  (:flash req)
                                              :action (get-action req redir-p)})}))
         :post (fn [req m]
                 (if-not-user req
                    (let [form (keywordize (:form-params req))
                          fval (apply validate form hvalds)]
                      (if (nil? fval)
                        (let [user (make-user db coll {:username (:username form)} (:password form) fixed-s)]
                          {:status  302
                           :headers {"Location" (or (get (:query-params req) redir-p) redir-to)}
                           :session {:username (:username form)}
                           :flash   (:signup-success flash)
                           :body    nil})
                        {:status  400
                         :headers {"Content-Type" "text/html; encoding=utf-8"}
                         :body    ((:login views) {:errors fval
                                                   :data   form
                                                   :fields fields
                                                   :flash  (:flash req)
                                                   :action (get-action req redir-p)})}))))}))))
