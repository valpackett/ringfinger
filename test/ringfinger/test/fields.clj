(ns ringfinger.test.fields
  (:use clojure.test, ringfinger.fields))

(deftest t-required
  (is (= ((:pred (required)) "s") true))
  (is (= ((:pred (required)) "")  false))
  (is (= ((:pred (required)) nil) false)))

(deftest t-pattern
  (let [low (:pred (pattern #"[a-z]"))]
    (is (= (low "a") true))
    (is (= (low "A") false))))

(deftest t-alphanumeric
  (is (= ((:pred (alphanumeric)) "aB0") true))
  (is (= ((:pred (alphanumeric)) ":-)") false)))

(deftest t-maxlength
  (is (= ((:pred (maxlength 3)) "123")  true))
  (is (= ((:pred (maxlength 3)) "1234") false)))

(deftest t-minlength
  (is (= ((:pred (minlength 3)) "123") true))
  (is (= ((:pred (minlength 3)) "12")  false)))

(deftest t-email
  (is (= ((:pred (email)) "me@myfreeweb.ru") true))
  (is (= ((:pred (email)) "not.an.email")    false)))

(deftest t-url
  (is (= ((:pred (url)) "http://floatboth.com") true))
  (is (= ((:pred (url)) "not@an.address!!")     false)))

(deftest t-ipv4
  (is (= ((:pred (ipv4)) "127.0.0.1")       true))
  (is (= ((:pred (ipv4)) "255.255.255.255") true))
  (is (= ((:pred (ipv4)) "256.0.0.0")       false))
  (is (= ((:pred (ipv4)) "127.0.lolwut")    false)))

(deftest t-color
  (is (= ((:pred (color)) "1f4")     true))
  (is (= ((:pred (color)) "#00fF00") true))
  (is (= ((:pred (color)) "#FFail")  false)))

(deftest t-date
  (is (= ((:pred (date)) "2011-11-02") true))
  (is (= ((:pred (date)) "not-a-date") false)))

(deftest t-number
  (is (= ((:pred (number)) "1234") true))
  (is (= ((:pred (number)) "-123") true))
  (is (= ((:pred (number)) "word") false)))

(deftest t-nmin
  (let [gte-ten (:pred (nmin 10))]
    (is (= (gte-ten "10") true))
    (is (= (gte-ten "9")  false))))

(deftest t-nmax
  (let [lte-ten (:pred (nmax 10))]
    (is (= (lte-ten "10") true))
    (is (= (lte-ten "11") false))))

(deftest t-nbetween
  (let [btw (:pred (nbetween 10 15))]
    (is (= (btw "10") true))
    (is (= (btw "15") true))
    (is (= (btw "9")  false))
    (is (= (btw "16") false))))
