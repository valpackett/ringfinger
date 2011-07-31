(ns ringfinger.db.mongodb
  (:use ringfinger.db, somnium.congomongo))

(deftype MongoDB [conn] Database
  (create [self coll data]
    (with-mongo conn (insert! coll data)))
  (get-many [self coll options]
    (with-mongo conn (fetch coll :where (:query options)
                                 :one?  (:one?  options)
                                 :skip  (:skip  options)
                                 :limit (:limit options)
                                 :sort  (:sort  options))))
  (get-one [self coll options]
    (get-many self coll (assoc options :one? true)))
  (update [self coll entry data]
    (with-mongo conn (update! coll entry data)))
  (delete [self coll entry]
    (with-mongo conn (destroy! coll entry))))

(defn mongodb
  "Creates a MongoDB data storage object using given db and a list of instances.
  Each instance is a map containing :host and/or :port"
  ([db] (mongodb db {}))
  ([db instances] (MongoDB. (make-connection db instances)))
  ([db username password instances]
   (let [conn (make-connection db instances)]
     (authenticate conn username password)
     (MongoDB. conn))))
