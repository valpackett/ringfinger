(ns formfinger.core
  "API for working with fields")

(defn f "A field" [validation error-msg]
  {:val validation
   :err error-msg}) ; TODO after i18n at all: auto i18n of error msgs

(defn many "A collection of fields" [f] f) ; magic haha

(defn get-required-fields
  "Returns required fields of a form"
  ([form] (get-required-fields form true))
  ([form value]
    (into {}
      (for [[k v] form]
        (cond
          (map? v) (let [r (get-required-fields v)] (if (not (empty? r)) r))
          (not (empty? (filter identity (map #(get-in % [:val :req]) v)))) [k value])))))

(defn validate
  "Validates a form, returns a tree of errors if there are any"
  [form data]
  (let [data (merge (get-required-fields form "") data)
        errs (into {}
               (for [[k v] data]
                 [k (cond
                      (map?  v) (validate (k form) v)
                      (coll? v) (map #(validate (k form) %) v)
                      :else     (filter (complement nil?)
                                  (map #(if ((get-in % [:val :pred]) v) nil (:err %))
                                       (k form))))]))
        errs (select-keys errs
                          (filter #(not (empty? (filter identity (% errs))))
                                  (keys errs)))]
    (if (empty? errs) nil errs)))

(defn make-fake
  "Generates a valid entry with random data for a form"
  [form]
  (into {}
    (for [[k v] form]
      (if (map? v) (let [r (make-fake v)] (if (not (empty? r)) [k r]))
        (if-let [f (last (filter :fake (map :val v)))] [k (first (take 1 (:fake f)))])))))
