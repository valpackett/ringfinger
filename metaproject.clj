(use 'clojure.java.shell)

; Ringfinger's version is defined by git tags
(def version
  (let [out (:out (sh "git" "describe" "--abbrev=0" "--tags"))]
    (.substring out 0 (- (count out) 1)))) ; remove the \n

(def clj-version "1.2.1")