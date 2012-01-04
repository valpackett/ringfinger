(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/mailfinger version
  :description "Email sending functions"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps []
               ["org.clojure/data.json"
                "clj-http"
                "commons-email"])
  :dev-dependencies ~(dev-deps [] []))
