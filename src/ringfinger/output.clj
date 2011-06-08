(ns ringfinger.output
  (:use clojure.contrib.json, clojure.contrib.prxml,
        [clojure.contrib.string :only [substring?]]))

(defprotocol Output
  (render [self status data]))

(defmacro errors-or-data [data]
  `(let [errs# (:errors ~data)] (if errs# errs# (:data ~data))))

(def json (reify Output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "application/json; charset=utf-8"}
           :body    (json-str (errors-or-data data))})))

; I HATE YOU, XML
(defn- to-xml [data]
  (let [errs (:errors data) dt (:data data)]
    (cond errs      (map (fn [k v] [k (map (fn [t] [:error t]) v)]) (keys errs) (vals errs))
          (map? dt) (map vec dt)
          :else     (map (fn [e] [:entry (map vec e)]) dt))))

(def xml  (reify Output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "application/xml; charset=utf-8"}
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                         (with-out-str (prxml [:response (to-xml data)])))})))

(def outputs (ref {}))
(def views   (ref {}))

(defmacro defoutput [name fun]
  `(dosync (ref-set outputs (merge @outputs {~name (reify Output
     (render [self status# data#] (~fun status# data#)))}))))

(defmacro defview [res action fun]
  `(dosync (ref-set views (merge @views {(str ~res "/" ~action)
     (reify Output
       (render [self status# data#]
         {:status  status#
          :headers {"Content-Type" "text/html; charset=utf-8"}
          :body (~fun data#)}))}))))

(defn getoutput [ctype res action]
  (cond (substring? "html" ctype) (get @views (str res "/" action))
        (substring? "json" ctype) json
        (substring? "xml"  ctype)  xml
        :else (get @outputs ctype)))
