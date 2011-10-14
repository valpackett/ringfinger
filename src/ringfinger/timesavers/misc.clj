(ns ringfinger.timesavers.misc
  "Useful common functions for web apps"
  (:use ringfinger.core,
        toolfinger,
        [clojure.string :only [trim, lower-case]])
  (:import org.apache.commons.codec.digest.DigestUtils))

(defn gravatar
  "Given an email, returns a Gravatar URL.
   Automatically uses secure.gravatar.com when inside of an HTTPS request"
   ([email] (gravatar email nil nil nil))
   ([email size] (gravatar email size nil nil))
   ([email size rating] (gravatar email size rating nil))
   ([email size rating default-pic]
     (str "http" (if (= (:scheme *request*) :https) "s://secure" "://www")
          ".gravatar.com/avatar/" (DigestUtils/md5Hex (lower-case (trim email)))
          (map-to-querystring (apply merge {}
            (if size {:s size})
            (if rating {:r rating})
            (if default-pic {:d default-pic}))))))