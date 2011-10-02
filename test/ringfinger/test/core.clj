(ns ringfinger.test.core
  (:use ringfinger.core, midje.sweet, ring.mock.request))

(defapp testapp {:static-dir "src"}
  (route "/method"
    {:get (fn [req matches]
            {:status  200
             :headers {"Content-Type" "application/json; charset=utf-8"}
             :body    "{\"method\":\"get\"}"})})
  (route "/test/:a"
    {:get (fn [req matches]
            {:status  200
             :headers {"Content-Type" "text/plain"}
             :body    (str "Yo: " (:a matches))})}))

(facts "about requests in general"
  (testapp (request :get "/test/hello")) =>
    (contains
      {:status  200
       :headers (contains {"Content-Length" "9"
                           "Content-Type" "text/plain"})
       :body    "Yo: hello"})
  (:status (testapp (request :post "/test/hello"))) => 405
  (:body   (testapp (header (request :delete "/method") "X-HTTP-Method-Override" "GET"))) => "{\"method\":\"get\"}"
  (:body   (testapp (request :post "/method?_method=get"))) => "{\"method\":\"get\"}"
  (:status (testapp (request :get "/test"))) => 404)

(facts "about JSONP"
  (testapp (request :get "/method?callback=my_cb")) =>
    (contains
      {:headers (contains {"Content-Type" "text/javascript; charset=utf-8"})
       :body "my_cb({\"method\":\"get\"})"}))
