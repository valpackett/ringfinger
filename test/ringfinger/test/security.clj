(ns ringfinger.test.security
  (:require [clojure.contrib.string :as cstr])
  (:use (ringfinger core resource db fields), ringfinger.db.inmem,
        clojure.test, ring.mock.request))

(defresource todos
  {:db inmem
   :pk :body}
  [:body  (required) "should be present"])

(defapp testapp {:static-dir "src"} todos)

(deftest t-refcheck
  (is (= (:status (testapp (header (request :delete "/todos/test") "Referer" "http://crackersite.tld/wtf.php")) 403))))
