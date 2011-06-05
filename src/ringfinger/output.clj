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

(defn pre-xml [data]
  (if (map? data)
    (map vec data)
    (map (fn [entry] ["entry" (map vec entry)]) data)))

(def xml  (reify output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "application/xml"}
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>" (with-out-str (prxml [:response (pre-xml data)])))})))

(defmacro defoutput [name fun]
  `(def ~name (reify output
     (render [self data#] (~fun data#)))))

(defn getoutput [ctype]
  (cond (substring? "json" ctype) json
        (substring? "xml"  ctype)  xml))
