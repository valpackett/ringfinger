(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/secfinger version
  :description "Security-related middleware for Ring and other functions"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [ringfinger/toolfinger ~version]
         [commons-codec "1.5"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje "1.0.3"]
                     [ring-mock "0.1.1"]])