(ns ringfinger.auth
  (:use ringfinger.db, ringfinger.db.inmem)
  (:require [clojure.contrib.string :as cstr])
  (:import org.apache.commons.codec.digest.DigestUtils,
           org.apache.commons.codec.binary.Base64))

(defn- get-user [db coll username password]
  (let [user (get_one db coll {:username username})]
    (if (= (:password user) password)
      user
      nil)))

(defn wrap-auth
  ([handler] (wrap-auth handler {:db inmem, :coll :ringfinger_auth}))
  ([handler options]
   (let [db   (:db options)
         coll (:coll options)]
     (fn [req]
       (let [auth-hdr (or (get (:headers req) "authorization") "")]
         (handler (assoc req :user
            (cond (cstr/substring? "Basic" auth-hdr)
                    (let [cr (cstr/split #":" (new String (Base64/decodeBase64 (cstr/drop 6 auth-hdr))))]
                      (get-user db coll (first cr) (second cr)))
                  :else nil))))))))
