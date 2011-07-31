(ns ringfinger.fields
  (:use (ringfinger db util), ringfinger.db.inmem,
        [clojure.contrib.string :only [as-str]])
  (:require [valip.predicates :as v]))

(defn required "Validates presence" []
  {:html {:required "required"}
   :pred v/present?})

(defn pattern "Validates according to given regexp" [re]
  {:html {:pattern (str re)}
   :pred #(boolean (re-matches re %))})

(defn alphanumeric "Validates alphanumeric strings" []
  (pattern #"[0-9a-zA-Z]+"))

(defn maxlength "Sets the maximum length to the given number" [n]
  {:html {:maxlength n}
   :pred #(<= (count %) n)})

(defn minlength "Sets the minimum length to the given number" [n]
  {:html {:pattern (str ".{" n ",}")}
   :pred #(>= (count %) n)})

(defn email "Validates email addresses" []
  {:html {:type "email"}
   :pred v/email-address?})

(defn email-with-lookup
  "Validates email addresses with an additional DNS lookup. Safer, but slower" []
  {:html {:type "email"}
   :pred v/valid-email-domain?})

(defn url "Validates URLs" []
  {:html {:type "url"}
   :pred v/url?})

(defn ipv4 "Validates IPv4 addresses" []
  {:html {:pattern "([0-9]{1,3}\\.){3}[0-9]{1,3}"}
   :pred (fn [a]
           (= '(false false false false)
              (map #(> (Integer/parseInt %) 255)
                   (drop 1 (re-matches #"([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})" a)))))})

(defn color "Validates hexadecimal color codes" []
  {:html {:type "color"}
   :pred #(boolean (re-matches #"#?([0-9a-fA-F]{6}|[0-9a-fA-F]{3})" %))})

(defn date "Validates dates" []
  {:html {:type "date"}
   :pred #(boolean (re-matches #"[0-9]{4}-[0-9]{2}-[0-9]{2}" %))})

(defn number "Validates integer numbers" []
  {:html {:type "number"}
   :pred v/integer-string?})

(defn nmin "Sets the minimum number to the given one" [n]
  {:html {:min n}
   :pred (v/gte n)})

(defn nmax "Sets the maximum number to the given one" [n]
  {:html {:max n}
   :pred (v/lte n)})

(defn nbetween "Sets the minimum and maximum numbers to given ones" [minn maxn]
  {:html {:min minn :max maxn}
   :pred (v/between minn maxn)})

; ---

(defmacro html-from-fields
  "Makes a map of field names - html attributes from a list of fields, eg.
  ([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']
   [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])
  becomes ([:name {:required 'required' :maxlength 10}])"
  [fields]
  `(let [v# (group-by first ~fields)]
     (sorted-zipmap (keys v#) (map (fn [a#] (apply merge (map #(:html (second %)) a#))) (vals v#)))))

(defmacro validations-from-fields
  "Makes a list of validations from a list of fields, eg.
  ([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']
   [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])
  becomes ([:name (required) 'y u no say ur name']
           [:name (my-check) 'too long']) ; the valip format"
  [fields]
  `(map #(assoc % 1 (:pred (second %))) ~fields))

(defmacro form-fields
  "HTML templating helper for rendering forms. Allowed styles are :label and :placeholder"
  [fields-html data errors wrap-html err-html style]
  `(map (fn [f# fval#] (let [title# (as-str f#)] (conj ~wrap-html
    (if (= ~style :label) [:label {:for title#} title#] nil)
    [:input (merge {:name title# :id title# :value (as-str (get ~data f#))}
                    (if (= ~style :placeholder) {:placeholder title#} nil) fval#)]
    (if (get ~errors f#) (conj ~err-html (map as-str (get ~errors f#))) nil)
  ))) (keys ~fields-html) (vals ~fields-html)))
