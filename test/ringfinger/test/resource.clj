(ns ringfinger.test.resource
  (:use (ringfinger auth core resource db fields), ringfinger.db.inmem,
        clojure.test, ring.mock.request)
  (:import org.apache.commons.codec.binary.Base64))

(defresource todos
  {:db inmem
   :pk :body
   :whitelist '(:state)}
  [:body (required) "should be present"])

(defresource hooked
  {:db inmem
   :pk :name
   :whitelist '(:ondata :onpost :onput)
   :hooks {:data    #(assoc % :ondata "yo")
           :create  #(assoc % :onpost "posted")
           :update  #(assoc % :onput  "put")}}
  [:name (required) ""])

(defresource owned
  {:db inmem
   :pk :name
   :owner-field :owner}
  [:name (required) "hey where's the name?"])

(defapp testapp
  {:static-dir "src"
   :fixed-salt "salt"}
  todos, hooked, owned)

(defn authd [req]
  (header req "Authorization" (str "Basic " (Base64/encodeBase64String (. "test:demo" getBytes)))))

(deftest t-create
  (let [res (testapp (body (request :post "/todos?format=json")
                           {:body  "test"
                            :state "false"}))]
    (is (= (:status res 201)))
    (is (= (get (:headers res) "Location") "/todos/test?format=json")))
  (let [res (testapp (body (request :post "/todos?format=json") {:state "false"}))]
    (is (= (:status res) 400))
    (is (= (:body res) "{\"body\":[\"should be present\"]}")))
  (is (= (:body (testapp (header (request :post "/todos") "Accept" "application/xml")))
          "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><body><error>should be present</error></body></response>"))
  (is (= (:status (testapp (body (request :post "/hooked?format=json") {:name "test"}))) 201))
  (is (= (:status (testapp (body (authd (request :post "/owned?format=json")) {:name "sup"}))) 201)))

(deftest t-update
  (let [res (testapp (body (request :put "/todos/test?format=json")
                           {:body  "test"
                            :state "true"}))]
    (is (= (:status res) 302))
    (is (= (get (:headers res) "Location") "/todos/test?format=json")))
  (is (= (:status (testapp (body (request :put "/hooked/test?format=json") {:name "test2"}))) 302))
  (is (= (:status (testapp (body (request :put "/owned/sup?format=json") {:name "hacked"}))) 403))
  (is (= (:status (testapp (body (authd (request :put "/owned/sup?format=json")) {:name "wassup"}))) 302)))

(deftest t-show
  (is (= (:body (testapp (request :get "/todos/test?format=json")))
          "{\"body\":\"test\",\"state\":true}"))
  (is (= (:body (testapp (header (request :get "/todos/test") "Accept" "application/xml")))
          "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><body>test</body><state>true</state></response>"))
  (is (= (:body (testapp (request :get "/hooked/test2?format=json")))
         "{\"onput\":\"put\",\"onpost\":\"posted\",\"ondata\":\"yo\",\"name\":\"test2\"}"))
  (is (= (:body (testapp (request :get "/owned/wassup?format=json")))
         "{\"owner\":\"test\",\"name\":\"wassup\"}")))

(deftest t-index
  (is (= (:body (testapp (request :get "/todos?format=json")))
          "[{\"body\":\"test\",\"state\":true}]"))
  (is (= (:body (testapp (request :get "/todos?format=json&query_state_ne=true")))
          "[]"))
  (is (= (:body (testapp (header (request :get "/todos") "Accept" "application/xml")))
          "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><entry><body>test</body><state>true</state></entry></response>")))

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
