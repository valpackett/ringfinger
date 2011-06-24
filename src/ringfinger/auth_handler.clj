(ns ringfinger.auth-handler
  (:use (ringfinger auth core util), ringfinger.db.inmem,
        hiccup.core, valip.core))

(def auth-demo-views
  {:login  (fn [stuff] (let [errs (:errors stuff)] (html [:html
    [:head [:title "Log in"]
           [:style default-style]]
    [:body
     [:h1 "Log in"]
     [:form {:method "post" :action ""}
      [:input  {:type "text"     :name "username" :placeholder "Username"}]
      (if (:username errs) (map (fn [t] [:div {:class "error"} t]) (:username errs)))
      [:input  {:type "password" :name "password" :placeholder "Password"}]
      (if (:password errs) (map (fn [t] [:div {:class "error"} t]) (:username errs)))
      [:button {:type "submit"} "Log in!"]]]])))
   :signup (fn [stuff])})

(defn auth-handlers [custom-options]
  (let [options  (merge {:url-base    "/auth/"
                         :views       auth-demo-views
                         :redir-to    "/"
                         :redir-param "redirect"
                         :db          inmem
                         :coll        :ringfinger_auth
                         :validations (list [:username string? "Shouldn't be empty"]
                                            [:password string? "Shouldn't be empty"])} custom-options)
        views    (:views       options)
        url-base (:url-base    options)
        redir-to (:redir-to    options)
        redir-p  (:redir-param options)
        db       (:db          options)
        coll     (:coll        options)
        valds    (:validations options)
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
                   :body    ((:login views) {:errors {}})}))
         :post (fn [req m]
                 (if-not-user req
                   (let [form (:form-params req)
                         user (get-user db coll (get form "username") (get form "password"))]
                     (if (nil? user)
                       {:status  400
                        :headers {"Content-Type" "text/html; encoding=utf-8"}
                        :body    ((:login views) {:errors {:username ["Wrong username/password"]}})} ; FIXME: global err?
                       {:status  302
                        :headers {"Location" (or (get (:query-params req) redir-p) redir-to)}
                        :session {:username (:username user)}
                        :body    nil}))))})
      )))
