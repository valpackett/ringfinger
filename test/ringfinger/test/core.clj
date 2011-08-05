(ns ringfinger.test.core
  (:use ringfinger.core, clojure.test, ring.mock.request))

(defapp testapp {:static-dir "src"}
  (route "/test/:a"
    {:get (fn [req matches]
            {:status  200
             :headers {"Content-Type" "text/plain"}
             :body    (str "Yo: " (:a matches))})}))

(deftest right-method
  (is (= (testapp (request :get "/test/hello"))
         {:status  200
          :headers {"Content-Length" "9"
                    "Content-Type" "text/plain"}
          :body    "Yo: hello"})))

(deftest wrong-method
  (is (= (:status (testapp (request :post "/test/hello"))) 405)))

(deftest wrong-url
  (is (= (:status (testapp (request :get "/test"))) 404)))
