(ns ringfinger.email.postmark
  (:use ringfinger.email, clojure.contrib.json)
  (:require [clojure.contrib.http.agent :as ha]))

(deftype PostmarkMailer [apikey ssl] Mailer
  (send-mail [self from to subject body]
    (ha/http-agent (str (if ssl "https://" "http://") "api.postmarkapp.com/email")
                   :method  "POST"
                   :headers {"Accept"       "application/json"
                             "Content-Type" "application/json"
                             "X-Postmark-Server-Token" apikey}
                   :body    (json-str {"From"     from
                                       "To"       to
                                       "Subject"  subject
                                       "TextBody" body}))))

(defn postmark
  "Creates a Postmark mailer object. If no args are given, uses the POSTMARK_API_TEST API key w/o SSL"
  ([] (PostmarkMailer. "POSTMARK_API_TEST" false))
  ([apikey] (PostmarkMailer. apikey true))
  ([apikey ssl] (PostmarkMailer. apikey ssl)))
