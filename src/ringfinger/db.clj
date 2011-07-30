(ns ringfinger.db
  (:use ringfinger.util))

(defprotocol Database
  (create   [self coll data])
  (get-many [self coll options])
  (get-one  [self coll options])
  (update   [self coll entry data])
  (delete   [self coll entry]))

; For databases w/o built-in filters, eg. inmem
; TODO: implement more of
; http://www.mongodb.org/display/DOCS/Advanced+Queries
(defn- make-query [kk vv]
  (apply andf (map (fn [k v]
    (condp = k
      :$not    (not (make-query kk v))
      :$lt     (< kk v)
      :$lte    (<= kk v)
      :$gt     (> kk v)
      :$gte    (>= kk v)
      :$exists (if (false? v) (nil? kk) (not (nil? kk)))
      :$ne     (not (= kk v))
               (= kk v))) (keys vv) (vals vv))))

(defn make-filter [query]
  (fn [entry]
    (apply andf (map (fn [k v]
      (cond
        (map? v) (make-query (get entry k) v)
        :else (= (get entry k) v))) (keys query) (vals query)))))
