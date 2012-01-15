(ns formfinger.core
  "API for working with fields"
  (:use hiccup.core,
        toolfinger,
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
   (recursive-map
     (fn [k v]
       (if (not (empty? (filter identity (map #(get-in % [:val :req]) v)))) value))
     form)))

(defn- make-getter [field taker]
  (fn [form]
    (recursive-map
      (fn [k v]
        (if-let [f (last (filter field (map :val v)))] (taker (field f))))
      form)))

(def #^{:doc "Returns the defaults of a form"}
  get-defaults (make-getter :default identity))
 
(def #^{:doc "Generates a valid entry with random data for a form"}
  make-fake (make-getter :fake #(first (take 1 %))))

(defn validate
  "Validates a form, returns a tree of errors if there are any"
  [form data]
  (let [data (merge (get-required-fields form "") data)
        errs (recursive-map
               (fn [k v]
                 (if (coll? v) (map #(validate (k form) %) v)
                     (filter (complement nil?)
                             (map #(if ((get-in % [:val :pred]) v) nil (:err %))
                                     (k form)))))
               data)
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
     (if (not (:recur options))
       [:input {:type "hidden" :name "csrftoken" :value (:csrf-token *request*)}])
     (for [[k v] form]
       (if (map? v) (conj
                      (if-let [custom (:nest-html options)]
                        (custom k)
                        [:fieldset {:id k}
                          [:h2 (str/capitalize (name k))]])
                      (render (assoc options :recur true) v (k data) (k errors)))
         (let [title     (or (let [r (apply str (map :title v))]
                               (if (not= r "") r))
                             (str/capitalize (name k)))
               err-html  (or (:err-html  options) [:div.error])
               wrap-html (or (:wrap-html options) [:div])]
           (conj wrap-html
             (filter identity
               [(if (= (:style options) :label)
                 [:label {:for k} title])
               [:input (apply merge {:name k :id k :value (k data)}
                              (if (= (:style options) :placeholder)
                                {:placeholder title})
                              (map #(get-in % [:val :html]) v))]
               (if-let [err (k errors)]
                 (map (partial conj err-html) err))]))))))))

(defn- get-hooks [field form]
  (recursive-map
    (fn [k v]
      (if-let [f (filter field (map :val v))]
        (apply comp (map field f))))
    form))

(defn- make-hook [field form]
  (fn [data]
    ((fn hook [frm data]
       (into {}
         (for [[k v] data]
           [k (if (map? v) (hook (k frm) v)
                (or ((k frm) v) v))])))
          (get-hooks field form) data)))

(def make-view-hook      (partial make-hook      :view))
(def make-data-pre-hook  (partial make-hook  :pre-hook))
(def make-data-post-hook (partial make-hook :post-hook))
