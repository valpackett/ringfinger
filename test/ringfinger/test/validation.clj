(ns ringfinger.test.validation
  (:use clojure.test, ringfinger.validation))

(deftest t-required
  (is (= ((:clj (required)) "s") true))
  (is (= ((:clj (required)) "")  false))
  (is (= ((:clj (required)) nil) false)))

(deftest t-pattern
  (let [low (:clj (pattern #"[a-z]"))]
    (is (= (low "a") true))
    (is (= (low "A") false))))

(deftest t-alphanumeric
  (is (= ((:clj (alphanumeric)) "aB0") true))
  (is (= ((:clj (alphanumeric)) ":-)") false)))

(deftest t-maxlength
  (is (= ((:clj (maxlength 3)) "123")  true))
  (is (= ((:clj (maxlength 3)) "1234") false)))

(deftest t-minlength
  (is (= ((:clj (minlength 3)) "123") true))
  (is (= ((:clj (minlength 3)) "12")  false)))

(deftest t-email
  (is (= ((:clj (email)) "me@myfreeweb.ru") true))
  (is (= ((:clj (email)) "not.an.email")    false)))

(deftest t-url
  (is (= ((:clj (url)) "http://floatboth.com") true))
  (is (= ((:clj (url)) "not@an.address!!")     false)))

(deftest t-color
  (is (= ((:clj (color)) "1f4")     true))
  (is (= ((:clj (color)) "#00fF00") true))
  (is (= ((:clj (color)) "#FFail")  false)))

(deftest t-date
  (is (= ((:clj (date)) "2011-11-02") true))
  (is (= ((:clj (date)) "not-a-date") false)))

(deftest t-number
  (is (= ((:clj (number)) "1234") true))
  (is (= ((:clj (number)) "-123") true))
  (is (= ((:clj (number)) "word") false)))

(deftest t-nmin
  (let [gte-ten (:clj (nmin 10))]
    (is (= (gte-ten "10") true))
    (is (= (gte-ten "9")  false))))

(deftest t-nmax
  (let [lte-ten (:clj (nmax 10))]
    (is (= (lte-ten "10") true))
    (is (= (lte-ten "11") false))))

(deftest t-nbetween
  (let [btw (:clj (nbetween 10 15))]
    (is (= (btw "10") true))
    (is (= (btw "15") true))
    (is (= (btw "9")  false))
    (is (= (btw "16") false))))
