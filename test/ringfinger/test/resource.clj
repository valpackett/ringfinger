(ns ringfinger.test.resource
  (:use (ringfinger auth core resource db validation), ringfinger.db.inmem,
        clojure.test, ring.mock.request)
  (:import org.apache.commons.codec.binary.Base64))

(defresource todos
  {:store inmem
   :pk    :body}
  [:body  (required) "should be present"])

(defresource owned
  {:store inmem
   :pk :name
   :owner-field :owner}
  [:name (required) "hey where's the name?"])

(defapp testapp
  {:static-dir "src"
   :fixed-salt "salt"}
  todos, owned)

(defn authd [req]
  (header req "Authorization" (str "Basic " (Base64/encodeBase64String (. "test:demo" getBytes)))))

(deftest t-create
  (let [res (testapp (body (request :post "/todos?format=json")
                           {:body  "test"
                            :state false}))]
    (is (= (:status res 302)))
    (is (= (get (:headers res) "Location") "/todos/test?format=json")))
  (is (= (testapp (body (request :post "/todos?format=json") {:state false}))
         {:status  400
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "{\"body\":[\"should be present\"]}"}))
  (is (= (testapp (header (request :post "/todos") "Accept" "application/xml"))
         {:status  400
          :headers {"Content-Type" "application/xml; charset=utf-8"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><body><error>should be present</error></body></response>"}))

  (is (= (:status (testapp (body (authd (request :post "/owned?format=json")) {:name "sup"}))) 302)))

(deftest t-update
  (let [res (testapp (body (request :put "/todos/test?format=json")
                           {:body  "test"
                            :state true}))]
    (is (= (:status res) 302))
    (is (= (get (:headers res) "Location") "/todos/test?format=json")))
  (is (= (:status (testapp (body (request :put "/owned/sup?format=json") {:name "hacked"}))) 403))
  (is (= (:status (testapp (body (authd (request :put "/owned/sup?format=json")) {:name "wassup"}))) 302)))

(deftest t-show
  (is (= (testapp (request :get "/todos/test?format=json"))
         {:status  200
          :headers {"Content-Type" "application/json; charset=utf-8"}
          :body    "{\"state\":true,\"body\":\"test\"}"}))
  (is (= (testapp (header (request :get "/todos/test") "Accept" "application/xml"))
          {:status  200
          :headers {"Content-Type" "application/xml; charset=utf-8"}
          :body    "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><state>true</state><body>test</body></response>"}))
  (is (= (:body (testapp (request :get "/owned/wassup?format=json")))
         "{\"owner\":\"test\",\"name\":\"wassup\"}")))

(deftest t-index
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

(deftest t-delete
  (let [res (testapp (request :delete "/todos/test?format=json"))]
    (is (= (:status res) 302))
    (is (= (get (:headers res) "Location") "/todos")))

  (is (= (get-one inmem :todos {:body "test"}) nil)))

(defn test-ns-hook []
  (make-user inmem :ringfinger_auth {:username "test"} "demo" "salt")
  (t-create)
  (t-update)
  (t-show)
  (t-index)
  (t-delete)
  (reset-inmem-db))
