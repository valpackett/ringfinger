(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/basefinger version
  :description "Document-oriented database abstraction"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["toolfinger"] ["ring/ring-core"])
  :dev-dependencies ~(dev-deps [] ["congomongo"]))
