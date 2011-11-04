(ns formfinger.test
  (:use formfinger.fields, midje.sweet))

(facts "about required"
  ((:pred (required)) "s") => true
  ((:pred (required)) "")  => false
  ((:pred (required)) nil) => false)

(let [low (:pred (pattern #"[a-z]"))]
  (facts "about pattern"
    (low "a") => true
    (low "A") => false))

(facts "about alphanumeric"
  ((:pred (alphanumeric)) "aB0") => true
  ((:pred (alphanumeric)) ":-)") => false)

(facts "about non-confusing"
  ((:pred (non-confusing)) "amazing") => true
  ((:pred (non-confusing)) "аmazing") => false) ; Cyrillic а != Latin a

(facts "about not-in"
  ((:pred (not-in ["about"])) "myUser") => true
  ((:pred (not-in ["about"])) "about")  => false)

(facts "about maxlength"
  ((:pred (maxlength 3)) "123")  => true
  ((:pred (maxlength 3)) "1234") => false)

(facts "about minlength"
  ((:pred (minlength 3)) "123") => true
  ((:pred (minlength 3)) "12")  => false)

(facts "about email-field"
  ((:pred (email-field)) "me@myfreeweb.ru") => true
  ((:pred (email-field)) "not.an.email")    => false)

(facts "about url-field"
  ((:pred (url-field)) "http://floatboth.com") => true
  ((:pred (url-field)) "not@an.address!!")     => false)

(facts "about ipv4-field"
  ((:pred (ipv4-field)) "127.0.0.1")       => true
  ((:pred (ipv4-field)) "255.255.255.255") => true
  ((:pred (ipv4-field)) "256.0.0.0")       => false
  ((:pred (ipv4-field)) "127.0.lolwut")    => false)

(facts "about color-field"
  ((:pred (color-field)) "1f4")     => true
  ((:pred (color-field)) "#00fF00") => true
  ((:pred (color-field)) "#FFail")  => false)

(facts "about date-field"
  ((:pred (date-field)) "2011-11-02") => true
  ((:pred (date-field)) "not-a-date") => false)

(facts "about time-field"
  ((:pred (time-field)) "10:01")  => true
  ((:pred (time-field)) "lolwtf") => false)

(facts "about number-field"
  ((:pred (number-field)) "1234") => true
  ((:pred (number-field)) "-123") => true
  ((:pred (number-field)) "word") => false)

(let [gte-ten (:pred (nmin 10))]
  (facts "about nmin"
    (gte-ten "10") => true
    (gte-ten "9")  => false))

(let [lte-ten (:pred (nmax 10))]
  (facts "about nmax"
    (lte-ten "10") => true
    (lte-ten "11") => false))

(let [btw (:pred (nbetween 10 15))]
  (facts "about nbetween"
    (btw "10") => true
    (btw "15") => true
    (btw "9")  => false
    (btw "16") => false))