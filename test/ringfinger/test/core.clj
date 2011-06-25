(ns ringfinger.test.core
  (:use ringfinger.core, clojure.test, ring.mock.request))

(defapp 'testapp {:static-dir "src"}
  (route "/test/:a"
    {:get (fn [req matches]
            {:status  200
             :headers {"Content-Type" "text/plain"}
             :body    (str "Yo: " (:a matches))})}))

(deftest right-method
  (is (= (testapp (request :get "/test/hello"))
         {:status  200
          :headers {"Content-Type" "text/plain"}
          :body    "Yo: hello"})))

(deftest wrong-method
  (is (= (testapp (request :post "/test/hello"))
         {:status  405
          :headers {"Content-Type" "text/plain"}
          :body    "405 Method Not Allowed"})))

(deftest wrong-url
  (is (= (testapp (request :get "/test"))
         {:status  404
          :headers {"Content-Type" "text/plain"}
          :body    "404 Not Found"})))
