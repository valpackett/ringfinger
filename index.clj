{:namespaces
 ({:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/authfinger.core-api.html",
   :name "authfinger.core",
   :doc
   "Low-level authorization API (creating users, getting users after checking) and the auth middleware."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/authfinger.routes-api.html",
   :name "authfinger.routes",
   :doc
   "Authorization routes -- registration (if you really want, even with\ne-mail confirmation) and logging in/out."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/basefinger.core-api.html",
   :name "basefinger.core",
   :doc "Document-oriented database abstraction."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/basefinger.inmem-api.html",
   :name "basefinger.inmem",
   :doc nil}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/basefinger.mongodb-api.html",
   :name "basefinger.mongodb",
   :doc
   "MongoDB support. Don't forget to add Congomongo to your deps!"}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/basefinger.session-api.html",
   :name "basefinger.session",
   :doc "Allows you to store sessions in a database."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/corefinger.core-api.html",
   :name "corefinger.core",
   :doc "A routing system and more"}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/corefinger.middleware-api.html",
   :name "corefinger.middleware",
   :doc "Common Ring middleware"}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/fastfinger.actions-api.html",
   :name "fastfinger.actions",
   :doc "Functions for easy creation of actions"}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/fastfinger.hooks-api.html",
   :name "fastfinger.hooks",
   :doc
   "Ready-to-use hooks for use with ringfinger.resource. Save even more time!"}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/fastfinger.misc-api.html",
   :name "fastfinger.misc",
   :doc "Useful common functions for web apps"}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/formfinger.field-helpers-api.html",
   :name "formfinger.field-helpers",
   :doc
   "The only useful thing for users here is form-fields.\nEverything else is kinda internal, but feel free to use this if you\ndon't use ringfinger.resource and write all the boilerplate by hand."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/formfinger.fields-api.html",
   :name "formfinger.fields",
   :doc
   "All functions in this module make fields for you to use\nwith restfinger.core.\nThey're defined as functions even when they need no\ncustomization just for consistency."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/mailfinger.console-api.html",
   :name "mailfinger.console",
   :doc nil}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/mailfinger.postmark-api.html",
   :name "mailfinger.postmark",
   :doc nil}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/mailfinger.sendmail-api.html",
   :name "mailfinger.sendmail",
   :doc nil}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/mailfinger.smtp-api.html",
   :name "mailfinger.smtp",
   :doc nil}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/restfinger.core-api.html",
   :name "restfinger.core",
   :doc
   "This module saves your time by writing all the Create/Read/Update/Delete\nboilerplate for you. Flash messages, validation, inserting example data,\ncustomization via hooks, actions and channels -- you name it, this module does it."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/restfinger.output-api.html",
   :name "restfinger.output",
   :doc "The output system used by ringfinger.resource."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/secfinger-api.html",
   :name "secfinger",
   :doc
   "Some security-related Ring middleware and other related functions."}
  {:source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/toolfinger-api.html",
   :name "toolfinger",
   :doc
   "Various functions and macros used by Ringfinger. Feel free to use them too!"}),
 :vars
 ({:arglists ([db coll username password]),
   :name "get-user",
   :namespace "authfinger.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//authfinger.core-api.html#authfinger.core/get-user",
   :doc
   "Returns a user from coll in db with given username and password if the password is valid",
   :var-type "function",
   :line 26,
   :file "authfinger/core.clj"}
  {:arglists ([db coll user password]),
   :name "make-user",
   :namespace "authfinger.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//authfinger.core-api.html#authfinger.core/make-user",
   :doc
   "Creates a user in coll in db with given fields (:username and whatever you need) and password",
   :var-type "function",
   :line 34,
   :file "authfinger/core.clj"}
  {:arglists ([salt & body]),
   :name "with-salt",
   :namespace "authfinger.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//authfinger.core-api.html#authfinger.core/with-salt",
   :doc
   "Changes the fixed part of the salt used for password hashing.\nWrap both app call and auth-routes calls (they're usually nested,\nbut you're free to (def something (auth-routes {…})), right?).\nAnd (make|get)-user call if you do them (in tests?)\nChange the salt once to a random value and NEVER change it later\n(or your app's users will seriously hate you)",
   :var-type "macro",
   :line 12,
   :file "authfinger/core.clj"}
  {:arglists
   ([handler]
    [handler
     {:keys [db coll], :or {db inmem, coll :ringfinger_auth}}]),
   :name "wrap-auth",
   :namespace "authfinger.core",
   :source-url nil,
   :raw-source-url nil,
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//authfinger.core-api.html#authfinger.core/wrap-auth",
   :doc
   "Ring middleware that adds :user if there's a user logged in. Supports session/form-based auth and HTTP Basic auth",
   :var-type "function",
   :line 44,
   :file "authfinger/core.clj"}
  {:arglists
   ([{:keys
      [views flash url-base redir-to redir-p db coll confirm fields],
      :or
      {flash
       {:login-success "Welcome back!",
        :login-invalid "Wrong username/password.",
        :signup-success "Welcome!",
        :logout "Good bye!",
        :confirm-success "Welcome!",
        :confirm-fail "Invalid confirmaton key."},
       db inmem,
       confirm nil,
       redir-to "/",
       redir-p "redirect",
       coll :ringfinger_auth,
       views auth-demo-views,
       url-base "/auth/",
       fields
       [[:username (required) "Shouldn't be empty"]
        [:password (required) "Shouldn't be empty"]
        [:password
         (minlength 6)
         "Should be at least 6 characters"]]}}]),
   :name "auth-routes",
   :namespace "authfinger.routes",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4524e6c620a943f82bd4973919ebf3830f0e6a66/authfinger/src/authfinger/routes.clj#L61",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4524e6c620a943f82bd4973919ebf3830f0e6a66/authfinger/src/authfinger/routes.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//authfinger.routes-api.html#authfinger.routes/auth-routes",
   :doc
   "Creates auth routes with given options:\n:db, :coll -- database and collection\n:views -- map of views (:login, :signup and :confirm)\n:flash -- map of flash messages (:login-success, :login-invalid, :signup-success, :logout, :confirm-success and :confirm-fail)\n:url-base -- the starting part of auth URLs, the default is /auth/\n:redir-to -- where to redirect after a successful login/signup if there's no referer, the default is /\n:redir-param -- query string parameter for keeping the redirect url, the default is _redirect, you generally don't need to care about this\n:confirm -- if you want email confirmation, map of parameters :mailer, :from, :email-field (default is :username), :subject, :mail-template\n:fields -- list of validations, defaults are: requiring username and at least 6 characters password",
   :var-type "function",
   :line 61,
   :file
   "/home/ubuntu/Code/open/ringfinger/./authfinger/src/authfinger/routes.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/basefinger/src/basefinger/inmem.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/basefinger/src/basefinger/inmem.clj#L7",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//basefinger.inmem-api.html#basefinger.inmem/inmem",
   :namespace "basefinger.inmem",
   :line 7,
   :file
   "/home/ubuntu/Code/open/ringfinger/./basefinger/src/basefinger/inmem.clj",
   :var-type "var",
   :doc
   "In-memory data storage FOR TESTING USE ONLY.\nOr for storing temporary data like sessions.",
   :name "inmem"}
  {:arglists ([]),
   :name "reset-inmem-db",
   :namespace "basefinger.inmem",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/basefinger/src/basefinger/inmem.clj#L41",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/basefinger/src/basefinger/inmem.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//basefinger.inmem-api.html#basefinger.inmem/reset-inmem-db",
   :doc "Erase EVERYTHING from the inmem database",
   :var-type "function",
   :line 41,
   :file
   "/home/ubuntu/Code/open/ringfinger/./basefinger/src/basefinger/inmem.clj"}
  {:arglists ([db] [db instances] [db username password instances]),
   :name "mongodb",
   :namespace "basefinger.mongodb",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/basefinger/src/basefinger/mongodb.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/basefinger/src/basefinger/mongodb.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//basefinger.mongodb-api.html#basefinger.mongodb/mongodb",
   :doc
   "Creates a MongoDB data storage object using given db and a list of instances.\nEach instance is a map containing :host and/or :port",
   :var-type "function",
   :line 25,
   :file
   "/home/ubuntu/Code/open/ringfinger/./basefinger/src/basefinger/mongodb.clj"}
  {:arglists ([db] [db coll]),
   :name "db-store",
   :namespace "basefinger.session",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/eb12a57a75ab8e8fd0390e0dbdb3b85b36c37f99/basefinger/src/basefinger/session.clj#L20",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/eb12a57a75ab8e8fd0390e0dbdb3b85b36c37f99/basefinger/src/basefinger/session.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//basefinger.session-api.html#basefinger.session/db-store",
   :doc
   "Creates a new SessionStore object (for ring.middleware.session) with given db and coll.\nThe default coll is :ringfinger_sessions",
   :var-type "function",
   :line 20,
   :file
   "/home/ubuntu/Code/open/ringfinger/./basefinger/src/basefinger/session.clj"}
  {:arglists
   ([{:keys
      [middleware
       session-store
       static-dir
       callback-param
       memoize-routing],
      :or
      {middleware nil,
       session-store (memory-store),
       static-dir "static",
       callback-param "callback",
       memoize-routing false}}
     &
     routes]),
   :name "app",
   :namespace "corefinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj#L50",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.core-api.html#corefinger.core/app",
   :doc
   "Creates a Ring handler with given options and routes, automatically wrapped with\nparams, session, flash, head, jsonp, length, method-override and some security middleware\n(+ stacktrace and file in development env)\nAccepted options:\n :middleware -- your custom additional middleware (use -> to add many)\n :session-store -- SessionStore for session middleware\n :static-dir -- directory with static files for serving them in development\n :callback-param -- parameter for JSONP callbacks, default is 'callback'\n :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, disabled by default",
   :var-type "function",
   :line 50,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/core.clj"}
  {:arglists ([nname options & routes]),
   :name "defapp",
   :namespace "corefinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj#L95",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.core-api.html#corefinger.core/defapp",
   :doc "Short for (def nname (app options & routes))",
   :var-type "macro",
   :line 95,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/core.clj"}
  {:arglists ([env yep nope]),
   :name "if-env",
   :namespace "corefinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj#L12",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.core-api.html#corefinger.core/if-env",
   :doc "Checks if the current RING_ENV == env",
   :var-type "macro",
   :line 12,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/core.clj"}
  {:arglists ([handler]),
   :name "nest",
   :namespace "corefinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj#L35",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.core-api.html#corefinger.core/nest",
   :doc
   "Creates an extended Ring handler ([req matches])  from a normal one.",
   :var-type "function",
   :line 35,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/core.clj"}
  {:arglists ([url handler] [url handler custom-regexps]),
   :name "route",
   :namespace "corefinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj#L39",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e30b173984c7717c46259205b52fe9719e9f02b/corefinger/src/corefinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.core-api.html#corefinger.core/route",
   :doc
   "Creates a route accepted by the app function from a URL in Clout (Sinatra-like) format\nand either a method-to-handler map, eg. {:get (fn [req matches] {:status 200 :body nil})}\nor a single handler.\nAccepts custom-regexps for finer matching (eg. only numeric ids)",
   :var-type "function",
   :line 39,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/core.clj"}
  {:arglists ([handler]),
   :name "wrap-head",
   :namespace "corefinger.middleware",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj#L13",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.middleware-api.html#corefinger.middleware/wrap-head",
   :doc "Ring middleware for handling HEAD requests properly",
   :var-type "function",
   :line 13,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/middleware.clj"}
  {:arglists ([handler]),
   :name "wrap-json-params",
   :namespace "corefinger.middleware",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj#L31",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.middleware-api.html#corefinger.middleware/wrap-json-params",
   :doc "Ring middleware for parsing JSON params",
   :var-type "function",
   :line 31,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/middleware.clj"}
  {:arglists ([handler callback-param]),
   :name "wrap-jsonp",
   :namespace "corefinger.middleware",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj#L21",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.middleware-api.html#corefinger.middleware/wrap-jsonp",
   :doc "Ring middleware for handling JSONP requests",
   :var-type "function",
   :line 21,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/middleware.clj"}
  {:arglists ([handler]),
   :name "wrap-length",
   :namespace "corefinger.middleware",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj#L6",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.middleware-api.html#corefinger.middleware/wrap-length",
   :doc "Ring middleware for adding Content-Length",
   :var-type "function",
   :line 6,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/middleware.clj"}
  {:arglists ([handler]),
   :name "wrap-method-override",
   :namespace "corefinger.middleware",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj#L45",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/09d7df54504d44fcead63a8dfc3da9bd01a93555/corefinger/src/corefinger/middleware.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//corefinger.middleware-api.html#corefinger.middleware/wrap-method-override",
   :doc
   "Ring middleware for method overriding (X-HTTP-Method-Override and _method query parameter)",
   :var-type "function",
   :line 45,
   :file
   "/home/ubuntu/Code/open/ringfinger/./corefinger/src/corefinger/middleware.clj"}
  {:arglists ([hook flash] [hook flash channel]),
   :name "make-redir-action",
   :namespace "fastfinger.actions",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/actions.clj#L7",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/actions.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fastfinger.actions-api.html#fastfinger.actions/make-redir-action",
   :doc
   "Makes an action which applies a hook to the entry it's being called on\nand redirects the user to the viewing URL, showing a flash message,\noptionally sending the result to a Lamina channel",
   :var-type "function",
   :line 7,
   :file
   "/home/ubuntu/Code/open/ringfinger/./fastfinger/src/fastfinger/actions.clj"}
  {:arglists ([context & fields]),
   :name "escape-input",
   :namespace "fastfinger.hooks",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/hooks.clj#L42",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/hooks.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fastfinger.hooks-api.html#fastfinger.hooks/escape-input",
   :doc
   "Returns a hook which escapes contents of given fields for\na given context (:html, :attr, :js, :css or :urlpart)",
   :var-type "function",
   :line 42,
   :file
   "/home/ubuntu/Code/open/ringfinger/./fastfinger/src/fastfinger/hooks.clj"}
  {:arglists ([field] [field output-field]),
   :name "make-slug-for",
   :namespace "fastfinger.hooks",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/hooks.clj#L10",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/hooks.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fastfinger.hooks-api.html#fastfinger.hooks/make-slug-for",
   :doc
   "Returns a hook which makes a slug (URL-friendly name, eg. My Article -> my-article)\nfor a given field. Default output-field is field + '_slug'.\nDon't forget that if you use a custom output-field, you need to whitelist it.\nNever returns empty values (takes a random word if the original's empty).\nTransliterates different scripts (eg. Cyrillic) into Latin",
   :var-type "function",
   :line 10,
   :file
   "/home/ubuntu/Code/open/ringfinger/./fastfinger/src/fastfinger/hooks.clj"}
  {:arglists ([field] [field moretags]),
   :name "safe-html",
   :namespace "fastfinger.hooks",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/hooks.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/hooks.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fastfinger.hooks-api.html#fastfinger.hooks/safe-html",
   :doc
   "Returns a hook which removes script, style, link, title, meta, head, body, html\nand other given tags from a string of HTML in a given field. Also adds a sandbox\nattribute to iframes. As a 'side-effect' (not in the programming sense),\nthe HTML is always valid",
   :var-type "function",
   :line 25,
   :file
   "/home/ubuntu/Code/open/ringfinger/./fastfinger/src/fastfinger/hooks.clj"}
  {:arglists
   ([email]
    [email size]
    [email size rating]
    [email size rating default-pic]),
   :name "gravatar",
   :namespace "fastfinger.misc",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/misc.clj#L8",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/fastfinger/src/fastfinger/misc.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fastfinger.misc-api.html#fastfinger.misc/gravatar",
   :doc
   "Given an email, returns a Gravatar URL.\nAutomatically uses secure.gravatar.com when inside of an HTTPS request",
   :var-type "function",
   :line 8,
   :file
   "/home/ubuntu/Code/open/ringfinger/./fastfinger/src/fastfinger/misc.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L73",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/data-post-hook-from-fields",
   :namespace "formfinger.field-helpers",
   :line 73,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a data post-hook from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "data-post-hook-from-fields"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L67",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/data-pre-hook-from-fields",
   :namespace "formfinger.field-helpers",
   :line 67,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a data pre-hook from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "data-pre-hook-from-fields"}
  {:arglists ([fields]),
   :name "defaults-from-fields",
   :namespace "formfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L37",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/defaults-from-fields",
   :doc
   "Makes a list of defaults from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :var-type "function",
   :line 37,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L44",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/fakers-from-fields",
   :namespace "formfinger.field-helpers",
   :line 44,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a list of fakers from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "fakers-from-fields"}
  {:arglists ([fields-html data errors wrap-html err-html style]),
   :name "form-fields",
   :namespace "formfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L88",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/form-fields",
   :doc
   "HTML templating helper for rendering forms. Allowed styles are :label and :placeholder",
   :var-type "macro",
   :line 88,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L61",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/get-hook-from-fields",
   :namespace "formfinger.field-helpers",
   :line 61,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a get hook from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "get-hook-from-fields"}
  {:arglists ([fields]),
   :name "html-from-fields",
   :namespace "formfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L16",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/html-from-fields",
   :doc
   "Makes a map of field names - html attributes from a list of fields, eg.\n([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']\n [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])\nbecomes ([:name {:required 'required' :maxlength 10}])",
   :var-type "function",
   :line 16,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj"}
  {:arglists ([fields]),
   :name "required-fields-of",
   :namespace "formfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L79",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/required-fields-of",
   :doc
   "Returns a list of required fields' names from a list of fields, eg.\n([:name (required) 'wtf'] [:date (date) 'lolwut'])\nbecomes\n(:name)",
   :var-type "function",
   :line 79,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj"}
  {:arglists ([fields]),
   :name "validations-from-fields",
   :namespace "formfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj#L26",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.field-helpers-api.html#formfinger.field-helpers/validations-from-fields",
   :doc
   "Makes a list of validations from a list of fields, eg.\n([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']\n [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])\nbecomes ([:name (required) 'y u no say ur name']\n         [:name (my-check) 'too long']) ; the valip format",
   :var-type "function",
   :line 26,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/field_helpers.clj"}
  {:arglists ([]),
   :name "alphanumeric",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L42",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/alphanumeric",
   :doc "Validates alphanumeric strings",
   :var-type "function",
   :line 42,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "checkbox",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L34",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/checkbox",
   :doc "A boolean field, input type=checkbox",
   :var-type "function",
   :line 34,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "color-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L97",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/color-field",
   :doc "Validates hexadecimal color codes, input type=color",
   :var-type "function",
   :line 97,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "date-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L102",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/date-field",
   :doc "Validates/parses/outputs dates, input type=date",
   :var-type "function",
   :line 102,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([] [local]),
   :name "date-time-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L128",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/date-time-field",
   :doc "Validates/parses/outputs date+times, input type=datetime",
   :var-type "function",
   :line 128,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([] [step]),
   :name "double-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L150",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/double-field",
   :doc "Validates/parses double numbers, input type=number",
   :var-type "function",
   :line 150,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([] [step]),
   :name "double-range-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L164",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/double-range-field",
   :doc "Validates/parses double numbers, input type=range",
   :var-type "function",
   :line 164,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "email-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L72",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/email-field",
   :doc "Validates email addresses, input type=email",
   :var-type "function",
   :line 72,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "email-lookup",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L77",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/email-lookup",
   :doc
   "Validates email addresses with an additional DNS lookup. Safer, but slower, input type=email",
   :var-type "function",
   :line 77,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "ipv4-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L88",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/ipv4-field",
   :doc "Validates IPv4 addresses",
   :var-type "function",
   :line 88,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([n]),
   :name "maxlength",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L64",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/maxlength",
   :doc "Sets the maximum length to the given number",
   :var-type "function",
   :line 64,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([n]),
   :name "minlength",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L68",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/minlength",
   :doc "Sets the minimum length to the given number",
   :var-type "function",
   :line 68,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([minn maxn]),
   :name "nbetween",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L180",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/nbetween",
   :doc "Sets the minimum and maximum numbers to given ones",
   :var-type "function",
   :line 180,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([n]),
   :name "nmax",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L175",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/nmax",
   :doc "Sets the maximum number to the given one",
   :var-type "function",
   :line 175,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([n]),
   :name "nmin",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L170",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/nmin",
   :doc "Sets the minimum number to the given one",
   :var-type "function",
   :line 170,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "non-confusing",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L52",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/non-confusing",
   :doc
   "Validates strings for confusing Unicode characters\n(the ones that look the same in different scripts)",
   :var-type "function",
   :line 52,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([lst]),
   :name "not-in",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L58",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/not-in",
   :doc
   "Given a list of forbidden values, does the right thing.\nUseful if you have routes like /about and /:username\nand you don't want someone to have an username 'about'",
   :var-type "function",
   :line 58,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([] [step]),
   :name "number-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L142",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/number-field",
   :doc "Validates/parses integer numbers, input type=number",
   :var-type "function",
   :line 142,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([re]),
   :name "pattern",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L30",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/pattern",
   :doc "Validates according to given regexp",
   :var-type "function",
   :line 30,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([] [step]),
   :name "range-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L158",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/range-field",
   :doc "Validates/parses integer numbers, input type=range",
   :var-type "function",
   :line 158,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "required",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/required",
   :doc "Validates presence",
   :var-type "function",
   :line 25,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "text-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L45",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/text-field",
   :doc "input type=text",
   :var-type "function",
   :line 45,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "textarea",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L48",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/textarea",
   :doc "textarea",
   :var-type "function",
   :line 48,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "time-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L117",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/time-field",
   :doc "Validates/parses/outputs times, input type=time",
   :var-type "function",
   :line 117,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([]),
   :name "url-field",
   :namespace "formfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj#L83",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/02a5215399bd4ffbd4febacee83b064e79c6295a/formfinger/src/formfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//formfinger.fields-api.html#formfinger.fields/url-field",
   :doc "Validates URLs, input type=url",
   :var-type "function",
   :line 83,
   :file
   "/home/ubuntu/Code/open/ringfinger/./formfinger/src/formfinger/fields.clj"}
  {:arglists ([from to subject body]),
   :name "console",
   :namespace "mailfinger.console",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/console.clj#L3",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/console.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//mailfinger.console-api.html#mailfinger.console/console",
   :doc
   "Mailer function which just prints the 'messages' to the console, for development/testing.\n**NOT a function that should be called to create a mailer, but the mailer itself**",
   :var-type "function",
   :line 3,
   :file
   "/home/ubuntu/Code/open/ringfinger/./mailfinger/src/mailfinger/console.clj"}
  {:arglists ([] [apikey] [apikey ssl]),
   :name "postmark",
   :namespace "mailfinger.postmark",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/postmark.clj#L5",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/postmark.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//mailfinger.postmark-api.html#mailfinger.postmark/postmark",
   :doc
   "Creates a Postmark mailer function. If no args are given, uses the POSTMARK_API_TEST API key w/o SSL.\nIf you provide an API key, SSL is on by default",
   :var-type "function",
   :line 5,
   :file
   "/home/ubuntu/Code/open/ringfinger/./mailfinger/src/mailfinger/postmark.clj"}
  {:arglists ([] [sendmail-path]),
   :name "sendmail",
   :namespace "mailfinger.sendmail",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/mailfinger/src/mailfinger/sendmail.clj#L8",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/2b271874c2cf6f97a7ead46853656ff6e4f8d78a/mailfinger/src/mailfinger/sendmail.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//mailfinger.sendmail-api.html#mailfinger.sendmail/sendmail",
   :doc "Creates a new Sendmail mailer function",
   :var-type "function",
   :line 8,
   :file
   "/home/ubuntu/Code/open/ringfinger/./mailfinger/src/mailfinger/sendmail.clj"}
  {:arglists ([username password]),
   :name "gmail",
   :namespace "mailfinger.smtp",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/smtp.clj#L22",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//mailfinger.smtp-api.html#mailfinger.smtp/gmail",
   :doc
   "Creates a new SMTP mailer function with given Gmail username and password.\nShort for (smtp 'smtp.gmail.com' 587 username password true)",
   :var-type "function",
   :line 22,
   :file
   "/home/ubuntu/Code/open/ringfinger/./mailfinger/src/mailfinger/smtp.clj"}
  {:arglists
   ([host port]
    [host port username password]
    [host port username password tls]),
   :name "smtp",
   :namespace "mailfinger.smtp",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/smtp.clj#L4",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/777b31e7c3c6fb25cccc1f9f74a44beae979c275/mailfinger/src/mailfinger/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//mailfinger.smtp-api.html#mailfinger.smtp/smtp",
   :doc "Creates a new SMTP mailer function with given settings",
   :var-type "function",
   :line 4,
   :file
   "/home/ubuntu/Code/open/ringfinger/./mailfinger/src/mailfinger/smtp.clj"}
  {:arglists
   ([collname
     {:keys
      [db
       pk
       owner-field
       default-dboptions
       url-prefix
       whitelist
       actions
       views
       forbidden-methods
       flash
       channels
       hooks],
      :or
      {flash nil,
       actions [],
       db nil,
       pk nil,
       whitelist nil,
       forbidden-methods [],
       owner-field nil,
       url-prefix "/",
       hooks {},
       views
       {:index default-index,
        :get default-get,
        :not-found default-not-found},
       default-dboptions {},
       channels {}}}
     &
     fields]),
   :name "resource",
   :namespace "restfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/e9137bdcbbfbd6e3d21795468d953c1344f7bdf1/restfinger/src/restfinger/core.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/e9137bdcbbfbd6e3d21795468d953c1344f7bdf1/restfinger/src/restfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//restfinger.core-api.html#restfinger.core/resource",
   :doc
   "Creates a list of two routes (/url-prefix+collname.format and\n/url-prefix+collname/pk.format) for RESTful Create/Read/Update/Delete\nof entries in collname.\nAlso, while in development environment, you can create example data using faker,\nlike this: /url-prefix+collname.format/_create_fakes?count=100 (the default count is 5).\nAccepted options:\n :db -- database (required!)\n :pk -- primary key (required!)\n :url-prefix -- a part of the URL before the collname, default is /\n :owner-field -- if you want entries to be owned by users, name of the field which should hold usernames\n :default-dboptions -- default database options (:query, :sort) for the index page\n :whitelist -- allowed extra fields (not required)\n :forbidden-methods -- a collection of methods to disallow (:index, :create, :read, :update, :delete)\n :views -- map of HTML views (:index, :get, :not-found)\n :flash -- map of flash messages (:created, :updated, :deleted, :forbidden),\n           can be either strings or callables expecting a single arg (the entry)\n :hooks -- map of hooks (:data (on both create and update), :create, :update, :view), must be callables expecting\n           the entry and returning it (with modifications you want). Hooks receive data with correct\n           types, so eg. dates/times are org.joda.time.DateTime's and you can mess with them using clj-time\n           Tip: compose hooks with comp\n :channels -- map of Lamina channels (:create, :update, :delete). Ringfinger will publish events\n              to these channels so you could, for example, push updates to clients in real time,\n              enqueue long-running jobs, index changes with a search engine, etc.\n :actions -- map of handlers for custom actions (callables accepting [req matches entry default-data])\n             on resource entries, called by visiting /url-prefix+collname/pk?_action=action",
   :var-type "function",
   :line 25,
   :file
   "/home/ubuntu/Code/open/ringfinger/./restfinger/src/restfinger/core.clj"}
  {:arglists ([] [b]),
   :name "secure-rand",
   :namespace "secfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj#L8",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//secfinger-api.html#secfinger/secure-rand",
   :doc "Secure random (SHA1PRNG) Base64-encoded string generator",
   :var-type "function",
   :line 8,
   :file
   "/home/ubuntu/Code/open/ringfinger/./secfinger/src/secfinger.clj"}
  {:arglists ([handler]),
   :name "wrap-csrf",
   :namespace "secfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj#L16",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//secfinger-api.html#secfinger/wrap-csrf",
   :doc "CSRF protection middleware for Ring",
   :var-type "function",
   :line 16,
   :file
   "/home/ubuntu/Code/open/ringfinger/./secfinger/src/secfinger.clj"}
  {:arglists ([handler]),
   :name "wrap-refcheck",
   :namespace "secfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj#L33",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//secfinger-api.html#secfinger/wrap-refcheck",
   :doc "Referer checking middleware for Ring",
   :var-type "function",
   :line 33,
   :file
   "/home/ubuntu/Code/open/ringfinger/./secfinger/src/secfinger.clj"}
  {:arglists ([handler]),
   :name "wrap-sec-headers",
   :namespace "secfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj#L47",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/cc6d390a5f7b0ea34d342db5b1176102d1dadab9/secfinger/src/secfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//secfinger-api.html#secfinger/wrap-sec-headers",
   :doc "Middleware for Ring which adds some headers for security",
   :var-type "function",
   :line 47,
   :file
   "/home/ubuntu/Code/open/ringfinger/./secfinger/src/secfinger.clj"}
  {:arglists ([] [x] [x & next]),
   :name "andf",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L8",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/andf",
   :doc "And function, just like the and macro. For use with apply",
   :var-type "function",
   :line 8,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([value & args]),
   :name "call-or-ret",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L14",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/call-or-ret",
   :doc
   "If value is a callable, calls it with args and returns the result.\nOtherwise, just returns it.",
   :var-type "macro",
   :line 14,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([req]),
   :name "from-browser?",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L101",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/from-browser?",
   :doc
   "Checks if the request comes from a web browser. Or something pretending to be a web browser, really",
   :var-type "function",
   :line 101,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([coll elem]),
   :name "haz?",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L20",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/haz?",
   :doc
   "Checks if a collection has an element,\neg. [:a :b] :a -> true, [:a :b] :c -> false",
   :var-type "macro",
   :line 20,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([req]),
   :name "is-xhr?",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L106",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/is-xhr?",
   :doc "Checks if the request is made by an XMLHttpRequest",
   :var-type "function",
   :line 106,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([a]),
   :name "keywordize",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L77",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/keywordize",
   :doc "Turns keys in map a into keywords",
   :var-type "macro",
   :line 77,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([m]),
   :name "map-to-querystring",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L57",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/map-to-querystring",
   :doc
   "Turns a map into a query sting, eg.\n{:abc 123 :def ' '} -> ?abc=123&def=+",
   :var-type "macro",
   :line 57,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([number noun]),
   :name "nice-count",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L42",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/nice-count",
   :doc
   "Displays a count of items nicely, eg.\n0 'entry' -> 'no entries', 1 'entry' -> 'one entry'\n7 'post' -> 'seven posts', 100500 'article' -> '100500 articles'",
   :var-type "macro",
   :line 42,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([& values]),
   :name "pack-to-map",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L52",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/pack-to-map",
   :doc
   "Packs values into a map, eg.\n(let [demo 1 test 2] (pack demo test)) -> {:demo 1 :test 2}",
   :var-type "macro",
   :line 52,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([m sargs]),
   :name "sort-maps",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L89",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/sort-maps",
   :doc
   "Sorts a sequence of maps using a map of sort args that maps keys to -1 for desc and 1 for asc order",
   :var-type "function",
   :line 89,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([n s]),
   :name "str-drop",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L30",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/str-drop",
   :doc
   "Drops first n characters from s.  Returns an empty string if n is\ngreater than the length of s.",
   :var-type "function",
   :line 30,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([substring s]),
   :name "substring?",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/substring?",
   :doc "Checks if s contains the substring.",
   :var-type "function",
   :line 25,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([s]),
   :name "typeify",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L66",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/typeify",
   :doc
   "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, also recognizes integers and doubles ",
   :var-type "function",
   :line 66,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([a]),
   :name "typeize",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L81",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/typeize",
   :doc "Maps typeify to values of map a",
   :var-type "macro",
   :line 81,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"}
  {:arglists ([n]),
   :name "zeroify",
   :namespace "toolfinger",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj#L36",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/4c0d9472d5bfa5d19035af28210e64828f6d3af3/toolfinger/src/toolfinger.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//toolfinger-api.html#toolfinger/zeroify",
   :doc
   "Converts an integer to a string, adding a leading zero if it's < 10,\ne.g. 1 -> '01', but 10 -> '10'\nUsed for dates and times",
   :var-type "macro",
   :line 36,
   :file
   "/home/ubuntu/Code/open/ringfinger/./toolfinger/src/toolfinger.clj"})}
