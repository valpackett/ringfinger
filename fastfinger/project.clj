(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/fastfinger version
  :description "Functions that write code for you"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [ringfinger/toolfinger ~version]
         [ringfinger/basefinger ~version]
         [ringfinger/corefinger ~version]
         [lamina "0.4.0-beta2-SNAPSHOT"]
         [faker "0.2.2"]
         [inflections "0.5.2"]
         [valip "0.2.0"]
         [enlive "1.0.0"]
         [com.ibm.icu/icu4j "4.8.1.1"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje ~lein-midje-version]
                     [ring-mock "0.1.1"]])