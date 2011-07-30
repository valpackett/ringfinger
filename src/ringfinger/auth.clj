(ns ringfinger.auth
  (:use ringfinger.db, ringfinger.db.inmem)
  (:require [clojure.contrib.string :as cstr])
  (:import org.apache.commons.codec.digest.DigestUtils,
           org.apache.commons.codec.binary.Base64))

(defn get-user
  "Returns a user from coll in db with given username and password if the password is valid"
  [db coll username password fixed-salt-part]
  (let [user (get-one db coll {:query {:username username}})]
    (if (= (:password_hash user) (DigestUtils/sha256Hex (str (:password_salt user) fixed-salt-part password)))
      (if (nil? (:_confirm_key user)) user nil)
      nil)))

(defn make-user
  "Creates a user in coll in db with given fields (username and whatever you want) and password"
  [db coll user password fixed-salt-part]
  (let [salt (str (rand))]
    (create db coll
      (merge user
        {:password_salt salt
         :password_hash (DigestUtils/sha256Hex (str salt fixed-salt-part password))}))))

(defn wrap-auth
  "Ring middleware that adds :user if there's a user logged in. Supports session/form-based auth and HTTP Basic auth"
  ([handler] (wrap-auth handler {}))
  ([handler options]
   (let [db   (:db   options inmem)
         coll (:coll options :ringfinger_auth)
         salt (:salt options "ringfingerFTW")]
     (fn [req]
       (let [auth-hdr (get (:headers req) "authorization" "")
             session-username (:username (:session req))]
         (handler (assoc req :user
            (cond session-username (let [user (get-one db coll {:query {:username session-username}})]
                                     (if (nil? (:_confirm_key user)) user nil))
                  (cstr/substring? "Basic" auth-hdr)
                    (let [cr (cstr/split #":" (new String (Base64/decodeBase64 (cstr/drop 6 auth-hdr))))]
                      (get-user db coll (first cr) (second cr) salt))
                  :else nil))))))))
