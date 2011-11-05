(ns corefinger.core
  "A routing system and more"
  (:use corefinger.middleware
        clout.core,
        (ring.middleware params cookies session stacktrace flash file),
        ring.middleware.session.memory,
        secfinger)
  (:require [clojure.java.io :as io]))

(def ^:dynamic *request* {:scheme :http})

(defmacro if-env "Checks if the current RING_ENV == env" [env yep nope]
  `(if (= (or (System/getenv "RING_ENV") "development") ~env) ~yep ~nope))

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
  params, session, flash, head, jsonp, length, method-override and some security middleware
  (+ stacktrace and file in development env)
  Accepted options:
   :middleware -- your custom additional middleware (use -> to add many)
   :session-store -- SessionStore for session middleware
   :static-dir -- directory with static files for serving them in development
   :callback-param -- parameter for JSONP callbacks, default is 'callback'
   :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, disabled by default"
  [{:keys [middleware session-store static-dir callback-param memoize-routing]
    :or {middleware nil
         session-store (memory-store)
         static-dir "static"
         callback-param "callback"
         memoize-routing false}} & routes]
  (let [allroutes (concat (filter identity (flatten routes)) (list not-found-route))
        rmf (if (= memoize-routing true) (memoize route-matches) route-matches)
        h (-> (fn [req]
                (let [route (first (filter #(rmf (:route %) req) allroutes))]
                  (binding [*request* req]
                    ((:handler route) req (rmf (:route route) req)))))
              wrap-flash)
        ; csrf and auth are added by the user
        ; flash is above to allow accessing the user from flash
        h (if middleware (middleware h) h)
        h (-> h
              (wrap-session {:store session-store
                             :cookie-attrs {:httponly true}
                             :cookie-name "s"})
              (wrap-jsonp callback-param)
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
          (wrap-file static-dir)
          wrap-stacktrace)
      h)))

(defmacro defapp "Short for (def nname (app options & routes))" [nname options & routes]
  (intern *ns* nname (eval `(app ~options ~@routes))))

(def default-style
  (slurp (io/resource "default.css")))
