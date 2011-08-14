(ns ringfinger.test.util
  (:use ringfinger.util, clojure.test))

(deftest t-sort-maps
  (let [m [{:a 1 :b 1} {:a 1 :b 2} {:a 2 :b 1} {:a 2 :b 2}]]
    (is (= (sort-maps m {:a 1 :b -1}) '({:a 1 :b 2} {:a 1 :b 1} {:a 2 :b 2} {:a 2 :b 1})))
    (is (= (sort-maps m {:a -1 :b 1}) '({:a 2 :b 1} {:a 2 :b 2} {:a 1 :b 1} {:a 1 :b 2})))))

(deftest t-haz
  (is (= (haz? [:a :b] :a) true))
  (is (= (haz? [:a :b] :c) false)))
