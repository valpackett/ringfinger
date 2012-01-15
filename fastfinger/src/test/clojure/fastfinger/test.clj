(ns fastfinger.test
  (:use (fastfinger hooks misc), midje.sweet))

(facts "about make-slug-for"
  ((make-slug-for :t) {:t "My F. Title, можно и по-русски"}) => (contains {:t_slug "my-f-title-mo-no-i-po-russki"})
  ((make-slug-for :t) {:t "$5.99 iPad app"}) => (contains {:t_slug "5-dollars-99-cents-ipad-app"})
  ((make-slug-for :t) {:t "Rock & Roll"}) => (contains {:t_slug "rock-and-roll"})
  ((make-slug-for :t) {:t "we're the 99%"}) => (contains {:t_slug "we-are-the-99-percent"}))

(fact ((safe-html :s) {:s "<b><script>alert('xss lol');</script></b>"}) => {:s "<b></b>"})

(facts "about escape-input"
  ((escape-input :html    :i) {:i "<tag></tag>"}) => {:i "&lt;tag&gt;&lt;&#x2F;tag&gt;"}
  ((escape-input :urlpart :i) {:i "hello world"}) => {:i "hello+world"}
  ((escape-input :js      :i) {:i "Hi :)"})       => {:i "Hi\\x20\\x3a\\x29"}
  ((escape-input :css     :i) {:i "yo "})         => {:i "yo\\20"})

(facts "about gravatar"
  (gravatar "floatboth@me.com" 200) => "http://www.gravatar.com/avatar/03c738a97e4f7e8dd46a846224fb275b?s=200"
  (gravatar "floatboth@me.com")     => "http://www.gravatar.com/avatar/03c738a97e4f7e8dd46a846224fb275b")
