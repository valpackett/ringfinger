(ns ringfinger.util
  (:use valip.predicates, clj-time.coerce)
  (:require [clojure.java.io :as io])
  (:import org.joda.time.DateTime))

(defn andf
  "And function, just like and macro. For use with apply"
  ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn typeify
  "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, also recognizes integers and doubles "
  [s]
  (cond
        (instance? DateTime s) (to-date s)
        (= s "true") true
        (= s "false") false
        (integer-string? s) (Integer/parseInt s)
        (decimal-string? s) (Double/parseDouble s)
        :else s))

(defmacro keywordize
  "Turns keys in map a into keywords" [a]
  `(zipmap (map keyword (keys ~a)) (vals ~a)))

(defmacro typeize
  "Maps typeify to values of map a" [a]
  `(zipmap (keys ~a) (map typeify (vals ~a))))

(defmacro sorted-zipmap [ks vs] `(zipmap (reverse ~ks) (reverse ~vs))) ; this should be built into zipmap, dammit

(defn sort-maps
  ; FIXME: wrong order w/ desc
  "Sorts a sequence of maps using a map of sort args that maps keys to -1 for desc and 1 for asc order" [m sargs]
  (if (or (= sargs nil) (= sargs {})) m
    (if (= (count sargs) 1)
      ((if (= (first (vals sargs)) -1) reverse identity)
        (sort-by #(get % (first (keys sargs))) m))
      (let [a (reverse sargs)]
        (recur (sort-maps m (apply array-map (first a)))
               (conj {} (rest a)))))))

(defn from-browser?
  "Returns true if the request comes from a web browser. Or something pretending to be a web browser, really"
  [req]
  ; TODO: better way? finer regexp?
  (boolean (re-matches #"(Mozilla|Opera).*" (get-in req [:headers "user-agent"] ""))))

(def default-style
  (slurp (io/resource "default.css")))
