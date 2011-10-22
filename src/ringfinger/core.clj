(ns ringfinger.core
  "The magic starts here. The routing system, some middleware and
   the if-env macro are there."
  (:use clout.core,
        [clojure.string :only [lower-case]],
        [clojure.data.json :only [read-json]],
        (ring.middleware params cookies session stacktrace flash file),
        ring.middleware.session.memory,
        ringfinger.auth,
        basefinger.inmem,
        secfinger)
  (:require [clojure.java.io :as io]))

(def ^:dynamic *request* {:scheme :http})

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

(defn wrap-json-params
  "Ring middleware for parsing JSON params"
  [handler]
  (fn [req]
    (if (and (:content-type req)
             (:body req)
             (.startsWith (:content-type req) "application/json"))
      (let [jsp (read-json (slurp (:body req)) false)]
            (handler (-> req
                         (assoc :form-params jsp)
                         (assoc :params (merge (:params req) jsp)))))
      (handler req))))

(defn wrap-method-override
  "Ring middleware for method overriding (X-HTTP-Method-Override and _method query parameter)"
  [handler]
  (fn [req]
    (let [rm (or (get-in req [:headers "x-http-method-override"])
                 (get-in req [:query-params "_method"]))]
      (handler (assoc req :request-method (if rm (keyword (lower-case rm)) (:request-method req)))))))

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
  eg. {:get (fn [req matches] {:status 200 :body nil})}
  Accepts custom-regexps for finer matching (eg. only numeric ids)"
  ([url handlers] (route url handlers {}))
  ([url handlers custom-regexps]
    (let [handlers (merge default-handlers handlers)]
      {:route   (route-compile url custom-regexps)
       :handler (fn [req matches] ((get handlers (:request-method req)) req matches))})))

(defn app
  "Creates a Ring handler with given options and routes, automatically wrapped with
  params, session, flash, auth, head, jsonp, length, method-override and some security middleware
  (+ stacktrace and file in development env)
  Accepted options:
   :auth-db and :auth-coll -- database and collection for auth middleware, must be the same as the ones you use with auth-routes, the default collection is :ringfinger_auth
   :session-store -- SessionStore for session middleware
   :static-dir -- directory with static files for serving them in development
   :callback-param -- parameter for JSONP callbacks, default is 'callback'
   :csrf-free -- turn off CSRF protection (if you know what you're doing!)
   :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, enabled by default"
  [options & routes]
  (let [allroutes (concat (filter identity (flatten routes)) (list not-found-route))
        rmf (if (= (:memoize-routing options true) true) (memoize route-matches) route-matches)
        h (-> (fn [req]
                (let [route (first (filter #(rmf (:route %) req) allroutes))]
                  (binding [*request* req]
                    ((:handler route) req (rmf (:route route) req)))))
              (wrap-auth {:db (:auth-db options inmem) :coll (:auth-coll options :ringfinger_auth)})
              wrap-flash)
        h (if (:csrf-free options) h (wrap-csrf h))
        h (-> h
              (wrap-session {:store (:session-store options (memory-store))
                             :cookie-attrs {:httponly true}
                             :cookie-name "s"})
              (wrap-jsonp (:callback-param options "callback"))
              wrap-length
              wrap-sec-headers
              wrap-head
              wrap-refcheck
              wrap-method-override
              wrap-json-params
              wrap-params
              )]
    (if-env "development"
      (-> h
          (wrap-file (:static-dir options "static"))
          wrap-stacktrace)
      h)))

(defmacro defapp "Short for (def nname (app options & routes))" [nname options & routes]
  (intern *ns* nname (eval `(app ~options ~@routes))))

(def default-style
  (slurp (io/resource "default.css")))
