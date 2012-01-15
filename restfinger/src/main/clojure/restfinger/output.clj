(ns restfinger.output
  "The output system used by ringfinger.resource."
  (:use toolfinger,
        [clojure.data.json :only [json-str]],
        [clj-yaml.core :only [generate-string]],
        clojure-csv.core))

(defn render [formatter status data]
  (let [result (formatter data)]
    {:status status
     :headers {"Content-Type" (:ctype result)}
     :body (:body result)}))

(defmacro errors-or-data [data]
  `(if-let [errs# (:errors ~data)] errs# (:data ~data)))

(defn json [data]
  {:ctype "application/json"
   :body  (json-str (errors-or-data data))})

(defn- to-xml [data]
  (let [errs (:errors data)
        dt (:data data)
        mapfn (fn [d]
                (apply str (map #(str "<" (name %1) ">" %2 "</" (name %1) ">") (keys d) (vals d))))]
        (cond errs (apply str (map (fn [k v]
                                (apply str "<" (name k) ">"
                                  (reduce #(str %1 "<error>" %2 "</error>") (cons "" v))
                                  "</" (name k) ">")) (keys errs) (vals errs)))
          (map? dt) (mapfn dt)
          :else     (reduce #(str %1 "<entry>" (mapfn %2) "</entry>") (cons "" dt)))))

(defn xml [data]
  {:ctype "application/xml"
   :body  (str "<?xml version=\"1.0\" encoding=\"UTF-8\" ?><response>"
               (to-xml data)
               "</response>")})

(defn csv [data]
  {:ctype "text/csv"
   :body  (let [data (:data data [])]
            (write-csv (map (fn [a] (map #(str (second %)) a)) (if (map? data) [data] data))))})

(defn yaml [data]
  {:ctype "text/x-yaml"
   :body  (generate-string (errors-or-data data))})

(defn html-output [view dd]
  (fn [data]
    {:ctype "text/html"
     :body (view (merge dd data))}))

(def getoutput (memoize (fn [ctype custom]
  (let [outputs (merge {"json" json "xml" xml "csv" csv "yaml" yaml} custom)] ; html before xml
    (first
      (filter identity
              (map #(if (substring? %1 ctype) %2)
                   (keys outputs) (vals outputs))))))))
