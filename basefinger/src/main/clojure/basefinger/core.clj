(ns basefinger.core
  "Document-oriented database abstraction."
  (:use toolfinger,
        [clojure.string :only [split]]))

(defprotocol Database
  (create      [self coll data])
  (create-many [self coll data])
  (get-many    [self coll options])
  (get-one     [self coll options])
  (get-count   [self coll options])
  (update      [self coll entry data replace?])
  (modify      [self coll entry modifiers])
  (delete      [self coll entry]))

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
      (if (map? v) (make-query (get entry k) v)
                   (= (get entry k) v))) (keys query) (vals query)))))

; For databases w/o built-in modifiers, eg. inmem
; http://www.mongodb.org/display/DOCS/Updating
(def modifiers
  {:$inc +
   :$dec -
   :$set (fn [o m] m)
   :$unset (fn [o m] nil)
   :$push conj})

(defn apply-modifications [obj mods]
  (if (empty? mods) (select-keys obj (filter identity (map #(if (nil? %2) nil %1) (keys obj) (vals obj))))
    (recur (let [f (first mods)
                 mod (get modifiers (key f))]
                 (merge-with mod obj (val f))) (rest mods))))

(defmacro qs [a] `(keyword (str "$" ~a)))
