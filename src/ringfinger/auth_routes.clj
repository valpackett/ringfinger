(ns ringfinger.auth-routes
  (:use (ringfinger auth core util db email fields default-views),
        ringfinger.db.inmem,
        valip.core)
  (:require [clojure.contrib.string :as cstr]
            [hiccup.page-helpers    :as hic])
  (:import java.util.UUID))

(defn get-action [req nm]
  (str (:uri req)
       (let [hdrs (:headers req)
             dmn  (str (cstr/as-str (:scheme req)) "://" (get hdrs "host"))
             rf   (get hdrs "referer" "")]
         (if (cstr/substring? dmn rf)
           (str "?" nm "=" (cstr/drop (count dmn) rf))
           ""))))

(def auth-demo-views
  {:login  (fn [stuff] (let [errs (:errors stuff)] (hic/html5 [:html
    [:head [:title "Log in"]
           [:style default-style]]
    [:body
     [:h1 "Log in"]
     (p-flash stuff)
     [:form {:method "post" :action (:action stuff)}
      (form-fields (:fields stuff) (:data stuff) (:errors stuff) [:div] [:div {:class "error"}] :placeholder)
      (p-csrftoken stuff)
      [:button {:type "submit"} "Log in!"]]]])))
   :signup (fn [stuff] (hic/html5 [:html
    [:head [:title "Sign up"]
           [:style default-style]]
    [:body
     [:h1 "Sign up"]
     (p-flash stuff)
     [:form {:method "post" :action (:action stuff)}
      (form-fields (:fields stuff) (:data stuff) (:errors stuff) [:div] [:div {:class "error"}] :placeholder)
      (p-csrftoken stuff)
      [:button {:type "submit"} "Sign up!"]
      ]]]))
   :confirm (fn [stuff] (hic/html5 [:html
    [:head [:title "Confirm"]
           [:style default-style]]
    [:body
     [:h1 "Confirm"]
     (p-flash stuff)
     "Check your email."
     ]]))
   })

(defn- demo-mail-template [data]
  (str "Welcome! To activate your account, click this link: " (:url data)))

