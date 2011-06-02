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

(def xml  (reify output
  (render [self status data]
          {:status  status
           :headers {"Content-Type" "text/xml"}
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>/n" (with-out-str (prxml [:response (map vec data)])))})))

(defmacro defoutput [name fun]
  `(def ~name (reify output
     (render [self data#] (~fun data#)))))

(defn getoutput [ctype]
  (cond (substring? "json" ctype) json
        (substring? "xml"  ctype)  xml))
