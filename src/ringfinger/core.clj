(ns ringfinger.core
  (:use clout.core,
        (ring.middleware params session stacktrace flash file),
        (ringfinger session auth), ringfinger.db.inmem))

(defmacro if-env [env yep nope]
  `(if (= (get (System/getenv) "RING_ENV" "development") ~env) ~yep ~nope))

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
  {:route   (route-compile url)
   :handler (fn [req matches]
              (let [rm       (get (:query-params req) "_method")
                    handlers (merge default-handlers handlers)
                    method   (if rm (keyword rm) (:request-method req))]
                      ((if (= method :head) (head-handler (:get handlers)) (get handlers method)) req matches)))})

(defn app [options & routes]
  (let [allroutes (concat (flatten routes) (list not-found-handler))
        h (-> (fn [req]
                (let [route (first (filter #(route-matches (:route %) req) allroutes))]
                  ((:handler route) req (route-matches (:route route) req))))
              (wrap-auth {:db (:auth-db options inmem) :coll (:auth-coll options :ringfinger_auth)})
              wrap-flash
              (wrap-session {:store (:session-store options (db-store inmem))})
              wrap-params
              )]
    (if-env "development"
      (-> h
          (wrap-file (:static-dir options "static"))
          wrap-stacktrace)
      h)))

(defmacro defapp [nname options & routes]
  (intern *ns* nname (eval `(app ~options ~@routes))))
