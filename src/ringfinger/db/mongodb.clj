(ns ringfinger.db.mongodb
  (:use ringfinger.db, somnium.congomongo))

(deftype MongoDB [conn] Database
  (create [self coll data]
    (with-mongo conn (insert! coll data)))
  (get-many [self coll query]
    (with-mongo conn (fetch coll :where query)))
  (get-one [self coll query]
    (with-mongo conn (fetch-one coll :where query)))
  (update [self coll entry data]
    (with-mongo conn (update! coll entry data)))
  (delete [self coll entry]
    (with-mongo conn (destroy! coll entry))))

(defn mongodb
  "Creates a MongoDB data storage object using given Congomongo connection"
  [conn] (MongoDB. conn))
