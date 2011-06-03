(ns ringfinger.db)

(defprotocol database
  (create   [self coll data])
  (get_many [self coll query])
  (get_one  [self coll query])
  (update   [self coll entry data])
  (delete   [self coll entry]))

; For databases w/o built-in filters, eg. inmem
; TODO: implement more of
; http://www.mongodb.org/display/DOCS/Advanced+Queries
(defn- andf ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn make-filter [query]
  (fn [entry]
    (apply andf (map (fn [k v]
      (cond
        (map? v) =
        :else (= (get entry k) v))) (keys query) (vals query)))))
