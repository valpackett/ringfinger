(ns ringfinger.auth
  "Low-level authorization API (creating users, getting users after checking) and the auth middleware."
  (:use (ringfinger db security util), ringfinger.db.inmem,
        [clojure.string :only [split]])
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
  (let [salt (secure-rand)]
    (create db coll
      (merge user
        {:auth_token    (secure-rand 64)
         :password_salt salt
         :password_hash (DigestUtils/sha256Hex (str salt fixed-salt-part password))}))))

(defn wrap-auth
  "Ring middleware that adds :user if there's a user logged in. Supports session/form-based auth and HTTP Basic auth"
  ([handler] (wrap-auth handler {}))
  ([handler options]
   (let [db   (:db   options inmem)
         coll (:coll options :ringfinger_auth)
         salt (:salt options "ringfingerFTW")]
     (fn [req]
       (let [auth-hdr (get-in req [:headers "authorization"] "")
             cookie-token (get-in req [:cookies "a" :value])]
         (handler (assoc req :user
            (cond cookie-token (let [user (get-one db coll {:query {:auth_token cookie-token}})]
                                 (if (nil? (:_confirm_key user)) user nil))
                  (substring? "Basic" auth-hdr)
                    (let [cr (split (new String (Base64/decodeBase64 (str-drop 6 auth-hdr))) #":")]
                      (get-user db coll (first cr) (second cr) salt))
                  :else nil))))))))
