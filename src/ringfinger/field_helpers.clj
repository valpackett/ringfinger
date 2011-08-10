(ns ringfinger.field-helpers
  (:use ringfinger.util,
        [clojure.contrib.string :only [as-str]]))

(defn html-from-fields
  "Makes a map of field names - html attributes from a list of fields, eg.
  ([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']
   [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])
  becomes ([:name {:required 'required' :maxlength 10}])"
  [fields]
  (let [v (group-by first fields)]
    (sorted-zipmap (keys v) (map (fn [a] (apply merge (map #(:html (second %) {}) a))) (vals v)))))

(defn validations-from-fields
  "Makes a list of validations from a list of fields, eg.
  ([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']
   [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])
  becomes ([:name (required) 'y u no say ur name']
           [:name (my-check) 'too long']) ; the valip format"
  [fields]
  (map #(assoc % 1 (:pred (second %) (fn [a] true))) fields))

(defn defaults-from-fields
  [fields]
  (let [v (group-by first fields)]
    (sorted-zipmap (keys v) (map (fn [a] (first (map #(:default (second %)) a))) (vals v)))))

(defn get-hook-from-fields
  "Makes a get hook from a list of fields. You usually don't need to use it manually.
  It's used by ringfinger.resource automatically"
  [fields]
  (let [h (group-by first (map #(assoc % 1 (:view (second %) str)) fields))
        hs (zipmap (keys h) (map #(map second %) (vals h)))]
     (fn [data]
       (let [ks (keys data)]
        (zipmap ks
                (map (fn [k v]
                       (if (or (nil? v) (= v "")) "" ; magic
                         (if-let [f (get hs k)]
                           (reduce #(if (ifn? %2) (%2 %1) %1) v (cons identity f)) ; like -> for fns in a coll
                           v))) ks (vals data)))))))

(defn data-hook-from-fields
  "Makes a data hook from a list of fields. You usually don't need to use it manually.
  It's used by ringfinger.resource automatically"
  [fields]
  (let [h (group-by first (map #(assoc % 1 (:hook (second %) identity)) fields))
        hs (zipmap (keys h) (map #(map second %) (vals h)))]
     (fn [data]
       (let [ks (keys data)]
         (zipmap ks
                 (map (fn [k v]
                        (if (nil? v) nil
                          (if-let [f (get hs k)]
                            (reduce #(if (ifn? %2) (%2 %1) %1) v (cons identity f)) ; like -> for fns in a coll
                            v))) ks (vals data)))))))

(defn required-fields-of
  "Returns a list of required fields' names from a list of fields, eg.
  ([:name (required) 'wtf'] [:date (date) 'lolwut'])
  becomes
  (:name)"
  [fields]
  (let [fs (group-by first fields)]
    (filter #(:req (apply merge (map second (get fs %))) false) (keys fs))))

(defmacro form-fields
  "HTML templating helper for rendering forms. Allowed styles are :label and :placeholder"
  [fields-html data errors wrap-html err-html style]
  `(map (fn [f# fval#] (let [title# (as-str f#)] (conj ~wrap-html
    (if (= ~style :label) [:label {:for title#} title#] nil)
    (let [value# (as-str (get ~data f#))]
      (if-let [rf# (:_render fval#)]
        (rf# title# value# (dissoc fval# :_render))
        [:input
         (merge {:name title# :id title# :value value#}
                (if (= ~style :placeholder) {:placeholder title#} nil)
                fval#)]))
    (if (get ~errors f#) (conj ~err-html (map as-str (get ~errors f#))) nil)
  ))) (keys ~fields-html) (vals ~fields-html)))
