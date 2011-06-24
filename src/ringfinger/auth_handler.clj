(ns ringfinger.auth-handler
  (:use (ringfinger auth core util validation), ringfinger.db.inmem,
        hiccup.core, valip.core)
  (:require [clojure.contrib.string :as cstr]))

(defn get-action [req nm]
  (str (:uri req)
       (let [hdrs (:headers req)
             dmn  (str (cstr/as-str (:scheme req)) "://" (get hdrs "host"))
             rf   (or (get hdrs "referer") "")]
         (if (cstr/substring? dmn rf)
           (str "?" nm "=" (cstr/drop (count dmn) rf))
           ""))))

(def auth-demo-views
  {:login  (fn [stuff] (let [errs (:errors stuff)] (html [:html
    [:head [:title "Log in"]
           [:style default-style]]
    [:body
     [:h1 "Log in"]
     [:form {:method "post" :action (:action stuff)}
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
                         :validations (list [:username (required)     "Shouldn't be empty"]
                                            [:password (required)     "Shouldn't be empty"]
                                            [:password (alphanumeric) "Should be alphanumeric"])} custom-options)
        views    (:views       options)
        url-base (:url-base    options)
        redir-to (:redir-to    options)
        redir-p  (:redir-param options)
        db       (:db          options)
        coll     (:coll        options)
        valds    (:validations options)
        hvalds   (map #(assoc % 1 (:clj (second %))) valds)
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
                   :body    ((:login views) {:errors {} :action (get-action req redir-p)})}))
         :post (fn [req m]
                 (if-not-user req
                   (let [form (keywordize (:form-params req))
                         fval (apply validate form hvalds)
                         user (get-user db coll (:username form) (:password form))]
                     (if (nil? fval)
                       (if (nil? user)
                         {:status  400
                          :headers {"Content-Type" "text/html; encoding=utf-8"}
                          :body    ((:login views) {:errors {:username ["Wrong username/password"]} :action (get-action req redir-p)})}
                         {:status  302
                          :headers {"Location" (or (get (:query-params req) redir-p) redir-to)}
                          :session {:username (:username user)}
                          :body    nil})
                       {:status  400
                        :headers {"Content-Type" "text/html; encoding=utf-8"}
                        :body    ((:login views) {:errors fval :action (get-action req redir-p)})}))))}
      ))))
