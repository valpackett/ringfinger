(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/restfinger version
  :description "Resource builder for Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["toolfinger"
                "basefinger"
                "corefinger"
                "formfinger"]
               ["org.clojure/data.json"
                "valip"
                "hiccup"
                "lamina"
                "clj-time"
                "inflections"
                "faker"
                "clojure-csv"
                "clj-yaml"])
  :dev-dependencies ~(dev-deps []
                               ["commons-codec"
                                "ring-mock"]))
