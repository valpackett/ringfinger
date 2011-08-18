(ns ringfinger.timesavers.actions
  "Functions for easy creation of actions"
  (:use (ringfinger resource util db),
        lamina.core))

(defn make-redir-action
  "Makes an action which applies a hook to the entry it's being called on
  and redirects the user to the viewing URL, showing a flash message,
  optionally sending the result to a Lamina channel"
  ([hook flash] (make-redir-action hook flash nil))
  ([hook flash channel]
   (fn [req matches entry default-data]
     (let [result (hook entry)]
       (update (:db default-data) (:coll default-data) entry result)
       (if channel (enqueue channel result))
       {:status  302
        :headers {"Location" (str (:urlbase default-data) "/" (get result (:pk default-data) (qsformat req)))}
        :flash   (call-or-ret flash result)}))))
