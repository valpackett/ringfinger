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
         [ringfinger/formfinger ~version]
         [valip "0.2.0"]
         [hiccup "0.3.7"]
         [lamina "0.4.0-rc2"]
         [clj-time "0.3.0"]
         [inflections "0.6.4-SNAPSHOT"]
         [faker "0.2.2"]
         [clojure-csv "1.3.2"]
         [clj-yaml "0.3.1"]]
  :dev-dependencies [[ringfinger/authfinger ~version]
                     [midje ~midje-version]
                     [lein-midje ~lein-midje-version]
                     [commons-codec "1.5"]
                     [ring-mock "0.1.1"]])
