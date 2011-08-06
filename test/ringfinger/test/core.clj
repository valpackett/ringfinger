(ns ringfinger.test.core
  (:use ringfinger.core, clojure.test, ring.mock.request))

(defapp testapp {:static-dir "src"}
  (route "/method"
    {:get (fn [req matches]
            {:status  200
             :headers {"Content-Type" "text/plain"}
             :body    ":get"})})
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

(deftest method-override
  (is (= (:body (testapp (header (request :delete "/method") "X-HTTP-Method-Override" "GET"))) ":get"))
  (is (= (:body (testapp (request :post "/method?_method=get"))) ":get")))

(deftest jsonp
  (let [res (testapp (request :get "/method?format=json&callback=my_cb"))]
    (is (= (get-in res [:headers "Content-Type"] "text/javascript; charset=utf-8")))
    (is (= (:body res) "my_cb(:get)")))) ; bad example :-)

(deftest wrong-url
  (is (= (:status (testapp (request :get "/test"))) 404)))
