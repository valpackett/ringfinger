(ns ringfinger.test.resource
  (:use ringfinger.core, ringfinger.resource, ringfinger.db, ringfinger.db.inmem,
        valip.predicates, ring.mock.request, clojure.test))

(defresource :todos
  {:store inmem
   :pk    :body}
  [:body  present? "should be present"])

(deftest right-create
  (is (= (app (body (request :post "/todos?format=json") {:body  "test"
                                                          :state false}))
         {:status  302
          :headers {"Location" "/todos/test?format=json"}
          :body    ""})))

(deftest wrong-create
  (is (= (app (body (request :post "/todos?format=json") {:state false}))
         {:status  400
          :headers {"Content-Type" "application/json"}
          :body    "{\"body\":[\"should be present\"]}"})))

(deftest right-update
  (is (= (app (body (request :put "/todos/test?format=json") {:body  "test"
                                                              :state true}))
         {:status  302
          :headers {"Location" "/todos/test?format=json"}
          :body    ""})))

(deftest right-show
  (is (= (app (request :get "/todos/test?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json"}
          :body    "{\"body\":\"test\",\"state\":\"true\"}"}))
  (is (= (app (header (request :get "/todos/test") "Accept" "text/xml"))
          {:status  200
          :headers {"Content-Type" "application/xml"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><body>test</body><state>true</state></response>"})))

(deftest right-index
  (is (= (app (request :get "/todos?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json"}
          :body "[{\"body\":\"test\",\"state\":\"true\"}]"})))

(deftest right-delete
  (is (= (app (request :delete "/todos/test?format=json"))
          {:status  200
           :headers {"Content-Type" "application/json"}
           :body    "{}"}))
  (is (= (get_one inmem :todos {:body "test"}) nil)))

(defn test-ns-hook []
  (wrong-create)
  (right-create)
  (right-update)
  (right-show)
  (right-index)
  (right-delete)
  (reset-inmem-db))
