(ns ringfinger.validation
  (:require [valip.predicates :as v]))

(defn required []
  "Validates presence"
  {:html {:required "required"}
   :clj  v/present?})

(defn pattern [re]
  "Validates according to given regexp"
  {:html {:pattern (str re)}
   :clj  #(boolean (re-matches re %))})

(defn alphanumeric []
  "Validates alphanumeric strings"
  (pattern #"[0-9a-zA-Z]+"))

(defn maxlength [n]
  "Sets the maximum length to the given number"
  {:html {:maxlength n}
   :clj  #(<= (count %) n)})

(defn minlength [n]
  "Sets the minimum length to the given number"
  {:html {:pattern (str ".{" min ",}")}
   :clj  #(>= (count %) n)})

(defn email []
  "Validates email addresses"
  {:html {:type "email"}
   :clj  v/email-address?})

(defn email-with-lookup []
  "Validates email addresses with an additional DNS lookup. Safer, but slower"
  {:html {:type "email"}
   :clj  v/valid-email-domain?})

(defn url []
  "Validates URLs"
  {:html {:type "url"}
   :clj  v/url?})

(defn color []
  "Validates hexadecimal color codes"
  {:html {:type "color"}
   :clj  #(boolean (re-matches #"#?([0-9a-fA-F]{6}|[0-9a-fA-F]{3})" %))})

(defn date []
  "Validates dates"
  {:html {:type "date"}
   :clj  v/html5-date?})

(defn number []
  "Validates integer numbers"
  {:html {:type "number"}
   :clj  v/integer-string?})

(defn nmin [n]
  "Sets the minimum number to the given one"
  {:html {:min n}
   :clj  (v/gte n)})

(defn nmax [n]
  "Sets the maximum number to the given one"
  {:html {:max n}
   :clj  (v/lte n)})

(defn nbetween [min max]
  "Sets the minimum and maximum numbers to given ones"
  {:html {:min min :max max}
   :clj (v/between min max)})
