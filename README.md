# ringfinger [![Build Status](https://secure.travis-ci.org/myfreeweb/ringfinger.png)](http://travis-ci.org/) #
Fun, fast, secure web development on top of [Clojure](http://clojure.org)/[Ring](https://github.com/mmcgrana/ring).
A framework for the age of non-relational databases, content delivery networks, cloud hosting, HTML5 and coffee.
InstaRESTful, if you like the hipster-speak. Named after a great Nine Inch Nails song.
Not ready yet, but a lot of things work, including MongoDB support, ready-to-use customizable login/signup system and more.

Check out the [website](http://ringfinger.floatboth.com) for a live demo.

## Get excited ##

```clojure
(ns superapp.core
  (:use (ringfinger core resource fields auth-routes),
        ringfinger.db.mongodb, ringfinger.timesavers.hooks,
        ring.util.serve))

(def mymongo (mongodb "mydb"))

(defresource contacts
  {:db mymongo
   :pk :name_slug
   :hooks {:data (make-slug-for :name)}}
  [:name  (required) "sorry, anonymous"]
  [:bday  (date-field) "invalid date"]
  [:email (email-field) "invalid email"])

(defapp myapp
        {:static-dir "custom_static_name"
         :session-db mymongo
         :auth-db mymongo}
        contacts
        (auth-routes {:db mymongo}))

(serve myapp 3000)
```

or something like that. You can do create/read/update/delete operations on the same resource with a browser
(there are default HTML templates, like in Rails) or something that supports JSON, YAML, CSV or XML.
Yeah, URLs are the same. The app is an API, and HTML is just another output format.
The `Accept` HTTP header (or `?_format=something`) is what "separates" the API.
And insert some example data by visiting `/contacts/_create_fakes` (only in development environment, of course). Nice, eh?

You can customize the behavior via hooks (eg. if you need to automatically add URL-friendly "slugs", as in the example, or automatic timestamps),
via providing [Lamina](https://github.com/ztellman/lamina) channels and subscribing to them (eg. if you need real-time push)
or adding custom actions, eg. for voting in a poll.
You also can use lower-level auth/database/validation/output/routing APIs if you can't fit something into these RESTful constraints.

## Coming "soon" ##

- optionally separating create/index and view/edit pages in html
- rate limiting (per user)
- queries in url prefixes (eg. :username prefix to allow :username/collname/:pk with the same pk values)
- pagination link (including HTTP Link header) things
- enlive views quick builder
- cyrillic transliteration for slugs
- automatic API docs route for resources w/ examples using faker
- [Swagger](http://swagger.wordnik.com) implementation
- automatic javascript model definitions for client-side mvc per resource, using clojurescript
- live reloader (long poll, on disconnect: try to reconnect, when server is up, reload)
- middleware like django-paranoid-sessions
- extracting security, db and output once they'll be more stable and have more features
- atom feeds for resources
- invite mode for registration
- file attachments w/ GridFS support
- asset system that doesn't suck & supports preprocessors, css sprite making & completely dynamic in dev mode & uploads to clouds for production with a lein task, supporting attachment storages
- pre-made Lamina subscribers for Pusher/pubsub.io/hook.io
- FleetDB, CouchDB support
- cloudy packages (ringfinger-aws = S3 + SES + SimpleDB, ringfinger-gae = Blobstore + Mail + Datastore)
- read-only mode
- database and attachment migrations (eg. mongodb + gridfs to simpledb + s3)
- field renaming
- (fun!) external package for outputting data in native formats - python pickle using jython, php serialized array using quercus, yaml for ruby
- easy full text search (elasticsearch, lucene): lamina subscriber + route for querying
