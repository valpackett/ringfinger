(ns ringfinger.email.smtp
  (:use ringfinger.email)
  (:import (org.apache.commons.mail SimpleEmail DefaultAuthenticator)))

(deftype SMTPMailer [host port username password tls] Mailer
  (send-mail [self from to subject body]
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
      (.send))))

(defn smtp
  "Creates a new SMTP mailer object with given settings"
  ([host port] (SMTPMailer. host port nil nil false))
  ([host port username password] (SMTPMailer. host port username password false))
  ([host port username password tls] (SMTPMailer. host port username password tls)))

(defn gmail [username password]
  "Creates a new SMTP mailer object with given Gmail username and password.
  Shortcut for (smtp 'smtp.gmail.com' 587 username password true)"
  (SMTPMailer. "smtp.gmail.com" 587 username password true))
