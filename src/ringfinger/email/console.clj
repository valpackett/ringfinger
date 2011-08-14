(ns ringfinger.email.console)

(defn console
  "Mailer function which just prints the 'messages' to the console, for development/testing.
  **NOT a function that should be called to create a mailer, but the mailer itself**"
  [from to subject body]
  (prn (str "From: " from))
  (prn (str "To: "   to))
  (prn (str "Subj: " subject))
  (prn body))
