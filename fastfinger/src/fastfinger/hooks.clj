(ns fastfinger.hooks
  "Ready-to-use hooks for use with ringfinger.resource. Save even more time!"
  (:refer-clojure :exclude [replace])
  (:use inflections.core,
        faker.lorem,
        net.cgrand.enlive-html,
        [clojure.string :only [escape, lower-case, replace]]
        [valip.predicates :as v])
  (:import com.ibm.icu.text.Transliterator))


; Inspired by https://github.com/rsl/stringex
(defn- fix-words [w]
  (-> w
      (replace #"\$([0-9]+)\.([0-9]+)" #(str (second %) " dollars " (last %) " cents"))
      (replace #"\$([0-9]+)" #(str (last %) " dollars"))
      (replace #"([0-9]+)%" #(str (last %) " percent"))
      (replace #"(we|they|you)'re" #(str (last %) " are"))
      (replace #"(she|he|it)'s" #(str (last %) " is"))
      (replace "i'm" "i am")
      (replace "&" "and")
      (replace "=" "equals")
      (replace "+" "plus")
      (replace "-" "minus")))

(defn make-slug-for
  "Returns a hook which makes a slug (URL-friendly name, eg. My Article -> my-article)
  for a given field. Default output-field is field + '_slug'.
  Don't forget that if you use a custom output-field, you need to whitelist it.
  Never returns empty values (takes a random word if the original's empty).
  Transliterates different scripts (eg. Cyrillic) into Latin"
  ([field] (make-slug-for field (keyword (str (name field) "_slug"))))
  ([field output-field]
   (let [fakes (words)
         tr (Transliterator/getInstance "Any-Latin")]
     (fn [data]
       (assoc data output-field
              (let [r (-> (. tr transliterate (get data field))
                          lower-case
                          fix-words
                          parameterize)]
                (if (= r "") (parameterize (first (take 1 fakes))) r)))))))

(defn safe-html
  "Returns a hook which removes script, style, link, title, meta, head, body, html
  and other given tags from a string of HTML in a given field. Also adds a sandbox
  attribute to iframes. As a 'side-effect' (not in the programming sense),
  the HTML is always valid"
  ([field] (safe-html field []))
  ([field moretags]
   (let [tagz (concat moretags [:script :style :link :meta :head :body :html])
         mergestr (fn [a] (apply str a))
         rm (fn [s] (loop [tagz tagz
                           s s]
                      (if (empty? tagz) s (recur (rest tagz) (at s [(first tagz)] nil)))))]
     (fn [data]
       (assoc data field
              (-> (html-snippet (field data))
                  rm (at [:iframe] (set-attr "sandbox" "")) emit* mergestr))))))

(defn escape-input
  "Returns a hook which escapes contents of given fields for
  a given context (:html, :attr, :js, :css or :urlpart)"
  [context & fields]
   (let [make-ascii-escfn (fn [prefix]
                            (let [ks (filter identity
                                       (map #(if (or ((v/between 65 122) %) ; alpha-
                                                     ((v/between 48 57) %)) ; -numeric
                                               nil %) (range 256)))
                                  escmap (zipmap (map char ks) (map #(str prefix (Integer/toHexString %)) ks))]
                              #(escape % escmap)))
         escfn (case context
                 :html #(escape % {\& "&amp;"
                                   \< "&lt;"
                                   \> "&gt;"
                                   \\ "&quot;"
                                   \' "&#x27;"
                                   \/ "&#x2F;"})
                 :attr (make-ascii-escfn "&#x")
                 :js (make-ascii-escfn "\\x")
                 :css (make-ascii-escfn "\\")
                 :urlpart #(java.net.URLEncoder/encode (str %) "UTF-8"))]
     (fn [data]
       (apply assoc data (flatten (map (fn [f] [f (escfn (f data))]) fields))))))
