(ns ringfinger.test.security
  (:use (ringfinger core resource db fields), ringfinger.db.inmem,
        midje.sweet, ring.mock.request))

(defresource todos
  {:db inmem
   :pk :body}
  [:body  (required) "should be present"])

(defapp testapp {:static-dir "src"} todos)

(fact (:status (testapp (header (request :delete "/todos/test") "Referer" "http://crackersite.tld/wtf.php"))) => 403)
