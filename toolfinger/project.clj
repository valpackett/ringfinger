(defproject ringfinger/toolfinger "0.2.0-SNAPSHOT"
  :description "Various utilities used by Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :dependencies ([org.clojure/clojure "1.2.1"]
                 [valip "0.2.0"]
                 [clj-time "0.3.0"]
                 [inflections "0.5.2"])
  :dev-dependencies ([midje "1.2.0"]
                     [lein-midje "1.0.3"]))