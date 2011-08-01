{:namespaces
 ({:source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/auth-api.html",
   :name "ringfinger.auth",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth_routes.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/auth-routes-api.html",
   :name "ringfinger.auth-routes",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/core-api.html",
   :name "ringfinger.core",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/9261df9709f7cefe5287afffb79f5a9e176204e2/src/ringfinger/db.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/db-api.html",
   :name "ringfinger.db",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/1e29884cfe4f99e65bf3f6cb60717a085750e1f8/src/ringfinger/email.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/email-api.html",
   :name "ringfinger.email",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/fields-api.html",
   :name "ringfinger.fields",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/09cbc99dbd45bd3f16067b7be98ff27d08526b61/src/ringfinger/output.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/output-api.html",
   :name "ringfinger.output",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/resource-api.html",
   :name "ringfinger.resource",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/93b478980a820b24c1e529f532a86045633eba30/src/ringfinger/security.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/security-api.html",
   :name "ringfinger.security",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/9261df9709f7cefe5287afffb79f5a9e176204e2/src/ringfinger/session.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/session-api.html",
   :name "ringfinger.session",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url "http://myfreeweb.github.com/ringfinger/util-api.html",
   :name "ringfinger.util",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/fecfe1b95467d4e603819b0c85ad42b2ef1b3605/src/ringfinger/db/inmem.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/db.inmem-api.html",
   :name "ringfinger.db.inmem",
   :doc "In-memory data storage FOR TESTING USE ONLY"}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/fecfe1b95467d4e603819b0c85ad42b2ef1b3605/src/ringfinger/db/mongodb.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/db.mongodb-api.html",
   :name "ringfinger.db.mongodb",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/adb502fb8ad365118309e4002f87db3177e603e9/src/ringfinger/email/postmark.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/email.postmark-api.html",
   :name "ringfinger.email.postmark",
   :doc nil}
  {:source-url
   "https://github.com/myfreeweb/ringfinger/blob/b7ce9195f6b84c95f2c41965d25be98b244cfb97/src/ringfinger/email/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger/email.smtp-api.html",
   :name "ringfinger.email.smtp",
   :doc nil}),
 :vars
 ({:arglists ([db coll username password fixed-salt-part]),
   :name "get-user",
   :namespace "ringfinger.auth",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj#L7",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-api.html#ringfinger.auth/get-user",
   :doc
   "Returns a user from coll in db with given username and password if the password is valid",
   :var-type "function",
   :line 7,
   :file "ringfinger/auth.clj"}
  {:arglists ([db coll user password fixed-salt-part]),
   :name "make-user",
   :namespace "ringfinger.auth",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj#L15",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-api.html#ringfinger.auth/make-user",
   :doc
   "Creates a user in coll in db with given fields (username and whatever you want) and password",
   :var-type "function",
   :line 15,
   :file "ringfinger/auth.clj"}
  {:arglists ([handler] [handler options]),
   :name "wrap-auth",
   :namespace "ringfinger.auth",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj#L24",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-api.html#ringfinger.auth/wrap-auth",
   :doc
   "Ring middleware that adds :user if there's a user logged in. Supports session/form-based auth and HTTP Basic auth",
   :var-type "function",
   :line 24,
   :file "ringfinger/auth.clj"}
  {:arglists ([options]),
   :name "auth-routes",
   :namespace "ringfinger.auth-routes",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth_routes.clj#L50",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9e36f5ccbf9f03da1d4ce40d03140412960b9bd8/src/ringfinger/auth_routes.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//auth-routes-api.html#ringfinger.auth-routes/auth-routes",
   :doc
   "Creates auth routes with given options:\n:db, :coll -- database and collection\n:views -- map of views (:login, :signup and :confirm)\n:flash -- map of flash messages (:login-success, :login-invalid, :signup-success, :logout, :confirm-success and :confirm-fail)\n:fixed-salt -- fixed part of the salt, must be the same as you use with app. NEVER change this in production!!\n:url-base -- the starting part of auth URLs, the default is /auth/\n:redir-to -- where to redirect after a successful login/signup if there's no referer, the default is /\n:redir-param -- query string parameter for keeping the redirect url, the default is redirect, you generally don't need to care about this\n:confirm -- if you want email confirmation, map of parameters :mailer, :from, :email-field (default is :username), :subject, :mail-template\n:fields -- list of validations, defaults is requiring username and at least 6 characters password",
   :var-type "function",
   :line 50,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/auth_routes.clj"}
  {:arglists ([options & routes]),
   :name "app",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj#L44",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/app",
   :doc
   "Creates a Ring handler with given options and routes, automatically wrapped with\nparams, session, flash, auth and some security middleware (+ stacktrace and file in development env)\nAccepted options:\n :auth-db and :auth-coll -- database and collection for auth middleware, must be the same as the ones you use with auth-routes, the default collection is :ringfinger_auth\n :fixed-salt -- the fixed part of password hashing salt, must be the same as the one you use with auth-routes. NEVER change this in production!!\n :session-db -- database for session middleware\n :static-dir -- directory with static files for serving them in development\n :memoize-routing -- whether to memoize (cache) route matching, gives better performance by using more memory, enabled by default",
   :var-type "function",
   :line 44,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([nname options & routes]),
   :name "defapp",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj#L72",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/defapp",
   :doc "Short for (def nname (app options routes*))",
   :var-type "macro",
   :line 72,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([get-handler]),
   :name "head-handler",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj#L21",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/head-handler",
   :doc
   "Creates a handler for HEAD requests from a GET request handler",
   :var-type "macro",
   :line 21,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([env yep nope]),
   :name "if-env",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj#L6",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/if-env",
   :doc "Checks if the current RING_ENV == env",
   :var-type "macro",
   :line 6,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([url handlers]),
   :name "route",
   :namespace "ringfinger.core",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj#L33",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/f0cefefcec40ebb313033eeac4f194f4f4712060/src/ringfinger/core.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//core-api.html#ringfinger.core/route",
   :doc
   "Creates a route accepted by the app function from a URL in Clout (Sinatra-like) format and a map of handlers\neg. {:get (fn [req matches] {:status 200 :body nil})}",
   :var-type "function",
   :line 33,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/core.clj"}
  {:arglists ([]),
   :name "alphanumeric",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L14",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/alphanumeric",
   :doc "Validates alphanumeric strings",
   :var-type "function",
   :line 14,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "color",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L45",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/color",
   :doc "Validates hexadecimal color codes",
   :var-type "function",
   :line 45,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "date",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L49",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/date",
   :doc "Validates dates",
   :var-type "function",
   :line 49,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "email",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/email",
   :doc "Validates email addresses",
   :var-type "function",
   :line 25,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "email-with-lookup",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L29",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/email-with-lookup",
   :doc
   "Validates email addresses with an additional DNS lookup. Safer, but slower",
   :var-type "function",
   :line 29,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([fields-html data errors wrap-html err-html style]),
   :name "form-fields",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L89",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/form-fields",
   :doc
   "HTML templating helper for rendering forms. Allowed styles are :label and :placeholder",
   :var-type "macro",
   :line 89,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([fields]),
   :name "html-from-fields",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L71",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/html-from-fields",
   :doc
   "Makes a map of field names - html attributes from a list of fields, eg.\n([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']\n [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])\nbecomes ([:name {:required 'required' :maxlength 10}])",
   :var-type "macro",
   :line 71,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "ipv4",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L38",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/ipv4",
   :doc "Validates IPv4 addresses",
   :var-type "function",
   :line 38,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "maxlength",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L17",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/maxlength",
   :doc "Sets the maximum length to the given number",
   :var-type "function",
   :line 17,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "minlength",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L21",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/minlength",
   :doc "Sets the minimum length to the given number",
   :var-type "function",
   :line 21,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([minn maxn]),
   :name "nbetween",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L65",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/nbetween",
   :doc "Sets the minimum and maximum numbers to given ones",
   :var-type "function",
   :line 65,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "nmax",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L61",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/nmax",
   :doc "Sets the maximum number to the given one",
   :var-type "function",
   :line 61,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([n]),
   :name "nmin",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L57",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/nmin",
   :doc "Sets the minimum number to the given one",
   :var-type "function",
   :line 57,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "number",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L53",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/number",
   :doc "Validates integer numbers",
   :var-type "function",
   :line 53,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([re]),
   :name "pattern",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L10",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/pattern",
   :doc "Validates according to given regexp",
   :var-type "function",
   :line 10,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "required",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L6",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/required",
   :doc "Validates presence",
   :var-type "function",
   :line 6,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([]),
   :name "url",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L34",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/url",
   :doc "Validates URLs",
   :var-type "function",
   :line 34,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([fields]),
   :name "validations-from-fields",
   :namespace "ringfinger.fields",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj#L80",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/8a366e227819cff5600e4c9fc194e7fa4e7f02eb/src/ringfinger/fields.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//fields-api.html#ringfinger.fields/validations-from-fields",
   :doc
   "Makes a list of validations from a list of fields, eg.\n([:name {:pred (required) :html {:required 'required'}} 'y u no say ur name']\n [:name {:pred (my-check) :html {:maxlength 10}} 'too long'])\nbecomes ([:name (required) 'y u no say ur name']\n         [:name (my-check) 'too long']) ; the valip format",
   :var-type "macro",
   :line 80,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/fields.clj"}
  {:arglists ([flash inst]),
   :name "call-flash",
   :namespace "ringfinger.resource",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj#L41",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//resource-api.html#ringfinger.resource/call-flash",
   :doc
   "If a flash message is a string, returns it. If it's a callable, calls it with inst and returns the result",
   :var-type "macro",
   :line 41,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/resource.clj"}
  {:arglists ([qp] [q v]),
   :name "params-to-dboptions",
   :namespace "ringfinger.resource",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj#L34",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//resource-api.html#ringfinger.resource/params-to-dboptions",
   :doc
   "Turns ring query-params into db options\neg. {'query_field_ne' 3, 'sort_field' -1}\nbecomes {:query {:field {:$ne 3}}, :sort {:field -1}}",
   :var-type "function",
   :line 34,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/resource.clj"}
  {:arglists ([collname options & fields]),
   :name "resource",
   :namespace "ringfinger.resource",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj#L48",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/resource.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//resource-api.html#ringfinger.resource/resource",
   :doc
   "Creates a list of two routes (/url-prefix+collname and /url-prefix+collname/pk) for\nRESTful Create/Read/Update/Delete of entries in collname\nAccepted options:\n :db -- database (required!)\n :pk -- primary key (required!)\n :url-prefix -- a part of the URL before the collname, default is /\n :owner-field -- if you want entries to be owned by users, name of the field which should hold usernames\n :default-dboptions -- default database options (:query, :sort) for index pages\n :whitelist -- allowed extra fields (not required, not validated, automatically created, etc.)\n :views -- map of HTML views :index, :get and :not-found\n :flash -- map of flash messages :created, :updated, :deleted and :forbidden, can be either strings or callables expecting a single arg (the entry)\n :hooks -- map of hooks :data (called on both create and update), :create and :update, must be callables expecting the entry and returning it (with modifications you want)\n :channels -- map of Lamina channels :create, :update and :delete for subscribing to these events",
   :var-type "function",
   :line 48,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/resource.clj"}
  {:arglists ([handler]),
   :name "wrap-csrf",
   :namespace "ringfinger.security",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/93b478980a820b24c1e529f532a86045633eba30/src/ringfinger/security.clj#L5",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/93b478980a820b24c1e529f532a86045633eba30/src/ringfinger/security.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//security-api.html#ringfinger.security/wrap-csrf",
   :doc "CSRF protection middleware for Ring",
   :var-type "function",
   :line 5,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/security.clj"}
  {:arglists ([handler]),
   :name "wrap-refcheck",
   :namespace "ringfinger.security",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/93b478980a820b24c1e529f532a86045633eba30/src/ringfinger/security.clj#L19",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/93b478980a820b24c1e529f532a86045633eba30/src/ringfinger/security.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//security-api.html#ringfinger.security/wrap-refcheck",
   :doc "Referer checking middleware for Ring",
   :var-type "function",
   :line 19,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/security.clj"}
  {:arglists ([db] [db coll]),
   :name "db-store",
   :namespace "ringfinger.session",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/9261df9709f7cefe5287afffb79f5a9e176204e2/src/ringfinger/session.clj#L19",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/9261df9709f7cefe5287afffb79f5a9e176204e2/src/ringfinger/session.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//session-api.html#ringfinger.session/db-store",
   :doc
   "Creates a new SessionStore object (for ring.middleware.session) with given db and coll.\nThe default coll is :ringfinger_sessions",
   :var-type "function",
   :line 19,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/session.clj"}
  {:arglists ([] [x] [x & next]),
   :name "andf",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj#L4",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/andf",
   :doc "And function, just like and macro. For use with apply",
   :var-type "function",
   :line 4,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([req]),
   :name "from-browser?",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj#L40",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/from-browser?",
   :doc
   "Returns true if the request comes from a web browser. Or something pretending to be a web browser, really",
   :var-type "function",
   :line 40,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([a]),
   :name "keywordize",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj#L19",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/keywordize",
   :doc "Turns keys in map a into keywords",
   :var-type "macro",
   :line 19,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([m sargs]),
   :name "sort-maps",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj#L29",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/sort-maps",
   :doc
   "Sorts a sequence of maps using a map of sort args that maps keys to -1 for desc and 1 for asc order",
   :var-type "function",
   :line 29,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([s]),
   :name "typeify",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj#L10",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/typeify",
   :doc
   "Normalizes the type of s. If it's a string 'true', returns true, if 'false' -- false, also recognizes integers and doubles ",
   :var-type "function",
   :line 10,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([a]),
   :name "typeize",
   :namespace "ringfinger.util",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj#L23",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/626b33d7c5e7923867330029944877a03c8d35e7/src/ringfinger/util.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//util-api.html#ringfinger.util/typeize",
   :doc "Maps typeify to values of map a",
   :var-type "macro",
   :line 23,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/util.clj"}
  {:arglists ([db] [db instances] [db username password instances]),
   :name "mongodb",
   :namespace "ringfinger.db.mongodb",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/fecfe1b95467d4e603819b0c85ad42b2ef1b3605/src/ringfinger/db/mongodb.clj#L20",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/fecfe1b95467d4e603819b0c85ad42b2ef1b3605/src/ringfinger/db/mongodb.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//db-api.html#ringfinger.db.mongodb/mongodb",
   :doc
   "Creates a MongoDB data storage object using given db and a list of instances.\nEach instance is a map containing :host and/or :port",
   :var-type "function",
   :line 20,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/db/mongodb.clj"}
  {:arglists ([] [apikey] [apikey ssl]),
   :name "postmark",
   :namespace "ringfinger.email.postmark",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/adb502fb8ad365118309e4002f87db3177e603e9/src/ringfinger/email/postmark.clj#L17",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/adb502fb8ad365118309e4002f87db3177e603e9/src/ringfinger/email/postmark.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.postmark/postmark",
   :doc
   "Creates a Postmark mailer object. If no args are given, uses the POSTMARK_API_TEST API key w/o SSL",
   :var-type "function",
   :line 17,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/postmark.clj"}
  {:arglists ([username password]),
   :name "gmail",
   :namespace "ringfinger.email.smtp",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/b7ce9195f6b84c95f2c41965d25be98b244cfb97/src/ringfinger/email/smtp.clj#L25",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/b7ce9195f6b84c95f2c41965d25be98b244cfb97/src/ringfinger/email/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.smtp/gmail",
   :doc
   "Creates a new SMTP mailer object with given Gmail username and password.\nShortcut for (smtp 'smtp.gmail.com' 587 username password true)",
   :var-type "function",
   :line 25,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/smtp.clj"}
  {:arglists
   ([host port]
    [host port username password]
    [host port username password tls]),
   :name "smtp",
   :namespace "ringfinger.email.smtp",
   :source-url
   "https://github.com/myfreeweb/ringfinger/blob/b7ce9195f6b84c95f2c41965d25be98b244cfb97/src/ringfinger/email/smtp.clj#L19",
   :raw-source-url
   "https://github.com/myfreeweb/ringfinger/raw/b7ce9195f6b84c95f2c41965d25be98b244cfb97/src/ringfinger/email/smtp.clj",
   :wiki-url
   "http://myfreeweb.github.com/ringfinger//email-api.html#ringfinger.email.smtp/smtp",
   :doc "Creates a new SMTP mailer object with given settings",
   :var-type "function",
   :line 19,
   :file
   "/Users/myfreeweb/Code/open/ringfinger/src/ringfinger/email/smtp.clj"})}
