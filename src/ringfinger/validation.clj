(ns ringfinger.validation
  (:use ringfinger.db, ringfinger.db.inmem)
  (:require [valip.predicates :as v]))

(defn required "Validates presence" []
  {:html {:required "required"}
   :clj  v/present?})

(defn pattern "Validates according to given regexp" [e]
  {:html {:pattern (str re)}
   :clj  #(boolean (re-matches re %))})

(defn alphanumeric "Validates alphanumeric strings" []
  (pattern #"[0-9a-zA-Z]+"))

(defn maxlength "Sets the maximum length to the given number" [n]
  {:html {:maxlength n}
   :clj  #(<= (count %) n)})

(defn minlength "Sets the minimum length to the given number" [n]
  {:html {:pattern (str ".{" n ",}")}
   :clj  #(>= (count %) n)})

(defn email "Validates email addresses" []
  {:html {:type "email"}
   :clj  v/email-address?})

(defn email-with-lookup
  "Validates email addresses with an additional DNS lookup. Safer, but slower" []
  {:html {:type "email"}
   :clj  v/valid-email-domain?})

(defn url "Validates URLs" []
  {:html {:type "url"}
   :clj  v/url?})

(defn ipv4 "Validates IPv4 addresses" []
  {:html {:pattern "([0-9]{1,3}\\.){3}[0-9]{1,3}"}
   :clj  (fn [a]
           (= '(false false false false)
              (map #(> (Integer/parseInt %) 255)
                   (drop 1 (re-matches #"([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})" a)))))})

(defn color "Validates hexadecimal color codes" []
  {:html {:type "color"}
   :clj  #(boolean (re-matches #"#?([0-9a-fA-F]{6}|[0-9a-fA-F]{3})" %))})

(defn date "Validates dates" []
  {:html {:type "date"}
   :clj  #(boolean (re-matches #"[0-9]{4}-[0-9]{2}-[0-9]{2}" %))})

(defn number "Validates integer numbers" []
  {:html {:type "number"}
   :clj  v/integer-string?})

(defn nmin "Sets the minimum number to the given one" [n]
  {:html {:min n}
   :clj  (v/gte n)})

(defn nmax "Sets the maximum number to the given one" [n]
  {:html {:max n}
   :clj  (v/lte n)})

(defn nbetween "Sets the minimum and maximum numbers to given ones" [minn maxn]
  {:html {:min minn :max maxn}
   :clj  (v/between minn maxn)})
