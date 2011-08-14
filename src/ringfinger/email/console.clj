(ns ringfinger.email.console
  "Mailer function which just prints the 'messages' to the console, for development/testing.")

(defn console [from to subject body]
  (prn (str "From: " from))
  (prn (str "To: "   to))
  (prn (str "Subj: " subject))
  (prn body))
