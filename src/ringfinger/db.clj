(ns ringfinger.db
  (:use ringfinger.util))

(defprotocol database
  (create   [self coll data])
  (get_many [self coll query])
  (get_one  [self coll query])
  (update   [self coll entry data])
  (delete   [self coll entry]))

; For databases w/o built-in filters, eg. inmem
; TODO: implement more of
; http://www.mongodb.org/display/DOCS/Advanced+Queries
(defn- make-query [kk vv]
  (apply andf (map (fn [k v]
    (cond
      (= k :$not)    (not (make-query kk v))
      (= k :$lt)     (< kk v)
      (= k :$lte)    (<= kk v)
      (= k :$gt)     (> kk v)
      (= k :$gte)    (>= kk v)
      (= k :$exists) (if (false? v) (nil? kk) (not (nil? kk)))
      (= k :$ne)     (not (= kk v))
      :else          (= kk v))) (keys vv) (vals vv))))

(defn make-filter [query]
  (fn [entry]
    (apply andf (map (fn [k v]
      (cond
        (map? v) (make-query (get entry k) v)
        :else (= (get entry k) v))) (keys query) (vals query)))))
