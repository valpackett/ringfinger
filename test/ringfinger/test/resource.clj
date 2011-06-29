(ns ringfinger.test.resource
  (:use (ringfinger core resource db validation), ringfinger.db.inmem,
        clojure.test, ring.mock.request))

(defresource "todos"
  {:store inmem
   :pk    :body}
  [:body  (required) "should be present"])

(defapp 'testapp {:static-dir "src"} todos)

(deftest right-create
  (let [res (testapp (body (request :post "/todos?format=json")
                           {:body  "test"
                            :state false}))]
    (is (= (:status res 302)))
    (is (= (get (:headers res) "Location") "/todos/test?format=json"))))

(deftest wrong-create
  (is (= (testapp (body (request :post "/todos?format=json") {:state false}))
         {:status  400
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "{\"body\":[\"should be present\"]}"}))
  (is (= (testapp (header (request :post "/todos") "Accept" "application/xml"))
         {:status  400
          :headers {"Content-Type" "application/xml; charset=utf-8"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><body><error>should be present</error></body></response>"})))

(deftest right-update
  (let [res (testapp (body (request :put "/todos/test?format=json")
                           {:body  "test"
                            :state true}))]
    (is (= (:status res) 302))
    (is (= (get (:headers res) "Location") "/todos/test?format=json"))))

(deftest right-show
  (is (= (testapp (request :get "/todos/test?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "{\"state\":true,\"body\":\"test\"}"}))
  (is (= (testapp (header (request :get "/todos/test") "Accept" "application/xml"))
          {:status  200
          :headers {"Content-Type" "application/xml; charset=utf-8"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><state>true</state><body>test</body></response>"})))

(deftest right-index
  (is (= (testapp (request :get "/todos?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "[{\"state\":true,\"body\":\"test\"}]"}))
  (is (= (testapp (request :get "/todos?format=json&state_ne=true"))
         {:status  200
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "[]"}))
  (is (= (testapp (header (request :get "/todos") "Accept" "application/xml"))
         {:status  200
          :headers {"Content-Type" "application/xml; charset=utf-8"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><entry><state>true</state><body>test</body></entry></response>"})))

(deftest right-delete
  (let [res (testapp (request :delete "/todos/test?format=json"))]
    (is (= (:status res) 302))
    (is (= (get (:headers res) "Location") "/todos")))
  (is (= (get-one inmem :todos {:body "test"}) nil)))

(defn test-ns-hook []
  (wrong-create)
  (right-create)
  (right-update)
  (right-show)
  (right-index)
  (right-delete)
  (reset-inmem-db))
