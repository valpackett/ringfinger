(ns ringfinger.email.smtp
  (:use ringfinger.email)
  (:import (org.apache.commons.mail SimpleEmail DefaultAuthenticator)))

(deftype SMTPMailer [host port username password tls] Mailer
  (send-mail [self from to subject body]
    (doto (SimpleEmail.)
        (.setAuthenticator (DefaultAuthenticator. username password))
        (.setHostName host)
        (.setSmtpPort port)
        (.setTLS      tls)
        (.setFrom     from)
        (.setSubject  subject)
        (.setMsg      body)
        (.addTo       to)
        (.send))))

(defn smtp
  ([host port username password] (smtp host port username password false))
  ([host port username password tls] (SMTPMailer. host port username password tls)))

(defn gmail [username password]
  (SMTPMailer. "smtp.gmail.com" 587 username password true))
