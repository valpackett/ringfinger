(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/formfinger version
  :description "Forms for Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [ringfinger/toolfinger ~version]
         [ringfinger/basefinger ~version]
         [valip "0.2.0"]
         [hiccup "0.3.6"]
         [clj-time "0.3.0"]
         [faker "0.2.2"]
         [com.ibm.icu/icu4j "4.8.1.1"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje "1.0.3"]])