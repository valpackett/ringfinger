(ns ringfinger.test.timesavers
  (:use ringfinger.timesavers.hooks, clojure.test))

(deftest t-slug
  (is (= ((make-slug-for :t) {:t "My F. Title"})
         {:t "My F. Title" :t_slug "my-f-title"})))

(deftest t-safe-html
  (is (= ((safe-html :s) {:s "<b><script>alert('xss lol');</script></b>"})
         {:s "<b></b>"})))

(deftest t-escape-input
  (is (= ((escape-input :i :html) {:i "<tag></tag>"}) {:i "&lt;tag&gt;&lt;&#x2F;tag&gt;"}))
  (is (= ((escape-input :i :urlpart) {:i "hello world"}) {:i "hello+world"})))
