(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/authfinger version
  :description "Auth and registration for Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps [[org.clojure/clojure ~clj-version]
         [ringfinger/toolfinger ~version]
         [ringfinger/basefinger ~version]
         [ringfinger/corefinger ~version]
         [ringfinger/formfinger ~version]
         [ringfinger/secfinger ~version]
         [commons-codec "1.5"]
         [valip "0.2.0"]
         [hiccup "0.3.7"]]
  :dev-dependencies [[midje ~midje-version]
                     [lein-midje ~lein-midje-version]
                     [ring-mock "0.1.1"]])
