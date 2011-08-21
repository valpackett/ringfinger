(ns ringfinger.test.timesavers
  (:use ringfinger.timesavers.hooks, midje.sweet))

(fact ((make-slug-for :t) {:t "My F. Title"}) => {:t "My F. Title" :t_slug "my-f-title"})

(fact ((safe-html :s) {:s "<b><script>alert('xss lol');</script></b>"}) => {:s "<b></b>"})

(facts "about escape-input"
  ((escape-input :html    :i) {:i "<tag></tag>"}) => {:i "&lt;tag&gt;&lt;&#x2F;tag&gt;"}
  ((escape-input :urlpart :i) {:i "hello world"}) => {:i "hello+world"}
  ((escape-input :js      :i) {:i "Hi :)"})       => {:i "Hi\\x20\\x3a\\x29"}
  ((escape-input :css     :i) {:i "yo "})         => {:i "yo\\20"})
