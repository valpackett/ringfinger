# ringfinger #
Fun, fast, secure web development on top of [Clojure](http://clojure.org)/[Ring](https://github.com/mmcgrana/ring).
A framework for the age of non-relational databases, content delivery networks, cloud hosting, HTML5 and coffee.
Named after a great Nine Inch Nails song.
Not ready yet, but a lot of things work, including MongoDB support.

## Get excited ##

    (ns superapp.core
      (:use (ringfinger core resource validation) ringfinger.db.inmem ring.adapter.netty))
    
    (defresource contacts
      {:db inmem
       :pk :name}
      [:name (required) "sorry, anonymous"]
      [:name (alphanumeric) "no wrong stuff in your name plz"]
      [:email (email) "invalid email"])
    
    (defapp myapp {} contacts)
    (run-netty myapp {:port 8080})

or something like that. You can do create/read/update/delete operations on the same resource with a browser (there are default HTML templates, like in Rails) or something that supports JSON or XML.

You can customize the behavior via hooks (eg. if you need to automatically add URL-friendly "slugs" or post dates/times) and via providing [Lamina](https://github.com/ztellman/lamina) channels and subscribing to them (eg. if you need real-time push).
You also can use lower-level database/validation/output/routing APIs if you can't fit something into these RESTful constraints.

## Coming "soon" ##

- a pre-made hook for making slugs ("My Title" -> "my-title") w/ [inflections-clj](https://github.com/r0man/inflections-clj)
- middleware like django-paranoid-sessions
- pre-made Lamina subscribers for Pusher/pubsub.io/hook.io
- sorting
- pagination
- atom/rss feeds for resources
- asset system that doesn't suck & supports preprocessors (clojurescript, coffeescript, sass, whatever) & completely dynamic in dev mode & uploads to clouds for production
- file attachments
- cloudy packages (ringfinger-aws = S3 + SES + SimpleDB, ringfinger-gae = Blobstore + Mail + Datastore)
- (fun!) external package for outputting data in native formats - python pickle using jython, php serialized array using quercus, yaml for ruby
- easy full text search (elasticsearch, lucene)
- automatic javascript model definitions for client-side mvc per resource, using clojurescript
- "designer-friendly" templates (mustache w/ support for helpers?)
- DOCUMENTATION!
