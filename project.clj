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
            :namespaces-to-document ~fingers
            :load-except-list [#"test/" #"project\.clj" #"website/" #"autodoc"]}
  :sub ~fingers
  :deps ~(deps fingers [])
  :dev-dependencies ~(dev-deps []
                               ["ring-serve"
                                "congomongo"
                                "org.clojars.weavejester/autodoc"]))
