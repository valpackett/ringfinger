(ns ringfinger.util
  (:use valip.predicates))

(defn andf
  "And function, just like and macro. For use with apply"
  ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn typeify
  "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, if a string with digits only - parses it as an integer"
  [s]
  (cond (= s "true")  true
        (= s "false") false
        (digits? s)   (Integer/parseInt s)
        :else         s))

(defmacro keywordize
  "Turns keys in map a into keywords" [a]
  `(zipmap (map keyword (keys ~a)) (vals ~a)))

(defmacro typeize
  "Maps typeify to values of map a" [a]
  `(zipmap (keys ~a) (map typeify (vals ~a))))

(defmacro sorted-zipmap [ks vs] `(zipmap (reverse ~ks) (reverse ~vs))) ; this should be built into zipmap, dammit

(defn from-browser?
  "Returns true if the request comes from a web browser. Or something pretending to be a web browser, really"
  [req]
  ; TODO: better way? finer regexp?
  (boolean (re-matches #"(Mozilla|Opera).*" (get-in req [:headers "user-agent"] ""))))

(def default-style "html{background:#cece9e}body{margin:4%;padding:2%;background:#fff;color:#333;font:14px \"Lucida Grande\", sans-serif}input,button{display: block}.error,input:invalid{background:#dd9090;color:#f4f4f4}.flash{background:#aba210;color:white;padding:4px}")
