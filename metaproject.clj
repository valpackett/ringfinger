(use 'clojure.java.shell)

; Ringfinger's version is defined by git tags
(def version
  (let [out (:out (sh "git" "describe" "--abbrev=0" "--tags"))]
    (.substring out 0 (- (count out) 1)))) ; remove the \n

(def dep-versions
  {"org.clojure/clojure" "1.3.0"
   "org.clojure/data.json" "0.1.1"
   "org.clojars.weavejester/autodoc" "0.9.0"
   "org.mozilla/rhino" "1.7R3"
   "com.ibm.icu/icu4j" "4.8.1.1"
   "ring/ring-core" "1.0.1"
   "ring/ring-devel" "1.0.1"
   "ring-mock" "0.1.1"
   "ring-serve" "0.1.1"
   "midje" "1.3.1"
   "hiccup" "0.3.8"
   "clout" "1.0.0"
   "valip" "0.2.0"
   "faker" "0.2.2"
   "enlive" "1.0.0"
   "lamina" "0.4.1-SNAPSHOT"
   "clj-time" "0.3.4"
   "clj-http" "0.2.7"
   "clj-yaml" "0.3.1"
   "clojure-csv" "1.3.2"
   "inflections" "0.6.5-SNAPSHOT"
   "congomongo" "0.1.7"
   "commons-codec" "1.5"
   "commons-email" "1.1"})

(def fingers ["toolfinger" "secfinger"  "mailfinger"
              "basefinger" "corefinger" "formfinger"
              "authfinger" "restfinger" "fastfinger"])

(defn- in-deps [fingers ext dev]
  (let [ext (if (not dev) (cons "org.clojure/clojure" ext) ext)
        ext (if dev (conj ext "midje") ext)
        ext-deps (select-keys dep-versions ext)
        fng-deps (zipmap (map #(str "ringfinger/" %) fingers) (repeat version))
        deps (merge ext-deps fng-deps)
        deps (zipmap (map symbol (keys deps)) (vals deps))]
    (into [] deps)))

(defn deps     [fingers ext] (in-deps fingers ext false))
(defn dev-deps [fingers ext] (in-deps fingers ext true))
