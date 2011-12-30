(ns corefinger.middleware
  "Common Ring middleware"
  (:use toolfinger
        [clojure.data.json :only [read-json json-str]],
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

(defn wrap-logging
  "Ring middleware for request logging.
   Why JSON: http://journal.paul.querna.org/articles/2011/12/26/log-for-machines-in-json/
   Options:
    :output -- :stdout, :stderr, a string (file path) or a function (for log systems)
               :stdout is default
    :status-filter -- a collection of status codes to log responses with or a predicate
                      (eg. #(> % 399)). nil is default
    :keys-filter -- which keys of (merge req res) to log"
  [handler {:keys [output status-filter keys-filter]
            :or {output :stdout
                 status-filter nil
                 keys-filter [:status :uri :remote-addr :request-method]}}]
  (fn [req]
    (let [res (handler req)
          status-filter (cond
                          (nil? status-filter) (fn [s] true)
                          (coll? status-filter) #(haz? status-filter %)
                          (fn? status-filter) status-filter)
          logger (cond
                   (= output :stdout) println
                   (= output :stderr) #(binding [*out* *err*]
                                         (println %))
                   (string? output) #(spit output (str % "\n") :append true)
                   (fn? output) output)
          entry (json-str (select-keys (merge req res) keys-filter))]
      (if (status-filter (:status res)) (logger entry))
      res)))
