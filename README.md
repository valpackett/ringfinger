# ringfinger #
Fun, fast, secure web development on top of [Clojure](http://clojure.org)/[Ring](https://github.com/mmcgrana/ring).
Named after a great Nine Inch Nails song.

## Get excited ##

    (ns superapp.core
      (:use (ringfinger core resource validation) ringfinger.db.inmem ring.adapter.jetty))
    
    (defresource contacts
      {:db inmem
       :pk :name}
      [:name (required) "sorry, anonymous"]
      [:name (alphanumeric) "no wrong stuff in your name plz"]
      [:email (email) "invalid email"])
    
    (defapp myapp {} contacts)
    (run-jetty myapp {:port 8080})

or something like that. You can do create/read/update/delete operations on the same resource with a browser (there are default HTML templates, like in Rails) or something that supports JSON or XML.

You can customize the behavior via hooks (eg. if you need to automatically add URL-friendly "slugs" or post dates/times) or (coming soon) a pub/sub like thing (eg. for logging or stats or real-time pushing to Pusher). You also can use lower-level database/validation/output/routing APIs if you can't fit something into these RESTful constraints.