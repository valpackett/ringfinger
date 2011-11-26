(ns restfinger.test
  (:use (basefinger core inmem),
        formfinger.fields,
        restfinger.core,
        authfinger.core,
        corefinger.core,
        midje.sweet, ring.mock.request))

(make-user inmem :ringfinger_auth {:username "test"} "demo")

(defresource todos
  {:db inmem
   :pk :body
   :whitelist [:state]}
  [:body (required) "should be present"])

(defresource hooked
  {:db inmem
   :pk :name
   :whitelist [:ondata :onpost :onput]
   :hooks {:data    #(assoc % :ondata "yo")
           :create  #(assoc % :onpost "posted")
           :update  #(assoc % :onput  "put")}}
  [:name (required) ""])

(defresource forbidden
  {:db inmem
   :pk :name
   :forbidden-methods [:delete]}
  [:name (required) ""])

(defresource owned
  {:db inmem
   :pk :name
   :owner-field :owner}
  [:name (required) "hey where's the name?"])

(defapp testapp
  {:static-dir "lib"
   :middleware wrap-auth}
  todos hooked owned forbidden)

(defn authd [req]
  (header req "Authorization" "Basic dGVzdDpkZW1v"))

(facts "about creating"
  (testapp (body (request :post "/todos.json")
                           {:body  "test"
                            :state "false"}))
       => (contains {:status 201
                     :headers (contains {"Location" "/todos/test.json"})})
  (testapp (body (request :post "/todos.json") {:state "false"}))
       => (contains {:status 400
                     :body "{\"body\":[\"should be present\"]}"})
  (:body (testapp (header (request :post "/todos") "Accept" "application/xml")))
       => "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><body><error>should be present</error></body></response>"
  (:status (testapp (body (request :post "/hooked.json") {:name "test"}))) => 201
  (:status (testapp (body (authd (request :post "/owned.json")) {:name "sup"}))) => 201)

(facts "about updating"
  (testapp (body (request :put "/todos/test.json")
                 {:body  "test" :state "on"}))
       => (contains {:status 302 :headers (contains {"Location" "/todos/test.json"})})
  (:status (testapp (body (request :put "/hooked/test.json") {:name "test2"}))) => 302
  (:status (testapp (body (request :put "/owned/sup.json") {:name "hacked"})))  => 403
  (:status (testapp (body (authd (request :put "/owned/sup.json")) {:name "wassup"}))) => 302)

(facts "about reading"
  (:body (testapp (request :get "/todos/test.json"))) => "{\"state\":\"on\",\"body\":\"test\"}"
  (:body (testapp (header (request :get "/todos/test") "Accept" "application/xml")))
          => "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><state>on</state><body>test</body></response>"
  (:body (testapp (request :get "/hooked/test2.json")))
          => "{\"ondata\":\"yo\",\"onpost\":\"posted\",\"name\":\"test2\",\"onput\":\"put\"}"
  ; read is public by default
  (:body (testapp (request :get "/owned/wassup.json")))
          => "{\"name\":\"wassup\",\"owner\":\"test\"}")

(facts "about index"
  (:body (testapp (request :get "/todos.json"))) => "[{\"state\":\"on\",\"body\":\"test\"}]"
  (:body (testapp (request :get "/todos.json?query_state_ne=on"))) => "[]"
  (:body (testapp (header (request :get "/todos") "Accept" "application/xml")))
       => "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response><entry><state>on</state><body>test</body></entry></response>")

(facts "about deleting"
  (testapp (request :delete "/todos/test.json"))
       => (contains {:status 302 :headers (contains {"Location" "/todos"})})
  (:status (testapp (body (request :post "/forbidden.json") {:name "test"}))) => 201
  (:status (testapp (request :delete "/forbidden/test.json"))) => 405
  (get-one inmem :todos {:body "test"}) => nil)

(reset-inmem-db)
