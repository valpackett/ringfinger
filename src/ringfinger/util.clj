(ns ringfinger.util
  (:use valip.predicates, [clojure.contrib.string :only [as-str]]))

(defn andf
  "And function, just like and macro. For use with apply"
  ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn typeify [s]
  "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, if a string with digits only - parses it as an integer"
  (cond (= s "true")  true
        (= s "false") false
        (digits? s)   (Integer/parseInt s)
        :else         s))

(defmacro keywordize [a]
  "Turns keys in map a into keywords"
  `(zipmap (map keyword (keys ~a)) (vals ~a)))

(defmacro typeize [a]
  "Maps typeify to values of map a"
  `(zipmap (keys ~a) (map typeify (vals ~a))))

(defmacro sorted-zipmap [ks vs] `(zipmap (reverse ~ks) (reverse ~vs))) ; this should be built into zipmap, dammit

(defn from-browser? [req]
  "Returns true if the request comes from a web browser. Or something pretending to be a web browser, really"
  ; TODO: better way? finer regexp?
  (boolean (re-matches #"(Mozilla|Opera).*" (get-in req [:headers "user-agent"] ""))))

(def default-style "html{background:#cece9e}body{margin:4%;padding:2%;background:#fff;color:#333;font:14px \"Lucida Grande\", sans-serif}input,button{display: block}.error,input:invalid{background:#dd9090;color:#f4f4f4}.flash{background:#aba210;color:white;padding:4px}")

(defmacro fields-from-validations [validations]
  "Makes a map of fields from a list of validations, eg. ([:name {:clj (required) :html {:required 'required'}} 'y u no say ur name'] [:name {:clj (my-check) :html {:maxlength 10}} 'too long']) becomes ([:name {:required 'required' :maxlength 10}])"
  `(let [v# (group-by first ~validations)]
     (sorted-zipmap (keys v#) (map (fn [a#] (apply merge (map #(:html (second %)) a#))) (vals v#)))))

(defmacro form-fields [fields data errors wrap-html err-html style]
  `(map (fn [f# fval#] (let [title# (as-str f#)] (conj ~wrap-html
    (if (= ~style :label) [:label {:for title#} title#] nil)
    [:input (merge {:name title# :id title# :value (as-str (get ~data f#))}
                    (if (= ~style :placeholder) {:placeholder title#} nil) fval#)]
    (if (get ~errors f#) (conj ~err-html (map as-str (get ~errors f#))) nil)
  ))) (keys ~fields) (vals ~fields)))
