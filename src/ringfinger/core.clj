; A little monkey-patch for using HttpOnly cookies
(require 'ring.middleware.cookies)
(intern 'ring.middleware.cookies 'set-cookie-attrs
  {:comment "Comment", :comment-url "CommentURL", :discard "Discard",
   :domain "Domain", :max-age "Max-Age", :path "Path", :port "Port",
   :secure "Secure", :version "Version", :expires "Expires",
   :httponly "HttpOnly"})
; -------

(ns ringfinger.core
  "Ringfinger's core: All You Need Is defapp! And if-env.
  Magic starts here."
  (:use clout.core,
        [clojure.string :only [lower-case]],
        (ring.middleware params cookies session stacktrace flash file),
        (ringfinger session security auth), ringfinger.db.inmem))


(defmacro if-env "Checks if the current RING_ENV == env" [env yep nope]
  `(if (= (or (System/getenv "RING_ENV") "development") ~env) ~yep ~nope))

(defn wrap-length
  "Ring middleware for adding Content-Length"
  [handler]
  (fn [req]
    (let [res (handler req)]
      (assoc-in res [:headers "Content-Length"] (str (count (:body res)))))))

(defn wrap-head
  "Ring middleware for handling HEAD requests properly"
  [handler]
  (fn [req]
    (if (= (:request-method req) :head)
      (assoc (handler (assoc req :request-method :get)) :body "")
      (handler req))))

(defn wrap-jsonp
  "Ring middleware for handling JSONP requests"
  [handler callback-param]
  (fn [req]
    (let [res (handler req)]
      (if-let [cb (get-in req [:query-params callback-param])]
        (assoc (assoc-in res [:headers "Content-Type"] "text/javascript; charset=utf-8")
               :body (str cb "(" (:body res) ")"))
        res))))

(defn method-na-handler [req matches]
  {:status  405
   :headers {"Content-Type" "text/plain"}
   :body    "405 Method Not Allowed"})

(def not-found-route
  {:route   (route-compile "/*")
   :handler (fn [req matches]
              {:status  404
               :headers {"Content-Type" "text/plain"}
               :body    "404 Not Found"})})

(def default-handlers
  (zipmap [:get :put :post :delete :options :trace :connect] (repeat method-na-handler)))

(defn route
  "Creates a route accepted by the app function from a URL in Clout (Sinatra-like) format and a map of handlers
  eg. {:get (fn [req matches] {:status 200 :body nil})}"
  [url handlers]
  (let [handlers (merge default-handlers handlers)]
    {:route   (route-compile url)
     :handler (fn [req matches]
                (let [rm       (or (get-in req [:headers "x-http-method-override"])
                                   (get-in req [:query-params "_method"]))
                      method   (if rm (keyword (lower-case rm)) (:request-method req))]
                    ((get handlers method) req matches)))}))

(defn app
  "Creates a Ring handler with given options and routes, automatically wrapped with
  params, session, flash, auth, head, jsonp, length and some security middleware
  (+ stacktrace and file in development env)
  Accepted options:
   :auth-db and :auth-coll -- database and collection for auth middleware, must be the same as the ones you use with auth-routes, the default collection is :ringfinger_auth
   :session-db -- database for session middleware OR
   :session-store -- SessionStore for session middleware, eg. for using the Redis store
   :static-dir -- directory with static files for serving them in development
   :callback-param -- parameter for JSONP callbacks, default is 'callback'
   :csrf-free -- turn off CSRF protection (if you know what you're doing!)
   :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, enabled by default"
  [options & routes]
  (let [allroutes (concat (filter identity (flatten routes)) (list not-found-route))
        rmf (if (= (:memoize-routing options true) true) (memoize route-matches) route-matches)
        h (-> (fn [req]
                (let [route (first (filter #(rmf (:route %) req) allroutes))]
                  ((:handler route) req (rmf (:route route) req))))
              (wrap-auth {:db (:auth-db options inmem) :coll (:auth-coll options :ringfinger_auth)})
              wrap-flash)
        h (if (:csrf-free options) h (wrap-csrf h))
        h (-> h
              (wrap-session {:store (:session-store options (db-store (:session-db options inmem)))
                             :cookie-attrs {:httponly true}
                             :cookie-name "s"})
              (wrap-jsonp (:callback-param options "callback"))
              wrap-params
              wrap-length
              wrap-sec-headers
              wrap-head
              wrap-refcheck
              )]
    (if-env "development"
      (-> h
          (wrap-file (:static-dir options "static"))
          wrap-stacktrace)
      h)))

(defmacro defapp "Short for (def nname (app options & routes))" [nname options & routes]
  (intern *ns* nname (eval `(app ~options ~@routes))))
