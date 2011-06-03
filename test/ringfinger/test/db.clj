(ns ringfinger.test.db
  (:use ringfinger.db, ringfinger.db.inmem, clojure.test))

(deftest creation
  (is (= {:test [{:key "value"}]} (create inmem :test {:key "value"}))))

(deftest reading
  (is (= {:key "value"} (get_one inmem :test {:key "value"}))))

(deftest updating
  (is (= {:test [{:key "updated"}]} (update inmem :test (get_one inmem :test {:key "value"}) {:key "updated"}))))

(deftest deleting
  (is (= {:test []} (delete inmem :test (get_one inmem :test {:key "updated"})))))

(defn test-ns-hook [] ; order matters here
  (creation)
  (reading)
  (updating)
  (deleting)
  (reset-inmem-db))
