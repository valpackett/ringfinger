(ns ringfinger.test.resource
  (:use ringfinger.core, ringfinger.resource, ringfinger.db, ringfinger.db.inmem,
        valip.predicates, ring.mock.request, clojure.test))

(defresource "todos"
  {:store inmem
   :pk    :body}
  [:body  present? "should be present"])

(defapp 'testapp {} todos)

(deftest right-create
  (is (= (testapp (body (request :post "/todos?format=json") {:body  "test"
                                                              :state false}))
         {:status  302
          :headers {"Location" "/todos/test?format=json"}
          :body    ""})))

(deftest wrong-create
  (is (= (testapp (body (request :post "/todos?format=json") {:state false}))
         {:status  400
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "{\"body\":[\"should be present\"]}"})))

(deftest right-update
  (is (= (testapp (body (request :put "/todos/test?format=json") {:body  "test"
                                                                  :state true}))
         {:status  302
          :headers {"Location" "/todos/test?format=json"}
          :body    ""})))

(deftest right-show
  (is (= (testapp (request :get "/todos/test?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "{\"state\":true,\"body\":\"test\"}"}))
  (is (= (testapp (header (request :get "/todos/test") "Accept" "text/xml"))
          {:status  200
          :headers {"Content-Type" "application/xml; charset=utf-8"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><state>true</state><body>test</body></response>"})))

(deftest right-index
  (is (= (testapp (request :get "/todos?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body "[{\"state\":true,\"body\":\"test\"}]"})))

(deftest right-delete
  (is (= (testapp (request :delete "/todos/test?format=json"))
          {:status  302
           :headers {"Location" "/todos"}
           :body    ""}))
  (is (= (get_one inmem :todos {:body "test"}) nil)))

(defn test-ns-hook []
  (wrong-create)
  (right-create)
  (right-update)
  (right-show)
  (right-index)
  (right-delete)
  (reset-inmem-db))
