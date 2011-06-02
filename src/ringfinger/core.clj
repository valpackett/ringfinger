(ns ringfinger.core
  (:use clout.core, (ring.middleware params)))

(def routes (ref (list
  {:route (route-compile "/*")
   :handler (fn [req matches]
    {:status  404
     :headers {"Content-Type" "text/plain"}
     :body    "404 Not Found"})})))

(defn method-na-handler [req matches]
  {:status  405
   :headers {"Content-Type" "text/plain"}
   :body    "405 Method Not Allowed"})

(def default-handlers
  {:get      method-na-handler
   :head     method-na-handler
   :options  method-na-handler
   :put      method-na-handler
   :post     method-na-handler
   :delete   method-na-handler})

(defn defroute [url handlers]
  (dosync
    (ref-set routes
      (conj @routes
        {:route   (route-compile url)
         :handler (fn [req matches]
                    (((:request-method req) (merge default-handlers handlers)) req matches))}))))

(defn main-handler [req]
  (let [route
     (first
        (filter (fn [route] (route-matches (:route route) req)) @routes))]
     ((:handler route) req (route-matches (:route route) req))))

(def app
  (-> main-handler
      wrap-params))
