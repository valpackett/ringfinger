(ns ringfinger.test.util
  (:use ringfinger.util, midje.sweet))

(facts "about nice-count"
  (nice-count 0 "post") => "no posts"
  (nice-count 1 "post") => "one post"
  (nice-count 2 "post") => "two posts"
  (nice-count 99 "problem") => "99 problems")

(facts "about sort-maps"
  (let [m [{:a 1 :b 1} {:a 1 :b 2} {:a 2 :b 1} {:a 2 :b 2}]]
    (sort-maps m {:a 1 :b -1}) => '({:a 1 :b 2} {:a 1 :b 1} {:a 2 :b 2} {:a 2 :b 1})
    (sort-maps m {:a -1 :b 1}) => '({:a 2 :b 1} {:a 2 :b 2} {:a 1 :b 1} {:a 1 :b 2})))

(facts "about haz?"
  (haz? [:a :b] :a) => true
  (haz? [:a :b] :c) => false)
