(ns formfinger.test
  (:refer-clojure :exclude [extend])
  (:use (formfinger fields core), midje.sweet, clj-time.core))

(facts "about the form API"
  (def frm
    {:name [(f (required) "This is required")
            (f (alphanumeric) "Must be alphanumeric")]
     :addr {:city [(f (maxlength 1024) "WTF")]
            :street [(f (maxlength 2048) "LOL")]}
     :sites (many {:name [(f (minlength 5) "wtf")]
                   :url [(f (url-field) "Not an URL")]})})
  (def inv-data
    {:addr {:city "Moscow"}
     :sites [{:name "Ringfinger"
              :url "http://ringfinger.floatboth.com"}
             {:name "Floatboth"
              :url "floatboth@com"}]})
  (def val-data
    {:name "v"
     :sites [{:name "CSSPrefixer"
              :url "http://cssprefixer.appspot.com"}]})

  (-> ((make-data-pre-hook {:date [(f (date-field) "")]})
         {:date "2010-01-10"})
      :date class) => org.joda.time.DateTime
  (get-required-fields frm) => {:name true}
  (get-defaults {:bool [(f (checkbox) "")]}) => {:bool ""}
  (validate frm (make-fake frm)) => nil
  (validate frm inv-data) => {:name  ["This is required" "Must be alphanumeric"]
                              :sites [nil {:url ["Not an URL"]}]}
  (validate frm val-data) => nil
  (render {:style :label} frm) => "<input name=\"csrftoken\" type=\"hidden\" /><div><label for=\"name\">Name</label><input id=\"name\" name=\"name\" pattern=\"[0-9a-zA-Z]+\" required=\"required\" /></div><fieldset id=\"addr\"><h2>Addr</h2><div><label for=\"city\">City</label><input id=\"city\" maxlength=\"1024\" name=\"city\" /></div><div><label for=\"street\">Street</label><input id=\"street\" maxlength=\"2048\" name=\"street\" /></div></fieldset><fieldset id=\"sites\"><h2>Sites</h2><div><label for=\"name\">Name</label><input id=\"name\" name=\"name\" pattern=\".{5,}\" /></div><div><label for=\"url\">Url</label><input id=\"url\" name=\"url\" type=\"url\" /></div></fieldset>"
  (render {:style :placeholder} frm inv-data (validate frm inv-data))
    => "<input name=\"csrftoken\" type=\"hidden\" /><div><input id=\"name\" name=\"name\" pattern=\"[0-9a-zA-Z]+\" placeholder=\"Name\" required=\"required\" /><div class=\"error\">This is required</div><div class=\"error\">Must be alphanumeric</div></div><fieldset id=\"addr\"><h2>Addr</h2><div><input id=\"city\" maxlength=\"1024\" name=\"city\" placeholder=\"City\" value=\"Moscow\" /></div><div><input id=\"street\" maxlength=\"2048\" name=\"street\" placeholder=\"Street\" /></div></fieldset><fieldset id=\"sites\"><h2>Sites</h2><div><input id=\"name\" name=\"name\" pattern=\".{5,}\" placeholder=\"Name\" /></div><div><input id=\"url\" name=\"url\" placeholder=\"Url\" type=\"url\" /></div></fieldset>")

; Fields
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
  ((:pred (date-field)) "not-a-date") => false
  (year ((:pre-hook (date-field)) "2011-11-02")) => 2011
  ((:view (date-field)) ((:post-hook (date-field)) (date-time 2001 10 02))) => "2001-10-02")

(facts "about time-field"
  ((:pred (time-field)) "10:01")  => true
  ((:pred (time-field)) "lolwtf") => false
  (hour ((:pre-hook (time-field)) "14:10")) => 14
  ((:view (time-field)) ((:post-hook (time-field)) (date-time 2001 10 02 00 42))) => "00:42")

(facts "about date-date-time-field"
  ((:pred (date-time-field)) "2001-10-02T10:01Z")  => true
  ((:pred (date-time-field)) "lolwtf") => false
  (hour ((:pre-hook (date-time-field)) "2010-01-02T14:10Z")) => 14
  ((:view (date-time-field)) ((:post-hook (date-time-field)) (date-time 2001 10 02 00 42))) => "2001-10-02T00:42Z")

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
