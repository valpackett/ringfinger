(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/corefinger version
  :description "Routing and various common middleware for Ring"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [org.clojure/data.json "0.1.1"]
         [ringfinger/secfinger ~version]
         [ring/ring-core ~ring-version]
         [clout "0.4.1"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje "1.0.3"]
                     [ring-mock "0.1.1"]])