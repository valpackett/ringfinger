(ns ringfinger.timesavers.hooks
  (:use inflections.core,
        [clojure.contrib.string :only [as-str]]))

(defn make-slug-for
  "Returns a hook which makes a slug (URL-friendly name, eg. My Article -> my-article)
  for a given field. Default output-field is field + '_slug'.
  Don't forget that if you use a custom output-field, you need to whitelist it."
  ([field] (make-slug-for field (keyword (as-str field "_slug"))))
  ([field output-field]
   (fn [data]
     (assoc data output-field (parameterize (get data field))))))
