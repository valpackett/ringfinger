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
  (is (= ((:pred (email-field)) "me@myfreeweb.ru") true))
  (is (= ((:pred (email-field)) "not.an.email")    false)))

(deftest t-url
  (is (= ((:pred (url-field)) "http://floatboth.com") true))
  (is (= ((:pred (url-field)) "not@an.address!!")     false)))

(deftest t-ipv4
  (is (= ((:pred (ipv4-field)) "127.0.0.1")       true))
  (is (= ((:pred (ipv4-field)) "255.255.255.255") true))
  (is (= ((:pred (ipv4-field)) "256.0.0.0")       false))
  (is (= ((:pred (ipv4-field)) "127.0.lolwut")    false)))

(deftest t-color
  (is (= ((:pred (color-field)) "1f4")     true))
  (is (= ((:pred (color-field)) "#00fF00") true))
  (is (= ((:pred (color-field)) "#FFail")  false)))

(deftest t-date
  (is (= ((:pred (date-field)) "2011-11-02") true))
  (is (= ((:pred (date-field)) "not-a-date") false)))

(deftest t-time
  (is (= ((:pred (time-field)) "10:01") true))
  (is (= ((:pred (time-field)) "lolwtf") false)))

(deftest t-number
  (is (= ((:pred (number-field)) "1234") true))
  (is (= ((:pred (number-field)) "-123") true))
  (is (= ((:pred (number-field)) "word") false)))

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
