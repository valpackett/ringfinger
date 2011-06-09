(ns ringfinger.db.inmem
  "In-memory data storage FOR TESTING USE ONLY"
  (:use (ringfinger db util)))

(def base (ref {}))

(def inmem (reify Database
  (create [self coll data]
    (dosync
      (if (false? (coll base))
        (ref-set base (assoc @base coll [data]))
        (ref-set base (assoc @base coll (conj (get @base coll) data))))))
  (get_many [self coll query]
    (filter (make-filter query) (get @base coll)))
  (get_one [self coll query]
    (first (get_many self coll query)))
  (update  [self coll entry data]
    (dosync
      (ref-set base (assoc @base coll (replace {entry (merge entry data)} (get @base coll))))))
  (delete [self coll entry]
    (dosync
      (ref-set base (assoc @base coll (remove (curry = entry) (get @base coll))))))))

(defn reset-inmem-db []
  (dosync (ref-set base {})))
