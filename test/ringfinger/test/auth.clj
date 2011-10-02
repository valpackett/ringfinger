(ns ringfinger.test.auth
  (:use midje.sweet, ring.mock.request
        ringfinger.db.inmem, (ringfinger core auth auth-routes))
  (:import org.apache.commons.codec.digest.DigestUtils))

(with-salt "salt"
  (make-user inmem :test_auth {:username "test"} "demo")

  (facts "about reading user info"
    (let [usr (get-user inmem :test_auth "test" "demo")]
      (:username usr) => "test"
      (:password_hash usr) => (DigestUtils/sha256Hex (str (:password_salt usr) "saltdemo")))))

(fact
  (binding [*request* (header (request :post "/auth/login") "Referer" "http://localhost/demo")]
    (get-action "redirect")) => "/auth/login?redirect=%2Fdemo")

(reset-inmem-db)
