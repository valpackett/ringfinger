(ns ringfinger.output
  (:use clojure.contrib.json, clojure.contrib.prxml,
        [clojure.contrib.string :only [substring?]]))

(defprotocol output
  (render [self status data]))

(def json (reify output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "application/json"}
           :body    (json-str data)})))

(defmacro to-xml [data]
  `(let [errs# (:errors ~data)]
     (prxml [:response (cond
       (not (nil? errs#)) (map (fn [fld# err#] [(keyword fld#) (map (fn [estr#] [:error estr#]) err#)]) (keys errs#) (vals errs#))
       (map? ~data) (map vec ~data)
       :else (map (fn [entry#] [:entry (map vec entry#)]) ~data))])))

(def xml  (reify output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "application/xml"}
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" (with-out-str (to-xml data)))})))

(def outputs (ref {}))
(def views   (ref {}))

(defmacro defoutput [name fun]
  `(dosync (ref-set outputs (merge @outputs {~name (reify output
     (render [self status# data#] (~fun status# data#)))}))))

(defmacro defview [res action fun]
  `(dosync (ref-set views (merge @views {(str ~res "/" ~action)
     (reify output
       (render [self status# data#]
         {:status  status#
          :headers {"Content-Type" "text/html"}
          :body (~fun data#)}))}))))

(defn getoutput [ctype res action]
  (cond (substring? "html" ctype) (get @views (str res "/" action))
        (substring? "json" ctype) json
        (substring? "xml"  ctype)  xml
        :else (get @outputs ctype)))
