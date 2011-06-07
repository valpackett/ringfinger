(ns ringfinger.core
  (:use clout.core, (ring.middleware params session),
        ringfinger.session, ringfinger.db.inmem))

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
       :headers (merge (:headers result#) {"Content-Length" (str (count (:body result#)))})
       :body    nil})))

(def default-handlers
  {:get      method-na-handler
   :options  method-na-handler
   :put      method-na-handler
   :post     method-na-handler
   :delete   method-na-handler})

(defmacro route [url handlers]
  `{:route   (route-compile ~url)
    :handler (fn [req# matches#]
               (let [rm#       (first (select-keys (:query-params req#) ["_method"]))
                     handlers# (merge default-handlers ~handlers)
                     method#   (if rm# (keyword (get rm# 1)) (:request-method req#))]
                       ((if (= method# :head) (head-handler (:get handlers#)) (get handlers# method#)) req# matches#)))})

(defn app [options & routes]
  (let [allroutes (concat (flatten routes) (list not-found-handler))]
    (-> (fn [req]
          (let [route (first (filter (fn [route] (route-matches (:route route) req)) allroutes))]
            ((:handler route) req (route-matches (:route route) req))))
        wrap-params
        (wrap-session {:store (or (:session-store options) (db-store inmem))}))))

(defn defapp [nname options & routes]
  (intern *ns* (symbol nname) (app options routes)))
