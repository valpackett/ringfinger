(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/fastfinger version
  :description "Functions that write code for you"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["toolfinger"
                "basefinger"
                "corefinger"]
               ["lamina"
                "faker"
                "inflections"
                "valip"
                "enlive"
                "com.ibm.icu/icu4j"])
  :dev-dependencies ~(dev-deps [] ["ring-mock"]))
