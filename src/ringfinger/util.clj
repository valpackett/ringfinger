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

; TODO: customization
(defmacro form-fields [fields data errors]
  `(map (fn [f# fval#] (list
    [:input (merge {:name (as-str f#) :value (as-str (get ~data f#)) :placeholder (as-str f#)} fval#)]
    (if (get ~errors f#) [:div {:class "error"} (map as-str (get ~errors f#))] nil)
  )) (keys ~fields) (vals ~fields)))
