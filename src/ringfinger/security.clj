(ns ringfinger.security
  (:use ringfinger.util)
  (:import org.apache.commons.codec.digest.DigestUtils))

(defn wrap-csrf "CSRF protection middleware for Ring" [handler]
  (fn [req]
    ; stop early if the req isn't coming from a browser
    (if (from-browser? req)
      (if (and (= :post (:request-method req)) ; method override doesn't affect it here
               (not (= (get-in req [:form-params "csrftoken"])
                       (get-in req [:cookies "csrftoken" :value]))))
        {:status  403
         :headers {"Content-Type" "text/plain"}
         :body    "CSRF attempt detected!"}
        (let [token (DigestUtils/md5Hex (str (rand)))]
          (assoc-in (handler (assoc req :csrf-token token)) [:cookies "csrftoken"] token)))
      (handler req))))

(defn wrap-refcheck "Referer checking middleware for Ring" [handler]
  (fn [req]
    (if (or (= :get (:request-method req)) (= :head (:request-method req)))
      (handler req)
      (let [referer (get-in req [:headers "referer"])]
        (if (or (= referer nil)
                (boolean (re-matches
                           (re-pattern (str "https?://[a-zA-Z0-9\\.]*\\.?" (get-in req [:headers "host"]) ".*"))
                           referer)))
          (handler req)
          {:status  403
           :headers {"Content-Type" "text/plain"}
           :body    (str "You can't " (:request-method req) " from other domains.")})))))
