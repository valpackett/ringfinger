(ns ringfinger.test.auth
  (:use midje.sweet, ring.mock.request
        ringfinger.db.inmem, (ringfinger auth auth-routes))
  (:import org.apache.commons.codec.digest.DigestUtils))

(make-user inmem :test_auth {:username "test"} "demo" "salt")

(facts "about reading user info"
  (let [usr (get-user inmem :test_auth "test" "demo" "salt")]
    (:username usr) => "test"
    (:password_hash usr) => (DigestUtils/sha256Hex (str (:password_salt usr) "saltdemo"))))

(fact
  (get-action (header (request :post "/auth/login") "Referer" "http://localhost/demo") "redirect")
  => "/auth/login?redirect=/demo")

(reset-inmem-db)
