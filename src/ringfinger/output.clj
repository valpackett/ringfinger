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

(defmacro to-xml [data]
  `(prxml [:response (cond
     (map? ~data) (map vec (errors-or-data ~data)) ; only errors on invalid PUTs
     :else (map (fn [entry#] [:entry (map vec entry#)]) ~data))]))

(def xml  (reify Output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "application/xml; charset=utf-8"}
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" (with-out-str (to-xml data)))})))

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
