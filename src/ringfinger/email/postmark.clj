(ns ringfinger.email.postmark
  (:use clojure.contrib.json)
  (:require [clojure.contrib.http.agent :as ha]))

(defn postmark
  "Creates a Postmark mailer function. If no args are given, uses the POSTMARK_API_TEST API key w/o SSL"
  ([] (postmark "POSTMARK_API_TEST" false))
  ([apikey] (postmark apikey true))
  ([apikey ssl]
   (let [url (str (if ssl "https://" "http://") "api.postmarkapp.com/email")]
     (fn [from to subject body]
       (ha/http-agent url
                     :method  "POST"
                     :headers {"Accept"       "application/json"
                               "Content-Type" "application/json"
                               "X-Postmark-Server-Token" apikey}
                     :body    (json-str {"From"     from
                                         "To"       to
                                         "Subject"  subject
                                         "TextBody" body}))))))
