(defproject ringfinger "0.1.0-SNAPSHOT"
  :description "Resource-oriented RESTful web development on Clojure + Ring"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :dependencies [[org.clojure/clojure "1.2.1"]
                 [org.clojure/clojure-contrib "1.2.0"]
                 [commons-codec       "1.5"  ]
                 [commons-email       "1.1"  ]
                 [ring                "0.3.8"]
                 [ring-mock           "0.1.1"]
                 [hiccup              "0.3.4"]
                 [clout               "0.4.1"]
                 [valip               "0.2.0"]])
