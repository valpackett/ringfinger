(ns ringfinger.session
  (:use ring.middleware.session.store,
        ringfinger.db))

(deftype DBStore [db coll] SessionStore
  (read-session   [self key] (get_one db coll {:_sid key}))
  (write-session  [self key data] (let [ex (get_one db coll {:_sid key})]
                                    (if ex (update db coll ex data)
                                           (create db coll (merge data {:_sid key})))))
  (delete-session [self key] (delete db coll key)))

(defn db-store
  ([db] (db-store db :ringfinger_sessions))
  ([db coll] (DBStore. db coll)))
