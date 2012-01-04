(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/formfinger version
  :description "Forms for Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["corefinger"
                "toolfinger"
                "basefinger"]
               ["valip"
                "hiccup"
                "clj-time"
                "faker"
                "com.ibm.icu/icu4j"
                "org.mozilla/rhino"])
  :dev-dependencies ~(dev-deps [] []))
