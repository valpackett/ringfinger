(ns corefinger.middleware
  "Common Ring middleware"
  (:use [clojure.data.json :only [read-json]],
        [clojure.string :only [lower-case]]))

(defn wrap-length
  "Ring middleware for adding Content-Length"
  [handler]
  (fn [req]
    (let [res (handler req)]
      (assoc-in res [:headers "Content-Length"] (str (count (:body res)))))))

(defn wrap-head
  "Ring middleware for handling HEAD requests properly"
  [handler]
  (fn [req]
    (if (= (:request-method req) :head)
      (assoc (handler (assoc req :request-method :get)) :body "")
      (handler req))))

(defn wrap-jsonp
  "Ring middleware for handling JSONP requests"
  [handler callback-param]
  (fn [req]
    (let [res (handler req)]
      (if-let [cb (get-in req [:query-params callback-param])]
        (assoc (assoc-in res [:headers "Content-Type"] "text/javascript; charset=utf-8")
               :body (str cb "(" (:body res) ")"))
        res))))

(defn wrap-json-params
  "Ring middleware for parsing JSON params"
  [handler]
  (fn [req]
    (if (and (:content-type req)
             (:body req)
             (.startsWith (:content-type req) "application/json"))
      (let [jsp (read-json (slurp (:body req)) false)]
        (-> req
            (assoc :form-params jsp)
            (assoc :params (merge (:params req) jsp))
            handler))
      (handler req))))

(defn wrap-method-override
  "Ring middleware for method overriding (X-HTTP-Method-Override and _method query parameter)"
  [handler]
  (fn [req]
    (let [rm (or (get-in req [:headers "x-http-method-override"])
                 (get-in req [:query-params "_method"]))]
      (handler (if rm (assoc req :request-method (keyword (lower-case rm))) req)))))
