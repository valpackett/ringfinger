(ns authfinger.routes
  "Authorization routes -- registration (if you really want, even with
  e-mail confirmation) and logging in/out."
  (:use (formfinger core fields),
        (basefinger core inmem),
        (authfinger core default-views),
        corefinger.core,
        toolfinger,
        clojure.walk)
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
  {:expires "Sun, 16-Dec-2029 03:24:16 GMT" :path "/" :http-only true :value (:token user)})

(defn ewrap-if-not-user
  [handler redir-p redir-to]
  (fn [req matches]
    (if (:user req)
        {:status  302
         :headers {"Location" (get (:query-params req) redir-p redir-to)}
         :body    ""}
        (handler req matches))))

(defn ewrap-render-auth-view
  [handler views form url-base redir-p]
  (fn [req matches]
    (let [resp (handler req matches)
          v (:aview resp)]
      (if v
          (-> resp
              (assoc :body
                     ((get views v)
                     {:errors (:errors resp {})
                      :data   (:data resp {})
                      :form   form
                      :urlb   url-base
                      :flash  (:i-flash resp)
                      :action (get-action redir-p)}))
              (assoc :headers
                     (merge (:headers resp {})
                            {"Content-Type" "text/html; charset=utf-8"})))
          resp))))

(defn ewrap-process-form
  [handler form]
  (fn [req matches]
    (let [data (keywordize-keys (:params req))]
      (handler req matches data (validate form data)))))

(defn auth-routes
  "Creates auth routes with given options:
   :db, :coll -- database and collection
   :views -- map of views (:login, :signup and :confirm)
   :flash -- map of flash messages (:login-success, :login-invalid, :signup-success, :logout, :confirm-success and :confirm-fail)
   :url-base -- the starting part of auth URLs, the default is /auth/
   :redir-to -- where to redirect after a successful login/signup if there's no referer, the default is /
   :redir-param -- query string parameter for keeping the redirect url, the default is _redirect, you generally don't need to care about this
   :confirm -- if you want email confirmation, map of parameters :mailer, :from, :email-field (default is *login-field*), :subject, :mail-template
   :form -- well, form. defaults are: requiring username and at least 6 characters password"
  [{:keys [views flash url-base redir-to redir-p db coll confirm form]
    :or {views auth-demo-views
         flash {:login-success   "Welcome back!"
                :login-invalid   "Wrong username/password."
                :signup-success  "Welcome!"
                :logout          "Good bye!"
                :confirm-success "Welcome!"
                :confirm-fail    "Invalid confirmaton key."}
         url-base "/auth/"
         redir-to "/" redir-p "redirect"
         db inmem coll :ringfinger_auth
         confirm nil
         form {*login-field* [(f (required) "Shouldn't be empty")
                              (f (minlength 2) "Should be at least 2 characters")
                              (f (maxlength 96) "You're insane!")]
               :password [(f (required) "Shouldn't be empty")
                          (f (minlength 6) "Should be at least 6 characters")
                          (f (maxlength 128) "You must be crazy!")]} }}]
  (if confirm (assert (haz? (arities (:mailer confirm)) 4)))
  (let [getloc #(get (:query-params %) redir-p redir-to)
        lwrap-if-not-user (fn [handler] (ewrap-if-not-user handler redir-p redir-to))
        lwrap-render-auth-view (fn [handler] (ewrap-render-auth-view handler views form url-base redir-p))
        lwrap-process-form (fn [handler] (ewrap-process-form handler form))]
    (list
      (route (str url-base "login")
        (-> (method-dispatch-handler
              {:get  (fn [req matches] {:status 200 :aview :login})
               :post (-> (fn [req matches form fval]
                           (let [user (get-user db coll (*login-field* form) (:password form))]
                             (if (nil? fval)
                               (if (nil? user)
                                 {:status  400
                                  :aview   :login
                                  :i-flash (:login-invalid flash)
                                  :data    (merge form {:password nil})}
                                 {:status  302
                                  :headers {"Location" (getloc req)}
                                  :cookies {"a" (auth-cookie user)}
                                  :flash   (:login-success flash)
                                  :body    ""})
                               {:status  400
                                :aview   :login
                                :errors  fval
                                :data    form})))
                         lwrap-process-form)})
            lwrap-render-auth-view
            lwrap-if-not-user))
      (route (str url-base "logout")
        {:get (fn [req matches]
                {:status  302
                 :headers {"Location" (getloc req)}
                 :cookies {"a" {:expires "Thu, 01-Jan-1970 00:00:01 GMT" :path "/" :value ""}}
                 :flash   (:logout flash)
                 :body    ""})})
      (if confirm
        (route (str url-base "confirm/:akey")
          {:get (-> (fn [req matches]
                      (if-let [user (get-one db coll {:query {:_confirm_key (:akey matches)}})]
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
                         :body    ""}))
                    lwrap-if-not-user)}))
      (route (str url-base "signup")
        (-> (method-dispatch-handler
              {:get  (fn [req matches] {:status 200 :aview :signup})
               :post (-> (fn [req matches form fval]
                           (if (nil? fval)
                             (if confirm
                                (let [akey (str (UUID/randomUUID))
                                      user (make-user db coll {*login-field* (*login-field* form) :_confirm_key akey} (:password form))]
                                  ((:mailer confirm)
                                   (:from confirm)
                                   (get form (:email-field confirm *login-field*))
                                   (:subject confirm)
                                   ((:mail-template confirm demo-mail-template)
                                      {:data  form
                                       :url   (str (name (:scheme req)) "://" (get (:headers req) "host") url-base "confirm/" akey "?" redir-p "=" (getloc req))}))
                                  {:status  200
                                   :aview   :confirm
                                   :data    form})
                                 (let [user (make-user db coll {*login-field* (*login-field* form)} (:password form))]
                                   {:status  302
                                    :headers {"Location" (getloc req)}
                                    :cookies {"a" (auth-cookie user)}
                                    :flash   (:signup-success flash)
                                    :body    ""}))
                             {:status 400
                              :aview  :signup
                              :errors fval
                              :data   form}))
                         lwrap-process-form)})
              lwrap-render-auth-view
              lwrap-if-not-user)))))
