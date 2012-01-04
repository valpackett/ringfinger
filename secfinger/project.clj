(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/secfinger version
  :description "Security-related middleware for Ring and other functions"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["toolfinger"] ["commons-codec"])
  :dev-dependencies ~(dev-deps [] ["ring-mock"]))
