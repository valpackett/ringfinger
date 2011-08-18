(ns ringfinger.timesavers.hooks
  "Ready-to-use hooks for use with ringfinger.resource. Save even more time!"
  (:use inflections.core,
        faker.lorem,
        net.cgrand.enlive-html,
        [valip.predicates :as v],
        [clojure.contrib.string :only [as-str]]))

(defn make-slug-for
  "Returns a hook which makes a slug (URL-friendly name, eg. My Article -> my-article)
  for a given field. Default output-field is field + '_slug'.
  Don't forget that if you use a custom output-field, you need to whitelist it.
  Never returns empty values"
  ([field] (make-slug-for field (keyword (as-str field "_slug"))))
  ([field output-field]
   (let [fakes (words)]
     (fn [data]
       (assoc data output-field
              (let [r (parameterize (get data field))]
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
  "Returns a hook which escapes the contents of the given field for
  a given context (:html, :attr, :js, :css or :urlpart), :html is the default"
  ([field] (escape-input field :html))
  ([field context]
   (let [make-ascii-escfn (fn [prefix]
                            (fn [s]
                              (->> (.getBytes s)
                                   (map #(let [i (int %)]
                                           (if (or (> i 256)
                                                   ((v/between 65 122) i) ; alpha-
                                                   ((v/between 48 57) i)) ; -numeric
                                             (str (char %))
                                             (str prefix (Integer/toHexString i)))))
                                   (apply str))))
         escfn (case context
                 :html #(-> % str (.replace "&" "&amp;") (.replace "<" "&lt;") (.replace ">" "&gt;")
                                  (.replace "\"" "&quot;") (.replace "'" "&#x27;") (.replace "/" "&#x2F;"))
                 :attr (make-ascii-escfn "&#x")
                 :js (make-ascii-escfn "\\x")
                 :css (make-ascii-escfn "\\")
                 :urlpart #(java.net.URLEncoder/encode (str %) "UTF-8"))]
     (fn [data] (assoc data field (escfn (field data)))))))
