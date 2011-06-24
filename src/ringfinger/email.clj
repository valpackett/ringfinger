(ns ringfinger.email)

(defprotocol Mailer
  (send-mail [self from to subject body]))
