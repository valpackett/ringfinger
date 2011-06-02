(ns ringfinger.db.inmem
  "In-memory data storage FOR TESTING USE ONLY"
  (:use ringfinger.db))

(def base (ref {}))

(def inmem (reify database
  (create [self coll data]
    (dosync
      (if (false? (coll base))
        (ref-set base (assoc @base coll [data]))
        (ref-set base (assoc @base coll (conj (get @base coll) data))))))
  (get_one [self coll field value]
    (first (filter (fn [entry] (= (get entry field) value)) (get @base coll))))
  (update  [self coll entry data]
    (dosync
      (ref-set base (assoc @base coll (replace {entry (merge entry data)} (get @base coll))))))
  (delete [self coll entry]
    (dosync
      (ref-set base (assoc @base coll (remove (fn [centry] (if (= entry centry) true false)) (get @base coll))))))))
