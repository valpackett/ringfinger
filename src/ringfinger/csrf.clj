(ns ringfinger.csrf
  "CSRF protection middleware for Ring"
  (:use ringfinger.util)
  (:import org.apache.commons.codec.digest.DigestUtils))

(defn wrap-csrf [handler]
  (fn [req]
    ; stop early if the req isn't coming from a browser
    (if (from-browser? req)
      (if (and (= :post (:request-method req))
               (not (= (get-in req [:form-params "csrftoken"])
                       (get-in req [:cookies "csrftoken" :value]))))
        {:status  403
         :headers {"Content-Type" "text/plain"}
         :body    "CSRF attempt detected!"}
        (let [token (DigestUtils/md5Hex (str (rand)))]
          (assoc-in (handler (assoc req :csrf-token token)) [:cookies "csrftoken"] token)))
      (handler req))))
