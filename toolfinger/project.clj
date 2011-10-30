(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/toolfinger version
  :description "Various utilities used by Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [valip "0.2.0"]
         [clj-time "0.3.0"]
         [inflections "0.5.2"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje ~lein-midje-version]])