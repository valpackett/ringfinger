(load-file (str (if (not (.endsWith (System/getProperty "user.dir") "/ringfinger")) "../") "metaproject.clj"))

(defproject ringfinger/authfinger version
  :description "Auth and registration for Ringfinger"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :deps ~(deps ["toolfinger"
                "basefinger"
                "corefinger"
                "formfinger"
                "secfinger"]
               ["commons-codec"
                "hiccup"])
  :dev-dependencies ~(dev-deps [] ["ring-mock"]))
