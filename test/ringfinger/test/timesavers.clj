(ns ringfinger.test.timesavers
  (:use ringfinger.timesavers.hooks, midje.sweet))

(fact ((make-slug-for :t) {:t "My F. Title"}) => {:t "My F. Title" :t_slug "my-f-title"})

(fact ((safe-html :s) {:s "<b><script>alert('xss lol');</script></b>"}) => {:s "<b></b>"})

(facts "about escape-input"
  ((escape-input :i :html) {:i "<tag></tag>"})    => {:i "&lt;tag&gt;&lt;&#x2F;tag&gt;"}
  ((escape-input :i :urlpart) {:i "hello world"}) => {:i "hello+world"}
  ((escape-input :i :css) {:i "yo "})             => {:i "yo\\20"})
