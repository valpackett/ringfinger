(ns toolfinger
  "Various functions and macros used by Ringfinger. Feel free to use them too!"
  (:use valip.predicates,
        clj-time.coerce,
        inflections.core)
  (:import org.joda.time.DateTime))

(defn andf
  "And function, just like the and macro. For use with apply"
  ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn arities
  "Returns the arities of a function" [f]
  ; Stolen from https://groups.google.com/group/clojure/browse_thread/thread/d9953ada48068d78/1823e56ffba50dbb
  (let [methods      (.getDeclaredMethods (class f))
        count-params (fn [m] (map #(count (.getParameterTypes %))
                                  (filter #(= m (.getName %)) methods)))
        invokes      (count-params "invoke")
        do-invokes   (map dec (count-params "doInvoke"))
        arities      (sort (distinct (concat invokes do-invokes)))]
    (if (seq do-invokes) (concat arities [:more]) arities)))

(defmacro call-or-ret
  "If value is a callable, calls it with args and returns the result.
  Otherwise, just returns it."
  [value & args]
  `(if (ifn? ~value) (~value ~@args) ~value))

(defmacro haz?
  "Checks if a collection has an element,
  eg. [:a :b] :a -> true, [:a :b] :c -> false"
  [coll elem] `(boolean (some #{~elem} ~coll)))

(defn #^String substring?
  "Checks if s contains the substring."
  ; stolen from clojure.contrib, but it's an obvious one
  [substring #^String s] (.contains s substring))

(defn #^String str-drop
  "Drops first n characters from s.  Returns an empty string if n is
   greater than the length of s."
  ; same
  [n #^String s] (if (< (count s) n) "" (.substring s n)))

(defmacro zeroify
  "Converts an integer to a string, adding a leading zero if it's < 10,
  e.g. 1 -> '01', but 10 -> '10'
  Used for dates and times"
  [n] `(str (if (< ~n 10) "0") ~n))

(defmacro nice-count
  "Displays a count of items nicely, eg.
  0 'entry' -> 'no entries', 1 'entry' -> 'one entry'
  7 'post' -> 'seven posts', 100500 'article' -> '100500 articles'"
  [number noun]
  `(str (case ~number 0 "no" 1 "one" 2 "two" 3 "three" 4 "four" 5 "five" 6 "six"
                      7 "seven" 8 "eight" 9 "nine" 10 "ten" 11 "eleven" 12 "twelve" ~number)
        " "
        (if (= ~number 1) ~noun (plural ~noun))))

(defn recursive-map
  "Map for maps, recursive."
  [func m]
  (into {}
    (for [[k v] m]
      (if (map? v) (let [r (recursive-map func v)]
                     (if (not (empty? r)) [k r]))
           (if-let [r (func k v)]
             (if (not (and (coll? r) (empty? r)))
               [k r]))))))

(defmacro pack-to-map
  "Packs values into a map, eg.
  (let [demo 1 test 2] (pack demo test)) -> {:demo 1 :test 2}"
  [& values] (zipmap (map keyword values) values))

(defmacro map-to-querystring
  "Turns a map into a query sting, eg.
  {:abc 123 :def ' '} -> ?abc=123&def=+"
  [m] `(if (empty? ~m) ""
        (apply str "?" (interpose "&" (map #(str
          (java.net.URLEncoder/encode (name (key %)) "UTF-8")
          "="
          (java.net.URLEncoder/encode (str (val %)) "UTF-8")) ~m)))))

(defn alter-query-params
  [req m] (map-to-querystring (merge (:query-params req) m)))

(defn typeify
  "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, also recognizes integers and doubles "
  [s]
  (cond
        (instance? DateTime s) (to-date s)
        (= s "true") true
        (= s "false") false
        (integer-string? (str s)) (Integer/parseInt s)
        (decimal-string? (str s)) (Double/parseDouble s)
        :else s))

(defmacro keywordize
  "Turns keys in map a into keywords" [a]
  `(zipmap (map keyword (keys ~a)) (vals ~a)))

(defmacro typeize
  "Maps typeify to values of map a" [a]
  `(zipmap (keys ~a) (map typeify (vals ~a))))

(defmacro sorted-zipmap [ks vs] `(zipmap (reverse ~ks) (reverse ~vs))) ; this should be built into zipmap, dammit

(defmacro dotformat [matches] `(if-let [fmt# (:format ~matches)] fmt#))

(defn sort-maps
  "Sorts a sequence of maps using a map of sort args that maps keys to -1 for desc and 1 for asc order"
  [m sargs]
  (loop [m m
         ks (reverse (keys sargs))]
    (if (empty? ks) m
      (let [k (first ks)
            c (if (neg? (get sargs k))
                        #(compare %2 %1)
                        compare)]
        (recur (sort-by k c m) (rest ks))))))

(defn from-browser?
  "Checks if the request comes from a web browser. Or something pretending to be a web browser, really"
  [req]
  (boolean (re-matches #"(Mozilla|Opera).*" (get-in req [:headers "user-agent"] ""))))

(defn is-xhr?
  "Checks if the request is made by an XMLHttpRequest"
  [req]
  (= (get-in req [:headers "x-requested-with"] "") "XMLHttpRequest"))
