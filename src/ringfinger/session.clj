(ns ringfinger.session
  (:use ring.middleware.session.store,
        ringfinger.db)
  (:import java.util.UUID))

(deftype DBStore [db coll] SessionStore
  (read-session   [self key] (get-one db coll {:_sid key}))
  (write-session  [self kkey data]
    (let [key (or kkey (str (UUID/randomUUID)))
          ex (get-one db coll {:_sid key})]
      ; update won't work properly there
      ; in sessions, we need to be able to delete fields
      ; sessions are completely schema-less
      (if (not (nil? ex)) (delete db coll ex))
      (create db coll (merge data {:_sid key}))
      key))
  (delete-session [self key] (delete db coll (get-one db coll {:_sid key})) nil))

(defn db-store
  ([db] (db-store db :ringfinger_sessions))
  ([db coll] (DBStore. db coll)))
