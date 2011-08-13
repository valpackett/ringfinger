(ns ringfinger.timesavers.hooks
  (:use inflections.core,
        faker.lorem,
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
