(defproject ringfinger "0.1.0-SNAPSHOT"
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
            :trim-prefix "ringfinger."
            :namespaces-to-document ["ringfinger"]
            :load-except-list [#"/test/" #"project\.clj" #".*default-views\.clj"]}
    :dependencies ([org.clojure/clojure "1.2.1"]
                   [org.clojure/clojure-contrib "1.2.0"]
                   [lamina "0.4.0-alpha3-SNAPSHOT"]
                   [congomongo "0.1.6-SNAPSHOT"]
                   [commons-codec "1.5"]
                   [commons-email "1.1"]
                   [ring "0.3.11"]
                   [hiccup "0.3.6"]
                   [clout "0.4.1"]
                   [valip "0.2.0"])
    :dev-dependencies ([ring-mock "0.1.1"]
                       [ring-serve "0.1.1"]
                       [org.clojars.weavejester/autodoc "0.9.0"]))
