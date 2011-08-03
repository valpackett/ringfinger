(ns ringfinger.test.timesavers
  (:use ringfinger.timesavers.hooks, clojure.test))

(deftest t-slug
  (is (= ((make-slug-for :t) {:t "My F. Title"})
         {:t "My F. Title" :t_slug "my-f-title"})))
