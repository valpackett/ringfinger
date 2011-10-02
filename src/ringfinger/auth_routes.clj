(ns ringfinger.auth-routes
  "Authorization routes -- magical registration (if you really want, even with
  e-mail confirmation) and logging in/out."
  (:use (ringfinger auth core util db fields field-helpers default-views),
        ringfinger.db.inmem,
        valip.core)
  (:import java.util.UUID))

(defn get-action [nm]
  (str (:uri *request*)
       (let [hdrs (:headers *request*)
             dmn  (str (name (:scheme *request*)) "://" (get hdrs "host"))
             rf   (get hdrs "referer" "")]
         (map-to-querystring
           (apply merge (:query-string *request*)
                        (if (and (substring? dmn rf) (not (substring? "/login" rf)))
                          {nm (str-drop (count dmn) rf)}
                          {}))))))

(defn auth-cookie [user]
  {:expires "Sun, 16-Dec-2029 03:24:16 GMT" :path "/" :http-only true :value (:auth_token user)})

(defn auth-routes
  "Creates auth routes with given options:
   :db, :coll -- database and collection
   :views -- map of views (:login, :signup and :confirm)
   :flash -- map of flash messages (:login-success, :login-invalid, :signup-success, :logout, :confirm-success and :confirm-fail)
   :url-base -- the starting part of auth URLs, the default is /auth/
   :redir-to -- where to redirect after a successful login/signup if there's no referer, the default is /
   :redir-param -- query string parameter for keeping the redirect url, the default is _redirect, you generally don't need to care about this
   :confirm -- if you want email confirmation, map of parameters :mailer, :from, :email-field (default is :username), :subject, :mail-template
   :fields -- list of validations, defaults are: requiring username and at least 6 characters password"
  [options]
  (let [views    (:views       options auth-demo-views)
        flash    (:flash       options {:login-success   "Welcome back!"
                                        :login-invalid   "Wrong username/password."
                                        :signup-success  "Welcome!"
                                        :logout          "Good bye!"
                                        :confirm-success "Welcome!"
                                        :confirm-fail    "Invalid confirmaton key."})
        url-base (:url-base    options "/auth/")
        redir-to (:redir-to    options "/")
        redir-p  (:redir-param options "_redirect")
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
                         :body    ""}
                        cb))]
    (list
      (route (str url-base "login")
        {:get (fn [req m]
                (if-not-user req
                  {:status  200
                   :headers {"Content-Type" "text/html; encoding=utf-8"}
                   :body    ((:login views) {:errors {}
                                             :data   {}
                                             :fields fieldhtml
                                             :urlb   url-base
                                             :action (get-action redir-p)})}))
         :post (fn [req m]
                (if-not-user req
                  (let [form (keywordize (:form-params req))
                        fval (apply validate form valds)
                        user (get-user db coll (:username form) (:password form))]
                    (if (nil? fval)
                      (if (nil? user)
                        (binding [*request* (assoc *request* :flash (:login-invalid flash))]
                          {:status  400
                           :headers {"Content-Type" "text/html; encoding=utf-8"}
                           :body    ((:login views) {:errors {}
                                                     :data   (merge form {:password nil})
                                                     :fields fieldhtml
                                                     :urlb   url-base
                                                     :action (get-action redir-p)})})
                        {:status  302
                         :headers {"Location" (getloc req)}
                         :cookies {"a" (auth-cookie user)}
                         :flash   (:login-success flash)
                         :body    ""})
                      {:status  400
                       :headers {"Content-Type" "text/html; encoding=utf-8"}
                       :body    ((:login views) {:errors fval
                                                 :data   form
                                                 :fields fieldhtml
                                                 :urlb   url-base
                                                 :action (get-action redir-p)})}))))})
      (route (str url-base "logout")
        {:get (fn [req m]
                {:status  302
                 :headers {"Location" (getloc req)}
                 :cookies {"a" {:expires "Thu, 01-Jan-1970 00:00:01 GMT" :path "/" :value ""}}
                 :flash   (:logout flash)
                 :body    ""})})
      (if confirm
        (route (str url-base "confirm/:akey")
          {:get (fn [req m]
                  (if-not-user req
                    (if-let [user (get-one db coll {:query {:_confirm_key (:akey m)}})]
                      (let []
                        (delete db coll user)
                        (create db coll (dissoc user :_confirm_key))
                        {:status  302
                         :headers {"Location" (getloc req)}
                         :cookies {"a" (auth-cookie user)}
                         :flash   (:confirm-success flash)
                         :body    ""})
                      {:status  302
                       :headers {"Location" (getloc req)}
                       :flash   (:confirm-fail flash)
                       :body    ""}
                        )))}))
      (route (str url-base "signup")
        {:get (fn [req m]
                (if-not-user req
                  {:status  200
                   :headers {"Content-Type" "text/html; encoding=utf-8"}
                   :body    ((:signup views) {:errors {}
                                              :data   {}
                                              :fields fieldhtml
                                              :urlb   url-base
                                              :action (get-action redir-p)})}))
          :post (if confirm
                   (fn [req m]
                     (if-not-user req
                        (let [form (keywordize (:form-params req))
                              fval (apply validate form valds)]
                          (if (nil? fval)
                            (let [akey (str (UUID/randomUUID))
                                  user (make-user db coll {:username (:username form) :_confirm_key akey} (:password form))]
                              ((:mailer confirm)
                               (:from confirm)
                               (get form (:email-field confirm :username))
                               (:subject confirm)
                               ((:mail-template confirm demo-mail-template)
                                  {:data  form
                                   :url   (str (name (:scheme req)) "://" (get (:headers req) "host") url-base "confirm/" akey "?" redir-p "=" (getloc req))}))
                              {:status  200
                               :headers {"Content-Type" "text/html; encoding=utf-8"}
                               :body    ((:confirm views) {:data form})})
                            {:status  400
                             :headers {"Content-Type" "text/html; encoding=utf-8"}
                             :body    ((:signup views) {:errors fval
                                                        :data   form
                                                        :fields fieldhtml
                                                        :urlb   url-base
                                                        :action (get-action redir-p)})}))))
                   (fn [req m]
                     (if-not-user req
                        (let [form (keywordize (:form-params req))
                              fval (apply validate form valds)]
                          (if (nil? fval)
                            (let [user (make-user db coll {:username (:username form)} (:password form))]
                              {:status  302
                               :headers {"Location" (getloc req)}
                               :cookies {"a" (auth-cookie user)}
                               :flash   (:signup-success flash)
                               :body    ""})
                            {:status  400
                             :headers {"Content-Type" "text/html; encoding=utf-8"}
                             :body    ((:signup views) {:errors fval
                                                        :data   form
                                                        :fields fieldhtml
                                                        :urlb   url-base
                                                        :action (get-action redir-p)})})))))}))))
