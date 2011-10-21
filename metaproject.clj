(use 'clojure.java.shell)

; Ringfinger's version is defined by git tags
(def version
  (let [out (:out (sh "git" "describe" "--abbrev=0" "--tags"))]
    (.substring out 0 (- (count out) 1)))) ; remove the \n
(def clj-version   "1.2.1")
(def ring-version  "1.0.0-beta2")
(def midje-version "1.2.0")

(def fingers ["toolfinger" "secfinger" "mailfinger" "basefinger"])