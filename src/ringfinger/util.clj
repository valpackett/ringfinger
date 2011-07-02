(ns ringfinger.util
  (:use valip.predicates, [clojure.contrib.string :only [as-str]]))

(defn andf ([] true) ([x] x)
  ([x & next]
    (if x (apply andf next) x)))

(defn typeify [s]
  (cond (= s "true")  true
        (= s "false") false
        (digits? s)   (Integer/parseInt s)
        :else         s))

(defmacro keywordize [a]
  `(zipmap (map keyword (keys ~a)) (vals ~a)))

(defmacro typeize [a]
  `(zipmap (keys ~a) (map typeify (vals ~a))))

(defmacro sorted-zipmap [ks vs] `(zipmap (reverse ~ks) (reverse ~vs))) ; this should be built into zipmap, dammit

(def default-style "html{background:#cece9e}body{margin:4%;padding:2%;background:#fff;color:#333;font:14px \"Lucida Grande\", sans-serif}input,button{display: block}.error,input:invalid{background:#dd9090;color:#f4f4f4}.flash{background:#aba210;color:white;padding:4px}")

(defmacro fields-from-validations [validations]
  `(let [v# (group-by first ~validations)]
     (sorted-zipmap (keys v#) (map (fn [a#] (apply merge (map #(:html (second %)) a#))) (vals v#)))))

(defmacro form-fields [fields data errors wrap-html err-html style]
  `(map (fn [f# fval#] (let [title# (as-str f#)] (conj ~wrap-html
    (if (= ~style :label) [:label {:for title#} title#] nil)
    [:input (merge {:name title# :id title# :value (as-str (get ~data f#))}
                    (if (= ~style :placeholder) {:placeholder title#} nil) fval#)]
    (if (get ~errors f#) (conj ~err-html (map as-str (get ~errors f#))) nil)
  ))) (keys ~fields) (vals ~fields)))
