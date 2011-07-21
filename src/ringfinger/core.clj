(ns ringfinger.core
  (:use clout.core,
        (ring.middleware params session stacktrace flash file),
        (ringfinger session security auth), ringfinger.db.inmem))

(defmacro if-env [env yep nope]
  "Checks if the current RING_ENV = env"
  `(if (= (or (System/getenv "RING_ENV") "development") ~env) ~yep ~nope))

(defn method-na-handler [req matches]
  {:status  405
   :headers {"Content-Type" "text/plain"}
   :body    "405 Method Not Allowed"})

(def not-found-handler
  {:route   (route-compile "/*")
   :handler (fn [req matches]
              {:status  404
               :headers {"Content-Type" "text/plain"}
               :body    "404 Not Found"})})

(defmacro head-handler [get-handler]
  "Creates a handler for HEAD requests from a handler for GET requests"
  `(fn [req# matches#]
    (let [result# (~get-handler req# matches#)]
      {:status  (:status result#)
       :headers (assoc (:headers result#) "Content-Length" (str (count (:body result#))))
       :body    nil})))

(def default-handlers
  {:get      method-na-handler
   :options  method-na-handler
   :put      method-na-handler
   :post     method-na-handler
   :delete   method-na-handler})

(defn route [url handlers]
  "Creates a route accepted by the app function from a url in Clout (Sinatra-like) format and a map of handlers
  eg. {:get (fn [req matches] {:status 200 :body nil})}"
  {:route   (route-compile url)
   :handler (fn [req matches]
              (let [rm       (get (:query-params req) "_method")
                    handlers (merge default-handlers handlers)
                    method   (if rm (keyword rm) (:request-method req))]
                      ((if (= method :head) (head-handler (:get handlers)) (get handlers method)) req matches)))})

(defn app [options & routes]
  "Creates a Ring handler with given options and routes, automatically wrapped with params, session, flash, auth and some security middleware (+ stacktrace and file in development env)
  Accepted options:
   :auth-db and :auth-coll -- database and collection for auth middleware, must be the same as the ones you use with auth-routes, the default collection is :ringfinger_auth
   :fixed-salt -- the fixed part of password hashing salt, must be the same as the one you use with auth-routes. NEVER change this in production!!
   :session-db -- database for session middleware
   :static-dir -- directory with static files for serving them in development
   :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, enabled by default"
  (let [allroutes (concat (flatten routes) (list not-found-handler))
        rmf (if (= (:memoize-routing options true) true) (memoize route-matches) route-matches)
        h (-> (fn [req]
                (let [route (first (filter #(rmf (:route %) req) allroutes))]
                  ((:handler route) req (rmf (:route route) req))))
              (wrap-auth {:db (:auth-db options inmem) :coll (:auth-coll options :ringfinger_auth) :salt (:fixed-salt options "ringfingerFTW")})
              wrap-flash
              wrap-csrf
              (wrap-session {:store (:session-db options (db-store inmem))})
              wrap-params
              wrap-refcheck
              )]
    (if-env "development"
      (-> h
          (wrap-file (:static-dir options "static"))
          wrap-stacktrace)
      h)))

(defmacro defapp [nname options & routes]
  "Short for (def nname (app options routes*))"
  (intern *ns* nname (eval `(app ~options ~@routes))))
