(ns ringfinger.test.auth
  (:use midje.sweet, ring.mock.request
        basefinger.inmem, (ringfinger core auth auth-routes))
  (:import org.apache.commons.codec.digest.DigestUtils))

(with-salt "salt"
  (make-user inmem :test_auth {:username "test"} "demo")

  (let [usr (get-user inmem :test_auth "test" "demo")]
    (facts "about reading user info"
      (:username usr) => "test"
      (:password_hash usr) => (DigestUtils/sha256Hex (str (:password_salt usr) "saltdemo")))))

(binding [*request* (header (request :post "/auth/login") "Referer" "http://localhost/demo")]
  (fact
    (get-action "redirect")) => "/auth/login?redirect=%2Fdemo")

(reset-inmem-db)
