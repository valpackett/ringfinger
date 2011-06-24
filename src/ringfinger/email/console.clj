(ns ringfinger.email.console
  (:use ringfinger.email))

(def console
  (reify Mailer
    (send-mail [self from to subject body]
      (prn (str "From: " from))
      (prn (str "To: "   to))
      (prn (str "Subj: " subject))
      (prn body))))
