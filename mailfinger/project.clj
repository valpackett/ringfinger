(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/mailfinger version
  :description "Email sending functions"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [org.clojure/data.json "0.1.1"]
         [clj-http "0.1.3"]
         [commons-email "1.1"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje "1.0.3"]])