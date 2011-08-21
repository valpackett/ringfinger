(ns ringfinger.security
  "Some security-related Ring middleware used by ringfinger.core and other related functions."
  (:use ringfinger.util)
  (:import org.apache.commons.codec.digest.DigestUtils,
           org.apache.commons.codec.binary.Base64,
           java.security.SecureRandom))

(defn secure-rand
  "Secure random (SHA1PRNG) Base64-encoded string generator"
  ([] (secure-rand 32))
  ([b]
    (let [s (byte-array b)]
      (.nextBytes (SecureRandom/getInstance "SHA1PRNG") s)
      (Base64/encodeBase64String s))))

(defn wrap-csrf "CSRF protection middleware for Ring" [handler]
  (fn [req]
    ; stop early if the req isn't coming from a browser
    (if (from-browser? req)
      (if (and (or (= :post (:request-method req)) (= :put (:request-method req)))
               (not (= (get-in req [:form-params "csrftoken"])
                       (get-in req [:session :csrftoken]))))
        {:status  403
         :headers {"Content-Type" "text/plain"}
         :body    "CSRF attempt detected!"}
        (let [token (java.net.URLEncoder/encode (secure-rand))]
          (assoc-in (handler (assoc req :csrf-token token)) [:session :csrftoken] token)))
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

(defn wrap-sec-headers "Middleware for Ring which adds some headers for security" [handler]
  (fn [req]
    (let [res (handler req)]
      (assoc res :headers
             (merge (:headers res)
                    {"X-Content-Type-Options" "nosniff"
                     "X-Frame-Options" "sameorigin"
                     "X-XSS-Protection" "1; mode=block"})))))
