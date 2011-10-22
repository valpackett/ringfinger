(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/restfinger version
  :description "Resource builder for Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [org.clojure/data.json "0.1.1"]
         [ringfinger/toolfinger ~version]
         [ringfinger/basefinger ~version]
         [ringfinger/corefinger ~version]
         [valip "0.2.0"]
         [hiccup "0.3.6"]
         [com.ibm.icu/icu4j "4.8.1.1"]
         [lamina "0.4.0-beta2-SNAPSHOT"]
         [clj-time "0.3.0"]
         [inflections "0.5.2"]
         [faker "0.2.2"]
         [clojure-csv "1.3.2"]
         [clj-yaml "0.3.0"]
         [commons-codec "1.5"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje "1.0.3"]
                     [ring-mock "0.1.1"]])