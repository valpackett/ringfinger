(ns basefinger.mongodb
  "MongoDB support. Don't forget to add Congomongo to your deps!"
  (:use basefinger.core, somnium.congomongo))

(deftype MongoDB [conn] Database
  (create [self coll data]
    (with-mongo conn (insert! coll data)))
  (create-many [self coll data]
    (with-mongo conn (mass-insert! coll data)))
  (get-many [self coll options]
    (with-mongo conn (fetch coll :where (:query options {})
                                 :one?  (:one?  options false)
                                 :skip  (:skip  options 0)
                                 :limit (:limit options 0)
                                 :sort  (:sort  options))))
  (get-one [self coll options]
    (get-many self coll (assoc options :one? true)))
  (update [self coll entry data]
    (with-mongo conn (update! coll entry data)))
  (modify [self coll entry modifiers]
    (with-mongo conn (fetch-and-modify coll entry modifiers :return-new true :upsert? true)))
  (delete [self coll entry]
    (with-mongo conn (destroy! coll entry))))

(defn mongodb
  "Creates a MongoDB data storage object using given db and a list of instances.
  Each instance is a map containing :host and/or :port"
  ([db] (mongodb db [{}]))
  ([db instances] (MongoDB. (apply make-connection db instances)))
  ([db username password instances]
   (let [conn (apply make-connection db instances)]
     (authenticate conn username password)
     (MongoDB. conn))))
