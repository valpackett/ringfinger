(ns ringfinger.output
  "The output system used by ringfinger.resource."
  (:use (clojure.contrib json prxml),
        clojure-csv.core,
        [clojure.contrib.string :only [substring?]]))

(defprotocol Output
  (render [self status headers data]))

(defmacro errors-or-data [data]
  `(if-let [errs# (:errors ~data)] errs# (:data ~data)))

(def json (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "application/json; charset=utf-8"} headers)
           :body    (json-str (errors-or-data data))})))

; I HATE YOU, XML
(defn- to-xml [data]
  (let [errs (:errors data) dt (:data data)]
    (cond errs      (map (fn [k v] [k (map (fn [t] [:error t]) v)]) (keys errs) (vals errs))
          (map? dt) (map vec dt)
          :else     (map (fn [e] [:entry (map vec e)]) dt))))

(def xml  (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "application/xml; charset=utf-8"} headers)
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>"
                         (with-out-str (prxml [:response (to-xml data)])))})))

(def csv  (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "text/csv; charset=utf-8"} headers)
           :body    (let [data (:data data [])]
                      (write-csv (map (fn [a] (map #(str (second %)) a)) (if (map? data) [data] data))))})))

(deftype HTMLOutput [view default-data] Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "text/html; charset=utf-8"} headers)
           :body    (view (merge default-data data))}))

(defn html-output [view dd] (HTMLOutput. view dd))

(def getoutput (memoize (fn [ctype custom]
  (let [outputs (merge {"json" json "xml" xml "csv" csv} custom)] ; html before xml
    (first
      (filter identity
              (map #(if (substring? %1 ctype) %2)
                   (keys outputs) (vals outputs))))))))
