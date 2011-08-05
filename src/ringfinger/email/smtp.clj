(ns ringfinger.email.smtp
  (:import (org.apache.commons.mail SimpleEmail DefaultAuthenticator)))

(defn smtp
  "Creates a new SMTP mailer function with given settings"
  ([host port] (smtp host port nil nil false))
  ([host port username password] (smtp host port username password false))
  ([host port username password tls]
   (fn [from to subject body]
     (doto (SimpleEmail.)
      (if (and username password)
        (.setAuthenticator (DefaultAuthenticator. username password)))
      (.setHostName host)
      (.setSmtpPort port)
      (.setTLS      tls)
      (.setFrom     from)
      (.setSubject  subject)
      (.setMsg      body)
      (.addTo       to)
      (.send)))))

(defn gmail
  "Creates a new SMTP mailer function with given Gmail username and password.
  Shortcut for (smtp 'smtp.gmail.com' 587 username password true)"
  [username password]
  (smtp "smtp.gmail.com" 587 username password true))
