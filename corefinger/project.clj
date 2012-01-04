(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/corefinger version
  :description "Routing and various common middleware for Ring"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["secfinger"]
               ["org.clojure/data.json"
                "ring/ring-core"
                "ring/ring-devel"
                "clout"])
  :dev-dependencies ~(dev-deps [] ["ring-mock"]))
