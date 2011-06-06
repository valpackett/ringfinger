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

(defmacro form-fields [fields data errors wrap-html err-html style]
  `(map (fn [f# fval#] (let [title# (as-str f#)] (conj ~wrap-html
    (if (= ~style :label) [:label {:for title#} title#] nil)
    [:input (merge {:name title# :id title# :value (as-str (get ~data f#))}
                    (if (= ~style :placeholder) {:placeholder title#} nil) fval#)]
    (if (get ~errors f#) (conj ~err-html (map as-str (get ~errors f#))) nil)
  ))) (keys ~fields) (vals ~fields)))
