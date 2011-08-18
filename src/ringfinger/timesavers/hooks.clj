(ns ringfinger.timesavers.hooks
  "Ready-to-use hooks for use with ringfinger.resource. Save even more time!"
  (:use inflections.core,
        faker.lorem,
        net.cgrand.enlive-html,
        ringfinger.util,
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
