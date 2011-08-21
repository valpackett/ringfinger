(ns ringfinger.output
  "The output system used by ringfinger.resource."
  (:use ringfinger.util,
        [clojure.data.json :only [json-str]],
        [clj-yaml.core :only [generate-string]],
        clojure-csv.core))

(defprotocol Output
  (render [self status headers data]))

(defmacro errors-or-data [data]
  `(if-let [errs# (:errors ~data)] errs# (:data ~data)))

(def json (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "application/json; charset=utf-8"} headers)
           :body    (json-str (errors-or-data data))})))

(defn- to-xml [data]
  (let [errs (:errors data)
        dt (:data data)
        mapfn (fn [d]
                (apply str (map #(str "<" (name %1) ">" %2 "</" (name %1) ">") (keys d) (vals d))))]
    (cond errs      (apply str (map (fn [k v]
                                  (apply str "<" (name k) ">"
                                    (reduce #(str %1 "<error>" %2 "</error>") (cons "" v))
                                    "</" (name k) ">")) (keys errs) (vals errs)))
          (map? dt) (mapfn dt)
          :else     (reduce #(str %1 "<entry>" (mapfn %2) "</entry>") (cons "" dt)))))

(def xml  (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "application/xml; charset=utf-8"} headers)
           :body    (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response>"
                         (to-xml data)
                         "</response>")})))

(def csv  (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "text/csv; charset=utf-8"} headers)
           :body    (let [data (:data data [])]
                      (write-csv (map (fn [a] (map #(str (second %)) a)) (if (map? data) [data] data))))})))

(def yaml (reify Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "text/x-yaml; charset=utf-8"} headers)
           :body    (generate-string (errors-or-data data))})))

(deftype HTMLOutput [view default-data] Output
  (render [self status headers data]
          {:status  status
           :headers (merge {"Content-Type" "text/html; charset=utf-8"} headers)
           :body    (view (merge default-data data))}))

(defn html-output [view dd] (HTMLOutput. view dd))

(def getoutput (memoize (fn [ctype custom]
  (let [outputs (merge {"json" json "xml" xml "csv" csv "yaml" yaml} custom)] ; html before xml
    (first
      (filter identity
              (map #(if (substring? %1 ctype) %2)
                   (keys outputs) (vals outputs))))))))
