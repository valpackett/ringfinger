(ns ringfinger.test.resource
  (:use ringfinger.core, ringfinger.resource, ringfinger.db, ringfinger.db.inmem,
        ring.mock.request, clojure.test))

(defresource :todos
  {:store inmem
   :pk    :body}
  {:body  :string
   :state :boolean})

(deftest right-create
  (is (= (app (body (request :post "/todos?format=json") {:body  "test"
                                                          :state false}))
         {:status  302
          :headers {"Location" "/todos/test?format=json"}
          :body    ""})))

(deftest wrong-create
  (is (= (app (body (request :post "/todos?format=json") {:body  false
                                                          :state false}))
         {:status  417
          :headers {"Content-Type" "application/json"}
          :body    "{\"error\": \"body should be a string\"}"})))

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
          :body    "{\"body\":\"test\",\"state\":\"true\"}"})))

(deftest right-delete
  (is (= (app (request :delete "/todos/test?format=json"))
          {:status  200
           :headers {"Content-Type" "application/json"}
           :body    "{}"}))
  (is (= (get_one inmem :todos :body "test") nil)))

(defn test-ns-hook []
;  (wrong-create)
  (right-create)
  (right-update)
  (right-show)
  (right-delete)
  (reset-inmem-db))
