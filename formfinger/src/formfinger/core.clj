(ns formfinger.core
  "API for working with fields"
  (:use hiccup.core,
        [corefinger.core :only [*request*]])
  (:require [clojure.string :as str]))

(defn f "A field"
  ([validation error-msg] (f validation error-msg nil))
  ([validation error-msg title]
    {:val validation
     :title title
     :err error-msg})) ; TODO after i18n at all: auto i18n of error msgs

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

(defn- make-getter [field taker]
  (fn resfn [form]
    (into {}
      (for [[k v] form]
        (if (map? v) (let [r (resfn v)] (if (not (empty? r)) [k r]))
          (if-let [f (last (filter field (map :val v)))] [k (taker (field f))]))))))

(def #^{:doc "Returns the defaults of a form"}
  get-defaults (make-getter :default identity))

(def #^{:doc "Generates a valid entry with random data for a form"}
  make-fake (make-getter :fake #(first (take 1 %))))

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

(defn render
  "Renders fields and errors in HTML.
   Options:
    :style -- whether to use :label, :placeholder or nothing
    :err-html -- error wrapper, default is [:div.error]
    :wrap-html -- field wrapper, default is [:div]
    :nest-html -- a function that, given a key, returns the nested form
                  wrapper, default returns [:fieldset {:id k} [:h2 (capitalize (name k))]]"
  ([options form] (render options form nil nil))
  ([options form data] (render options form data nil)) ; not auto-validating eg. for rendering w/ defaults
  ([options form data errors]
   (html
     [:input {:type "hidden" :name "csrftoken" :value (:csrf-token *request*)}]
     (for [[k v] form]
       (if (map? v) (conj
                      (if-let [custom (:nest-html options)]
                        (custom k)
                        [:fieldset {:id k}
                          [:h2 (str/capitalize (name k))]])
                      (render options v (k data) (k errors)))
         (let [title     (or (apply str (map :title v)) (str/capitalize (name k)))
               err-html  (or (:err-html  options) [:div.error])
               wrap-html (or (:wrap-html options) [:div])]
           (filter identity
             [(if (= (:style options) :label)
               [:label {:for k} title])
             [:input (apply merge {:name k :id k :value (k data)}
                            (if (= (:style options) :placeholder)
                              {:placeholder title})
                            (map #(get-in % [:val :html]) v))]
             (if-let [err (k errors)]
               (map (partial conj err-html) err))])))))))
