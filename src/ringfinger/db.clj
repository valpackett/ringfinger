(ns ringfinger.db)

(defprotocol database
  (create   [self coll data])
;  (get_many [self coll field value param])
  (get_one  [self coll field value])
  (update   [self coll entry data])
  (delete   [self coll entry]))
