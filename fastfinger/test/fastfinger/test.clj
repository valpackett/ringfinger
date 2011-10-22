(ns fastfinger.test
  (:use (fastfinger hooks misc), midje.sweet))

(let [orig "My F. Title, можно и по-русски"]
  (fact ((make-slug-for :t) {:t orig}) => {:t orig :t_slug "my-f-title-mo-no-i-po-russki"}))

(fact ((safe-html :s) {:s "<b><script>alert('xss lol');</script></b>"}) => {:s "<b></b>"})

(facts "about escape-input"
  ((escape-input :html    :i) {:i "<tag></tag>"}) => {:i "&lt;tag&gt;&lt;&#x2F;tag&gt;"}
  ((escape-input :urlpart :i) {:i "hello world"}) => {:i "hello+world"}
  ((escape-input :js      :i) {:i "Hi :)"})       => {:i "Hi\\x20\\x3a\\x29"}
  ((escape-input :css     :i) {:i "yo "})         => {:i "yo\\20"})

(facts "about gravatar"
  (gravatar "floatboth@me.com" 200) => "http://www.gravatar.com/avatar/03c738a97e4f7e8dd46a846224fb275b?s=200"
  (gravatar "floatboth@me.com")     => "http://www.gravatar.com/avatar/03c738a97e4f7e8dd46a846224fb275b")