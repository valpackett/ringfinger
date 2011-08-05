(ns ringfinger.email.console)

(defn console [from to subject body]
  (prn (str "From: " from))
  (prn (str "To: "   to))
  (prn (str "Subj: " subject))
  (prn body))
