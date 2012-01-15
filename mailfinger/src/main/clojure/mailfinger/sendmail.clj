(ns mailfinger.sendmail)

(def first-sendmail
  (first (filter #(.isFile (java.io.File. %))
    (map #(str "/usr/" % "/sendmail")
         ["sbin" "bin" "local/sbin" "local/bin"]))))

(defn sendmail
  "Creates a new Sendmail mailer function"
  ([] (sendmail first-sendmail))
  ([sendmail-path]
    (fn [from to subject body]
      (let [process (.start (ProcessBuilder. [sendmail-path (str "-f " from) to]))
            stream (java.io.PrintStream. (.getOutputStream process))]
              (.print stream (format "Subject: %s\nContent-Type: text/plain;encoding=utf-8\n\n%s" subject body))
              (.close stream)
              (.waitFor process)))))