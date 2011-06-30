(ns ringfinger.test.auth
  (:use clojure.test, ring.mock.request
        ringfinger.db.inmem, (ringfinger auth auth-handler))
  (:import org.apache.commons.codec.digest.DigestUtils))

(deftest read-user
  (let [usr (get-user inmem :test_auth "test" "demo" "salt")]
    (is (= (:username usr) "test"))
    (is (= (:password_hash usr) (DigestUtils/sha256Hex (str (:password_salt usr) "saltdemo"))))))

(deftest referer
  (is (= (get-action (header (request :post "/auth/login") "Referer" "http://localhost/demo") "redirect")
         "/auth/login?redirect=/demo")))

(defn test-ns-hook []
  (make-user inmem :test_auth {:username "test"} "demo" "salt")
  (read-user)
  (referer)
  (reset-inmem-db))
