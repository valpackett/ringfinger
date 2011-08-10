(ns ringfinger.fields
  (:use (ringfinger db util), ringfinger.db.inmem,
        (clj-time format coerce))
  (:require [valip.predicates :as v]))

(defn required "Validates presence" []
  {:html {:required "required"}
   :pred v/present?
   :req  true})

(defn pattern "Validates according to given regexp" [re]
  {:html {:pattern (str re)}
   :pred #(boolean (re-matches re %))})

(defn alphanumeric "Validates alphanumeric strings" []
  (pattern #"[0-9a-zA-Z]+"))

(defn text "input type=text" []
  {:html {:type "text"}})

(defn textarea "textarea" []
  {:html {:_render (fn [title value attrs] [:textarea (merge {:id title :name title} attrs) value])}})

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

(defn date "Validates/parses/outputs dates" []
  {:html {:type "date"}
   :hook #(try (parse (:date formatters) %) ; returns Joda DateTime
            (catch IllegalArgumentException ex
              false))
   :view #(unparse (:date formatters) (from-date %)) ; gets java.util.Date
   :pred #(boolean (re-matches #"[0-9]{4}-[0-9]{2}-[0-9]{2}" %))})

(def #^{:private true} time-hhmm-fmt (formatter "HH:mm"))

(defn time-field "Validates/parses/outputs times" []
  {:html {:type "time"}
   :hook #(try (parse (:time-parser formatters) %) ; returns Joda DateTime
            (catch IllegalArgumentException ex
              false))
   :view #(unparse time-hhmm-fmt (from-date %)) ; gets java.util.Date
   :pred #(boolean (re-matches #"[0-9]{2}:[0-9]{2}" %))})

(defn number "Validates integer numbers" []
  {:html {:type "number"}
   ;hook not needed, typeize parses this
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
