(ns ringfinger.util
  (:use valip.predicates))

(defn andf ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn typeify [s]
  (cond (= s "true")  true
        (= s "false") false
        (digits? s)   (Integer/parseInt s)
        :else         s))
