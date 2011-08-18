(ns ringfinger.test.timesavers
  (:use ringfinger.timesavers.hooks, clojure.test))

(deftest t-slug
  (is (= ((make-slug-for :t) {:t "My F. Title"})
         {:t "My F. Title" :t_slug "my-f-title"})))

(deftest t-safe-html
  (is (= ((safe-html :s) {:s "<b><script>alert('xss lol');</script></b>"})
         {:s "<b></b>"})))
