(defproject ringfinger "0.1.2-SNAPSHOT"
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
            :load-except-list [#"/test/" #"project\.clj"]}
  :dependencies ([org.clojure/clojure "1.2.1"]
                 ; Output
                 [org.clojure/data.json "0.1.1"]
                 [clojure-csv "1.3.2"]
                 [clj-yaml "0.3.0"]
                 [enlive "1.0.0"]
                 [hiccup "0.3.6"]
                 ; Web
                 [ring/ring-core "0.3.11"]
                 [ring/ring-devel "0.3.11"]
                 [clout "0.4.1"]
                 [valip "0.2.0"]
                 [clj-http "0.1.3"]
                 ; Misc
                 [lamina "0.4.0-beta2-SNAPSHOT"]
                 [clj-time "0.3.0"]
                 [inflections "0.5.2"]
                 [faker "0.2.2"]
                 [commons-codec "1.5"]
                 [commons-email "1.1"])
  :dev-dependencies ([ring-mock "0.1.1"]
                     [ring-serve "0.1.1"]
                     [midje "1.2.0"]
                     [lein-midje "1.0.3"]
                     [congomongo "0.1.7-SNAPSHOT"]
                     [org.clojars.weavejester/autodoc "0.9.0"]))
