(ns ringfinger.core
  (:use clout.core))

(def routes (ref (list
  {:route (route-compile "/*")
   :handler (fn [req matches]
    {:status 404
     :headers {"Content-Type" "text/plain"}
     :body "404 Not Found"})})))

(defn defroute [route]
  (dosync (ref-set routes (conj @routes route))))

(defn app [req]
  (let [route
     (first
        (filter (fn [route] (route-matches (:route route) req)) @routes))]
     ((:handler route) req (route-matches (:route route) req))))