(defn auth-routes
  "Creates auth routes with given options:
   :db, :coll -- database and collection
   :views -- map of views (:login, :signup and :confirm)
   :flash -- map of flash messages (:login-success, :login-invalid, :signup-success, :logout, :confirm-success and :confirm-fail)
   :fixed-salt -- fixed part of the salt, must be the same as you use with app. NEVER change this in production!!
   :url-base -- the starting part of auth URLs, the default is /auth/
   :redir-to -- where to redirect after a successful login/signup if there's no referer, the default is /
   :redir-param -- query string parameter for keeping the redirect url, the default is redirect, you generally don't need to care about this
   :confirm -- if you want email confirmation, map of parameters :mailer, :from, :email-field (default is :username), :subject, :mail-template
   :fields -- list of validations, defaults is requiring username and at least 6 characters password"
  [options]
  (let [views    (:views       options auth-demo-views)
        flash    (:flash       options {:login-success   "Welcome back!"
                                        :login-invalid   "Wrong username/password."
                                        :signup-success  "Welcome!"
                                        :logout          "Good bye!"
                                        :confirm-success "Welcome!"
                                        :confirm-fail    "Invalid confirmaton key."})
        fixed-s  (:fixed-salt  options "ringfingerFTW")
        url-base (:url-base    options "/auth/")
        redir-to (:redir-to    options "/")
        redir-p  (:redir-param options "redirect")
        db       (:db          options inmem)
        coll     (:coll        options :ringfinger_auth)
        confirm  (:confirm     options)
        fields   (:fields      options (list [:username (required)     "Shouldn't be empty"]
                                             [:password (required)     "Shouldn't be empty"]
                                             [:password (minlength 6)  "Should be at least 6 characters"]))
        fieldhtml(html-from-fields fields)
        valds    (validations-from-fields fields)
        getloc   #(get (:query-params %) redir-p redir-to)
        if-not-user (fn [req cb]
                      (if (:user req)
                        {:status  302
                         :headers {"Location" (getloc req)}
                         :body    nil}
                        cb))]
    (filter identity (list
      (route (str url-base "login")
        {:get (fn [req m]
                (if-not-user req
                  {:status  200
                   :headers {"Content-Type" "text/html; encoding=utf-8"}
                   :body    ((:login views) {:errors {}
                                             :data   {}
                                             :fields fieldhtml
                                             :req    req
                                             :action (get-action req redir-p)})}))
         :post (fn [req m]
                 (if-not-user req
                   (let [form (keywordize (:form-params req))
                         fval (apply validate form valds)
                         user (get-user db coll (:username form) (:password form) fixed-s)]
                     (if (nil? fval)
                       (if (nil? user)
                         {:status  400
                          :headers {"Content-Type" "text/html; encoding=utf-8"}
                          :body    ((:login views) {:errors {}
                                                    :data   (merge form {:password nil})
                                                    :fields fieldhtml
                                                    :req    req
                                                    :action (get-action req redir-p)})}
                         {:status  302
                          :headers {"Location" (getloc req)}
                          :session {:username (:username user)}
                          :flash   (:login-success flash)
                          :body    nil})
                       {:status  400
                        :headers {"Content-Type" "text/html; encoding=utf-8"}
                        :body    ((:login views) {:errors fval
                                                  :data   form
                                                  :fields fieldhtml
                                                  :req    req
                                                  :action (get-action req redir-p)})}))))})
      (route (str url-base "logout")
        {:get (fn [req m]
                {:status  302
                 :headers {"Location" (getloc req)}
                 :session {:username nil}
                 :flash   (:logout flash)
                 :body    nil})})
      (if confirm
        (route (str url-base "confirm/:akey")
          {:get (fn [req m]
                  (if-not-user req
                    (let [user (get-one db coll {:query {:_confirm_key (:akey m)}})]
                      (if (nil? user)
                        {:status  302
                         :headers {"Location" (getloc req)}
                         :flash   (:confirm-fail flash)
                         :body    nil}
                        (let []
                          (delete db coll user)
                          (create db coll (dissoc user :_confirm_key))
                          {:status  302
                           :headers {"Location" (getloc req)}
                           :flash   (:confirm-success flash)
                           :session {:username (:username user)}
                           :body    nil})))))}))
      (route (str url-base "signup")
        {:get (fn [req m]
                (if-not-user req
                  {:status  200
                   :headers {"Content-Type" "text/html; encoding=utf-8"}
                   :body    ((:signup views) {:errors {}
                                              :data   {}
                                              :fields fieldhtml
                                              :req    req
                                              :action (get-action req redir-p)})}))
          :post (if confirm
                   (fn [req m]
                     (if-not-user req
                        (let [form (keywordize (:form-params req))
                              fval (apply validate form valds)]
                          (if (nil? fval)
                            (let [akey (str (UUID/randomUUID))
                                  user (make-user db coll {:username (:username form) :_confirm_key akey} (:password form) fixed-s)]
                              (send-mail (:mailer confirm)
                                         (:from confirm)
                                         (get form (:email-field confirm :username))
                                         (:subject confirm)
                                         ((:mail-template confirm demo-mail-template)
                                            {:data  form
                                             :url   (str (cstr/as-str (:scheme req)) "://" (get (:headers req) "host") url-base "confirm/" akey "?" redir-p "=" (getloc req))}))
                              {:status  200
                               :headers {"Content-Type" "text/html; encoding=utf-8"}
                               :body    ((:confirm views) {:data  form
                                                           :req    req})})
                            {:status  400
                             :headers {"Content-Type" "text/html; encoding=utf-8"}
                             :body    ((:signup views) {:errors fval
                                                        :data   form
                                                        :fields fieldhtml
                                                        :req    req
                                                        :action (get-action req redir-p)})}))))
                   (fn [req m]
                     (if-not-user req
                        (let [form (keywordize (:form-params req))
                              fval (apply validate form valds)]
                          (if (nil? fval)
                            (let [user (make-user db coll {:username (:username form)} (:password form) fixed-s)]
                              {:status  302
                               :headers {"Location" (getloc req)}
                               :session {:username (:username form)}
                               :flash   (:signup-success flash)
                               :body    nil})
                            {:status  400
                             :headers {"Content-Type" "text/html; encoding=utf-8"}
                             :body    ((:signup views) {:errors fval
                                                        :data   form
                                                        :fields fieldhtml
                                                        :req    req
                                                        :action (get-action req redir-p)})})))))})))))
