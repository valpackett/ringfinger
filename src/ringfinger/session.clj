(ns ringfinger.session
  (:use ring.middleware.session.store,
        ringfinger.db)
  (:import java.util.UUID))

(deftype DBStore [db coll] SessionStore
  (read-session   [self key] (get_one db coll {:_sid key}))
  (write-session  [self kkey data]
    (let [key (or kkey (str (UUID/randomUUID)))
          ex (get_one db coll {:_sid key})]
      (if (nil? ex)
        (create db coll (merge data {:_sid key}))
        (update db coll ex data))
      key))
  (delete-session [self key] (delete db coll (get_one db coll {:_sid key})) nil))

(defn db-store
  ([db] (db-store db :ringfinger_sessions))
  ([db coll] (DBStore. db coll)))
