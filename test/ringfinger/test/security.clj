(ns ringfinger.test.security
  (:require [clojure.contrib.string :as cstr])
  (:use (ringfinger core resource db fields), ringfinger.db.inmem,
        clojure.test, ring.mock.request))

(defresource todos
  {:db inmem
   :pk :body}
  [:body  (required) "should be present"])

(defapp testapp {:static-dir "src"} todos)

(deftest t-csrf
  (let [token (cstr/drop 10 (first (get-in (testapp (header (request :get "/todos") "User-Agent" "Mozilla/5.0")) [:headers "Set-Cookie"])))]
    (is (= (:status (testapp (header (header (body (request :post "/todos") {"body" "test" "csrftoken" token})
                                             "Cookie" "csrftoken=WRONG")
                                             "User-Agent" "Mozilla/5.0"))) 403))
    (is (= (:status (testapp (header (header (body (request :post "/todos") {"body" "test" "csrftoken" token})
                                             "Cookie" (str "csrftoken=" token))
                                             "User-Agent" "Mozilla/5.0"))) 302))))

(deftest t-refcheck
  (is (= (:status (testapp (header (request :delete "/todos/test") "Referer" "http://hackersite.tld/wtf.php")) 403))))
