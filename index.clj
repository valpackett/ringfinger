{:namespaces
 ({:source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/auth-api.html",
   :name "ringfinger.auth",
   :doc
   "Low-level authorization API (creating users, getting users after checking) and the auth middleware."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/auth_routes.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/auth-routes-api.html",
   :name "ringfinger.auth-routes",
   :doc
   "Authorization routes -- magical registration (if you really want, even with\ne-mail confirmation) and logging in/out."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/core-api.html",
   :name "ringfinger.core",
   :doc
   "Ringfinger's core: All You Need Is defapp! And if-env.\nMagic starts here."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/db.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/db-api.html",
   :name "ringfinger.db",
   :doc
   "Document-oriented database abstraction used by ringfinger.resource."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/email.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/email-api.html",
   :name "ringfinger.email",
   :doc "Email sending abstraction used by ringfinger.auth-routes."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/field-helpers-api.html",
   :name "ringfinger.field-helpers",
   :doc
   "The only useful thing for users here is form-fields.\nEverything else is kinda internal, but feel free to use this if you\ndon't use ringfinger.resource and write all the boilerplate by hand."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/fields-api.html",
   :name "ringfinger.fields",
   :doc
   "All functions in this module make fields for you to use\nwith ringfinger.resource.\nThey're defined as functions even when they need no\ncustomization just for consistency."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/output.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/output-api.html",
   :name "ringfinger.output",
   :doc "The output system used by ringfinger.resource."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/resource.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/resource-api.html",
   :name "ringfinger.resource",
   :doc
   "This module saves your time by writing all the Create/Read/Update/Delete\nboilerplate for you. Flash messages, validation, inserting example data,\ncustomization via hooks and channels -- you name it, this module does it."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/security.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/security-api.html",
   :name "ringfinger.security",
   :doc
   "Some security-related Ring middleware used by ringfinger.core."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/session.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/session-api.html",
   :name "ringfinger.session",
   :doc
   "This is used automatically when you provide a :session-db option to ringfinger.core/(def)app."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/timesavers/hooks.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/timesavers.hooks-api.html",
   :name "ringfinger.timesavers.hooks",
   :doc
   "Ready-to-use hooks for use with ringfinger.resource. Save even more time!"}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/util-api.html",
   :name "ringfinger.util",
   :doc "Various functions and macros used by Ringfinger."}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/db/inmem.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/db.inmem-api.html",
   :name "ringfinger.db.inmem",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/db/mongodb.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/db.mongodb-api.html",
   :name "ringfinger.db.mongodb",
   :doc
   "MongoDB support. Don't forget to add Congomongo to your deps!"}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/console.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/email.console-api.html",
   :name "ringfinger.email.console",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/email/postmark.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/email.postmark-api.html",
   :name "ringfinger.email.postmark",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/email.smtp-api.html",
   :name "ringfinger.email.smtp",
   :doc nil}),
 :vars
 ({:arglists ([db coll username password fixed-salt-part]),
   :name "get-user",
   :namespace "ringfinger.auth",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj#L9",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-api.html#ringfinger.auth/get-user",
   :doc
   "Returns a user from coll in db with given username and password if the password is valid",
   :var-type "function",
   :line 9,
   :file "ringfinger/auth.clj"}
  {:arglists ([db coll user password fixed-salt-part]),
   :name "make-user",
   :namespace "ringfinger.auth",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj#L17",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-api.html#ringfinger.auth/make-user",
   :doc
   "Creates a user in coll in db with given fields (username and whatever you want) and password",
   :var-type "function",
   :line 17,
   :file "ringfinger/auth.clj"}
  {:arglists ([handler] [handler options]),
   :name "wrap-auth",
   :namespace "ringfinger.auth",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj#L28",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/auth.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-api.html#ringfinger.auth/wrap-auth",
   :doc
   "Ring middleware that adds :user if there's a user logged in. Supports session/form-based auth and HTTP Basic auth",
   :var-type "function",
   :line 28,
   :file "ringfinger/auth.clj"}
  {:arglists ([options]),
   :name "auth-routes",
   :namespace "ringfinger.auth-routes",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/auth_routes.clj#L19",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/auth_routes.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-routes-api.html#ringfinger.auth-routes/auth-routes",
   :doc
   "Creates auth routes with given options:\n:db, :coll -- database and collection\n:views -- map of views (:login, :signup and :confirm)\n:flash -- map of flash messages (:login-success, :login-invalid, :signup-success, :logout, :confirm-success and :confirm-fail)\n:fixed-salt -- fixed part of the salt, must be the same as you use with app. NEVER change this in production!!\n:url-base -- the starting part of auth URLs, the default is /auth/\n:redir-to -- where to redirect after a successful login/signup if there's no referer, the default is /\n:redir-param -- query string parameter for keeping the redirect url, the default is redirect, you generally don't need to care about this\n:confirm -- if you want email confirmation, map of parameters :mailer, :from, :email-field (default is :username), :subject, :mail-template\n:fields -- list of validations, defaults are: requiring username and at least 6 characters password",
   :var-type "function",
   :line 19,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/auth_routes.clj"}
  {:arglists ([options & routes]),
   :name "app",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L64",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/app",
   :doc
   "Creates a Ring handler with given options and routes, automatically wrapped with\nparams, session, flash, auth, head, jsonp, length and some security middleware\n(+ stacktrace and file in development env)\nAccepted options:\n :auth-db and :auth-coll -- database and collection for auth middleware, must be the same as the ones you use with auth-routes, the default collection is :ringfinger_auth\n :fixed-salt -- the fixed part of password hashing salt, must be the same as the one you use with auth-routes. NEVER change this in production!!\n :session-db -- database for session middleware OR\n :session-store -- SessionStore for session middleware, eg. for using the Redis store\n :static-dir -- directory with static files for serving them in development\n :callback-param -- parameter for JSONP callbacks, default is 'callback'\n :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, enabled by default",
   :var-type "function",
   :line 64,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([nname options & routes]),
   :name "defapp",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L98",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/defapp",
   :doc "Short for (def nname (app options & routes))",
   :var-type "macro",
   :line 98,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([env yep nope]),
   :name "if-env",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L9",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/if-env",
   :doc "Checks if the current RING_ENV == env",
   :var-type "macro",
   :line 9,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([url handlers]),
   :name "route",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L52",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/route",
   :doc
   "Creates a route accepted by the app function from a URL in Clout (Sinatra-like) format and a map of handlers\neg. {:get (fn [req matches] {:status 200 :body nil})}",
   :var-type "function",
   :line 52,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([handler]),
   :name "wrap-head",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L19",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/wrap-head",
   :doc "Ring middleware for handling HEAD requests properly",
   :var-type "function",
   :line 19,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([handler callback-param]),
   :name "wrap-jsonp",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L27",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/wrap-jsonp",
   :doc "Ring middleware for handling JSONP requests",
   :var-type "function",
   :line 27,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([handler]),
   :name "wrap-length",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj#L12",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/wrap-length",
   :doc "Ring middleware for adding Content-Length",
   :var-type "function",
   :line 12,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L73",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/data-post-hook-from-fields",
   :namespace "ringfinger.field-helpers",
   :line 73,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a data post-hook from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "data-post-hook-from-fields"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L67",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/data-pre-hook-from-fields",
   :namespace "ringfinger.field-helpers",
   :line 67,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a data pre-hook from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "data-pre-hook-from-fields"}
  {:arglists ([fields]),
   :name "defaults-from-fields",
   :namespace "ringfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L37",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/defaults-from-fields",
   :doc
   "Makes a list of defaults from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :var-type "function",
   :line 37,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L44",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/fakers-from-fields",
   :namespace "ringfinger.field-helpers",
   :line 44,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a list of fakers from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "fakers-from-fields"}
  {:arglists ([fields-html data errors wrap-html err-html style]),
   :name "form-fields",
   :namespace "ringfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L88",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/form-fields",
   :doc
   "HTML templating helper for rendering forms. Allowed styles are :label and :placeholder",
   :var-type "macro",
   :line 88,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L61",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/get-hook-from-fields",
   :namespace "ringfinger.field-helpers",
   :line 61,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj",
   :var-type "var",
   :doc
   "Makes a get hook from a list of fields. You usually don't need to use it manually.\nIt's used by ringfinger.resource automatically",
   :name "get-hook-from-fields"}
  {:arglists ([fields]),
   :name "html-from-fields",
   :namespace "ringfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L16",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/html-from-fields",
   :doc
   "Makes a map of field names - html attributes from a list of fields, eg.\n([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']\n [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])\nbecomes ([:name {:required 'required' :maxlength 10}])",
   :var-type "function",
   :line 16,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj"}
  {:arglists ([fields]),
   :name "required-fields-of",
   :namespace "ringfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L79",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/required-fields-of",
   :doc
   "Returns a list of required fields' names from a list of fields, eg.\n([:name (required) 'wtf'] [:date (date) 'lolwut'])\nbecomes\n(:name)",
   :var-type "function",
   :line 79,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj"}
  {:arglists ([fields]),
   :name "validations-from-fields",
   :namespace "ringfinger.field-helpers",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj#L26",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/field_helpers.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//field-helpers-api.html#ringfinger.field-helpers/validations-from-fields",
   :doc
   "Makes a list of validations from a list of fields, eg.\n([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']\n [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])\nbecomes ([:name (required) 'y u no say ur name']\n         [:name (my-check) 'too long']) ; the valip format",
   :var-type "function",
   :line 26,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/field_helpers.clj"}
  {:arglists ([]),
   :name "alphanumeric",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L40",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/alphanumeric",
   :doc "Validates alphanumeric strings",
   :var-type "function",
   :line 40,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "checkbox",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L32",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/checkbox",
   :doc "A boolean field",
   :var-type "function",
   :line 32,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "color",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L83",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/color",
   :doc "Validates hexadecimal color codes",
   :var-type "function",
   :line 83,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "date",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L88",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/date",
   :doc "Validates/parses/outputs dates",
   :var-type "function",
   :line 88,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "email",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L58",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/email",
   :doc "Validates email addresses",
   :var-type "function",
   :line 58,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "email-with-lookup",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L63",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/email-with-lookup",
   :doc
   "Validates email addresses with an additional DNS lookup. Safer, but slower",
   :var-type "function",
   :line 63,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "ipv4",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L74",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/ipv4",
   :doc "Validates IPv4 addresses",
   :var-type "function",
   :line 74,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "maxlength",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L50",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/maxlength",
   :doc "Sets the maximum length to the given number",
   :var-type "function",
   :line 50,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "minlength",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L54",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/minlength",
   :doc "Sets the minimum length to the given number",
   :var-type "function",
   :line 54,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([minn maxn]),
   :name "nbetween",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L134",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/nbetween",
   :doc "Sets the minimum and maximum numbers to given ones",
   :var-type "function",
   :line 134,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "nmax",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L129",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/nmax",
   :doc "Sets the maximum number to the given one",
   :var-type "function",
   :line 129,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "nmin",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L124",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/nmin",
   :doc "Sets the minimum number to the given one",
   :var-type "function",
   :line 124,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "number",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L118",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/number",
   :doc "Validates integer numbers",
   :var-type "function",
   :line 118,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([re]),
   :name "pattern",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L28",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/pattern",
   :doc "Validates according to given regexp",
   :var-type "function",
   :line 28,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "required",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L23",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/required",
   :doc "Validates presence",
   :var-type "function",
   :line 23,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "text",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L43",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/text",
   :doc "input type=text",
   :var-type "function",
   :line 43,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "textarea",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L46",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/textarea",
   :doc "textarea",
   :var-type "function",
   :line 46,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "time-field",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L106",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/time-field",
   :doc "Validates/parses/outputs times",
   :var-type "function",
   :line 106,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "url",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj#L69",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/url",
   :doc "Validates URLs",
   :var-type "function",
   :line 69,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([collname options & fields]),
   :name "resource",
   :namespace "ringfinger.resource",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/resource.clj#L27",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/resource.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//resource-api.html#ringfinger.resource/resource",
   :doc
   "Creates a list of two routes (/url-prefix+collname and /url-prefix+collname/pk) for\nRESTful Create/Read/Update/Delete of entries in collname.\nAlso, while in development environment, you can create example data using faker,\nlike this: /url-prefix+collname/_insert_fakes?count=100 (the default count is 5).\nAccepted options:\n :db -- database (required!)\n :pk -- primary key (required!)\n :url-prefix -- a part of the URL before the collname, default is /\n :owner-field -- if you want entries to be owned by users, name of the field which should hold usernames\n :default-dboptions -- default database options (:query, :sort) for the index page\n :whitelist -- allowed extra fields (not required)\n :forbidden-methods -- a collection of methods to disallow (:index, :create, :read, :update, :delete)\n :views -- map of HTML views (:index, :get, :not-found)\n :flash -- map of flash messages (:created, :updated, :deleted, :forbidden),\n           can be either strings or callables expecting a single arg (the entry)\n :hooks -- map of hooks (:data (on both create and update), :create, :update, :view), must be callables expecting\n           the entry and returning it (with modifications you want). Hooks receive data with correct\n           types, so eg. dates/times are org.joda.time.DateTime's and you can mess with them using clj-time\n           Tip: compose hooks with ->\n :channels -- map of Lamina channels (:create, :update, :delete). Ringfinger will publish events\n              to these channels so you could, for example, push updates to clients in real time,\n              enqueue long-running jobs, index changes with a search engine, etc.",
   :var-type "function",
   :line 27,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/resource.clj"}
  {:arglists ([handler]),
   :name "wrap-csrf",
   :namespace "ringfinger.security",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/security.clj#L6",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/security.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//security-api.html#ringfinger.security/wrap-csrf",
   :doc "CSRF protection middleware for Ring",
   :var-type "function",
   :line 6,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/security.clj"}
  {:arglists ([handler]),
   :name "wrap-refcheck",
   :namespace "ringfinger.security",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/security.clj#L20",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/security.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//security-api.html#ringfinger.security/wrap-refcheck",
   :doc "Referer checking middleware for Ring",
   :var-type "function",
   :line 20,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/security.clj"}
  {:arglists ([db] [db coll]),
   :name "db-store",
   :namespace "ringfinger.session",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/session.clj#L20",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/session.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//session-api.html#ringfinger.session/db-store",
   :doc
   "Creates a new SessionStore object (for ring.middleware.session) with given db and coll.\nThe default coll is :ringfinger_sessions",
   :var-type "function",
   :line 20,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/session.clj"}
  {:arglists ([field] [field output-field]),
   :name "make-slug-for",
   :namespace "ringfinger.timesavers.hooks",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/timesavers/hooks.clj#L7",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/feb78f66928e124a16d37fa3e6dbc35764d1fee8/src/ringfinger/timesavers/hooks.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//timesavers.hooks-api.html#ringfinger.timesavers.hooks/make-slug-for",
   :doc
   "Returns a hook which makes a slug (URL-friendly name, eg. My Article -> my-article)\nfor a given field. Default output-field is field + '_slug'.\nDon't forget that if you use a custom output-field, you need to whitelist it.\nNever returns empty values",
   :var-type "function",
   :line 7,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/timesavers/hooks.clj"}
  {:arglists ([] [x] [x & next]),
   :name "andf",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L9",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/andf",
   :doc "And function, just like the and macro. For use with apply",
   :var-type "function",
   :line 9,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([value & args]),
   :name "call-or-ret",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L15",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/call-or-ret",
   :doc
   "If value is a callable, calls it with args and returns the result.\nOtherwise, just returns it.",
   :var-type "macro",
   :line 15,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([req]),
   :name "from-browser?",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L75",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/from-browser?",
   :doc
   "Checks if the request comes from a web browser. Or something pretending to be a web browser, really",
   :var-type "function",
   :line 75,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([coll elem]),
   :name "haz?",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L21",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/haz?",
   :doc
   "Checks if a collection has an element,\neg. [:a :b] :a -> true, [:a :b] :c -> false",
   :var-type "macro",
   :line 21,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([req]),
   :name "is-xhr?",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L80",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/is-xhr?",
   :doc "Checks if the request is made by an XMLHttpRequest",
   :var-type "function",
   :line 80,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([a]),
   :name "keywordize",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L53",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/keywordize",
   :doc "Turns keys in map a into keywords",
   :var-type "macro",
   :line 53,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([number noun]),
   :name "nice-count",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L32",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/nice-count",
   :doc
   "Displays a count of items nicely, eg.\n0 'entry' -> 'no entries', 1 'entry' -> 'one entry'\n7 'post' -> 'seven posts', 100500 'article' -> '100500 articles'",
   :var-type "macro",
   :line 32,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([m sargs]),
   :name "sort-maps",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L63",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/sort-maps",
   :doc
   "Sorts a sequence of maps using a map of sort args that maps keys to -1 for desc and 1 for asc order",
   :var-type "function",
   :line 63,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([s]),
   :name "typeify",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L42",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/typeify",
   :doc
   "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, also recognizes integers and doubles ",
   :var-type "function",
   :line 42,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([a]),
   :name "typeize",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L57",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/typeize",
   :doc "Maps typeify to values of map a",
   :var-type "macro",
   :line 57,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([n]),
   :name "zeroify",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj#L26",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/ca7f4c69d865b46a4149cbdc525c3ca61b5cdc79/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/zeroify",
   :doc
   "Converts an integer to a string, adding a leading zero if it's < 10,\ne.g. 1 -> '01', but 10 -> '10'\nUsed for dates and times",
   :var-type "macro",
   :line 26,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/db/inmem.clj",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/db/inmem.clj#L6",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//db-api.html#ringfinger.db.inmem/inmem",
   :namespace "ringfinger.db.inmem",
   :line 6,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/db/inmem.clj",
   :var-type "var",
   :doc
   "In-memory data storage FOR TESTING USE ONLY.\nOr for storing temporary data like sessions.",
   :name "inmem"}
  {:arglists ([]),
   :name "reset-inmem-db",
   :namespace "ringfinger.db.inmem",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/db/inmem.clj#L36",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/da5ac7557eb3fda1870cdc5c6b6e08d0778f6e4b/src/ringfinger/db/inmem.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//db-api.html#ringfinger.db.inmem/reset-inmem-db",
   :doc "Erase EVERYTHING from the inmem database",
   :var-type "function",
   :line 36,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/db/inmem.clj"}
  {:arglists ([db] [db instances] [db username password instances]),
   :name "mongodb",
   :namespace "ringfinger.db.mongodb",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/db/mongodb.clj#L23",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/3ad070931e98ccff408003bf18181c3fb0db69c7/src/ringfinger/db/mongodb.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//db-api.html#ringfinger.db.mongodb/mongodb",
   :doc
   "Creates a MongoDB data storage object using given db and a list of instances.\nEach instance is a map containing :host and/or :port",
   :var-type "function",
   :line 23,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/db/mongodb.clj"}
  {:arglists ([from to subject body]),
   :name "console",
   :namespace "ringfinger.email.console",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/console.clj#L3",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/console.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.console/console",
   :doc
   "Mailer function which just prints the 'messages' to the console, for development/testing.\n**NOT a function that should be called to create a mailer, but the mailer itself**",
   :var-type "function",
   :line 3,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/console.clj"}
  {:arglists ([] [apikey] [apikey ssl]),
   :name "postmark",
   :namespace "ringfinger.email.postmark",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/email/postmark.clj#L5",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/86b44e35da168f28a64a90a6efad69a2a7c3470e/src/ringfinger/email/postmark.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.postmark/postmark",
   :doc
   "Creates a Postmark mailer function. If no args are given, uses the POSTMARK_API_TEST API key w/o SSL.\nIf you provide an API key, SSL is on by default",
   :var-type "function",
   :line 5,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/postmark.clj"}
  {:arglists ([username password]),
   :name "gmail",
   :namespace "ringfinger.email.smtp",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/smtp.clj#L22",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.smtp/gmail",
   :doc
   "Creates a new SMTP mailer function with given Gmail username and password.\nShort for (smtp 'smtp.gmail.com' 587 username password true)",
   :var-type "function",
   :line 22,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/smtp.clj"}
  {:arglists
   ([host port]
    [host port username password]
    [host port username password tls]),
   :name "smtp",
   :namespace "ringfinger.email.smtp",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/smtp.clj#L4",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/04a2761c3417c783d02aad9b6b6a8c508971977d/src/ringfinger/email/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.smtp/smtp",
   :doc "Creates a new SMTP mailer function with given settings",
   :var-type "function",
   :line 4,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/smtp.clj"})}
