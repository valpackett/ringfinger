(load-file "metaproject.clj")

(defproject ringfinger version
  :description "Fun, fast, secure web development framework for Clojure/Ring"
  :url "https://github.com/myfreeweb/ringfinger"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/licenses/LICENSE-2.0.html"
            :distribution :repo}
  :autodoc {:name "Ringfinger"
            :page-title "Ringfinger API docs"
            :web-src-dir "https://github.com/myfreeweb/ringfinger/blob/"
            :web-home "http://myfreeweb.github.com/ringfinger/"
            :output-path "autodoc"
            :root "."
            :source-path ""
            :namespaces-to-document ~(conj fingers "ringfinger")
            :load-except-list [#".*/test/" #".*project\.clj" #"website/" #"autodoc"]}
  :sub ~(conj fingers ".")
  :deps [[org.clojure/clojure ~clj-version]
         [ringfinger/toolfinger ~version]
         [ringfinger/secfinger  ~version]
         [ringfinger/mailfinger ~version]
         [ringfinger/basefinger ~version]
         [ringfinger/corefinger ~version]
         [ringfinger/fastfinger ~version]
         [ringfinger/restfinger ~version]
         [ringfinger/formfinger ~version]]
  :dev-dependencies [[ring-serve "0.1.1"]
                     [midje ~midje-version]
                     [lein-midje "1.0.3"]
                     [org.clojars.weavejester/autodoc "0.9.0"]])
