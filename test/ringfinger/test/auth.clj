(ns ringfinger.test.auth
  (:use clojure.test, ringfinger.db.inmem, ringfinger.auth)
  (:import org.apache.commons.codec.digest.DigestUtils))

(deftest read-user
  (let [usr (get-user inmem :test_auth "test" "demo")]
    (is (= (:username usr) "test"))
    (is (= (:password_hash usr) (DigestUtils/sha256Hex (str (:password_salt usr) "demo"))))))

(defn test-ns-hook []
  (make-user inmem :test_auth {:username "test"} "demo")
  (read-user)
  (reset-inmem-db))
