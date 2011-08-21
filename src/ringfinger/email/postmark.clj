(ns ringfinger.email.postmark
  (:use [clojure.data.json :only [json-str]])
  (:require [clj-http.client :as r]))

(defn postmark
  "Creates a Postmark mailer function. If no args are given, uses the POSTMARK_API_TEST API key w/o SSL.
  If you provide an API key, SSL is on by default"
  ([] (postmark "POSTMARK_API_TEST" false))
  ([apikey] (postmark apikey true))
  ([apikey ssl]
   (let [url (str (if ssl "https://" "http://") "api.postmarkapp.com/email")]
     (fn [from to subject body]
       (r/post url
               {:headers {"X-Postmark-Server-Token" apikey}
                :content-type :json
                :accept :json
                :body    (json-str {"From"     from
                                    "To"       to
                                    "Subject"  subject
                                    "TextBody" body})})))))
