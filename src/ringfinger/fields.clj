(ns ringfinger.fields
  "All functions in this module make fields for you to use
  with ringfinger.resource.
  They're defined as functions even when they need no
  customization just for consistency."
  (:use (ringfinger db util), ringfinger.db.inmem,
        (clj-time format coerce),
        faker.lorem)
  (:require [valip.predicates :as v],
            [faker.internet :as netfake]))

; FORMAT:
; {:html {:_render -- custom renderer}
;  :pred -- predicate
;  :fake -- lazy sequence of example data
;  :req  -- is required?
;  :default -- default, for put requests
;  :pre-hook -- hook that's executed BEFORE user hooks
;  :post-hook -- AFTER user hooks
;  :view -- get hook
; }

(defn required "Validates presence" []
  {:html {:required "required"}
   :pred v/present?
   :req  true})

(defn pattern "Validates according to given regexp" [re]
  {:html {:pattern (str re)}
   :pred #(boolean (re-matches re %))})

(defn checkbox "A boolean field" []
  {:html {:_render (fn [title value attrs]
                     [:input (merge {:id title :name title :type "checkbox"}
                                    (if (= value "true") {:checked "checked"} nil)
                                    attrs)])}
   :default ""
   :pre-hook #(if (= % "on") true false)})

(defn alphanumeric "Validates alphanumeric strings" []
  (pattern #"[0-9a-zA-Z]+"))

(defn text "input type=text" []
  {:html {:type "text"}})

(defn textarea "textarea" []
  {:html {:_render (fn [title value attrs] [:textarea (merge {:id title :name title} attrs) value])}
   :fake (sentences)})

(defn maxlength "Sets the maximum length to the given number" [n]
  {:html {:maxlength n}
   :pred #(<= (count %) n)})

(defn minlength "Sets the minimum length to the given number" [n]
  {:html {:pattern (str ".{" n ",}")}
   :pred #(>= (count %) n)})

(defn email "Validates email addresses" []
  {:html {:type "email"}
   :fake (repeatedly netfake/email)
   :pred v/email-address?})

(defn email-with-lookup
  "Validates email addresses with an additional DNS lookup. Safer, but slower" []
  {:html {:type "email"}
   :fake (repeatedly netfake/free-email)
   :pred v/valid-email-domain?})

(defn url "Validates URLs" []
  {:html {:type "url"}
   :fake (repeatedly #(str "http://" (netfake/domain-name)))
   :pred v/url?})

(defn ipv4 "Validates IPv4 addresses" []
  {:html {:pattern "([0-9]{1,3}\\.){3}[0-9]{1,3}"}
   ; rand-int's second arg is exclusive, 256 means 0-255
   :fake (repeatedly #(str (rand-int 256) "." (rand-int 256) "." (rand-int 256) "." (rand-int 256)))
   :pred (fn [a]
           (= '(false false false false)
              (map #(> (Integer/parseInt %) 255)
                   (drop 1 (re-matches #"([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})\.([0-9]{1,3})" a)))))})

(defn color "Validates hexadecimal color codes" []
  {:html {:type "color"}
   :fake (repeatedly #(str "#" (rand-int 10) (rand-int 10) (rand-int 10))) ; okay, enough randomness
   :pred #(boolean (re-matches #"#?([0-9a-fA-F]{6}|[0-9a-fA-F]{3})" %))})

(defn date "Validates/parses/outputs dates" []
  {:html {:type "date"}
   :fake (repeatedly #(let [month (+ 1 (rand-int 12))
                            ; i don't remember how much days there are in february,
                            ; and i'm too lazy to open iCal
                            day (+ 1 (rand-int (if (= month 2) 20 29)))]
                        ; yeah these MUST be in a let. otherwise, strange things happen,
                        ; like "010" or whatever. blame Rich Hickey, Sun/Oracle, whoever you want, but not me.
                        (str "2011-" (zeroify month) "-" (zeroify day))))
   :pre-hook #(try (parse (:date formatters) %) ; returns Joda DateTime
                (catch IllegalArgumentException ex
                  nil))
   :post-hook to-date
   :view #(unparse (:date formatters) (from-date %)) ; gets java.util.Date
   :pred #(boolean (re-matches #"[0-9]{4}-[0-9]{2}-[0-9]{2}" %))})

(defn time-field "Validates/parses/outputs times" []
  {:html {:type "time"}
   :fake (repeatedly #(let [hour (rand-int 24)
                            minute (rand-int 60)]
                        (str (zeroify hour) ":" (zeroify minute))))
   :pre-hook #(try (parse (:time-parser formatters) %) ; returns Joda DateTime
                (catch IllegalArgumentException ex
                  nil))
   :post-hook to-date
   :view #(unparse (:hour-minute formatters) (from-date %)) ; gets java.util.Date
   :pred #(boolean (re-matches #"[0-9]{2}:[0-9]{2}(:[0-9]{2})?(\.[0-9]{2})?" %))})

(defn number "Validates integer numbers" []
  {:html {:type "number"}
   :fake (repeatedly #(str (rand-int 1024)))
   :pre-hook #(Integer/parseInt %)
   :pred v/integer-string?})

(defn nmin "Sets the minimum number to the given one" [n]
  {:html {:min n}
   :fake (repeatedly #(str (+ n (rand-int 1024))))
   :pred (v/gte n)})

(defn nmax "Sets the maximum number to the given one" [n]
  {:html {:max n}
   :fake (repeatedly #(str (rand-int n)))
   :pred (v/lte n)})

(defn nbetween "Sets the minimum and maximum numbers to given ones" [minn maxn]
  {:html {:min minn :max maxn}
   :fake (repeatedly #(str (+ minn (rand-int (- maxn minn)))))
   :pred (v/between minn maxn)})
