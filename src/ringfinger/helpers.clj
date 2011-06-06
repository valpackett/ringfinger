(ns ringfinger.helpers
  (:use [clojure.contrib.string :only [as-str]]))

; TODO: customization
(defmacro form-fields [fields data errors]
  `(map (fn [f#] (list
    [:input {:name (as-str f#) :value (as-str (get ~data f#)) :placeholder (as-str f#)}]
    (if (get ~errors f#) [:div {:class "error"} (map as-str (get ~errors f#))] nil)
  )) ~fields))
