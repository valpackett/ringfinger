(ns ringfinger.test.db
  (:use ringfinger.db, ringfinger.db.inmem, clojure.test))

(deftest creation
  (is (= {:test [{:key "value"}]} (create inmem :test {:key "value"}))))

(deftest reading
  (is (= {:key "value"} (get-one inmem :test {:query {:key "value"}}))))

(deftest updating
  (is (= {:test [{:key "updated"}]} (update inmem :test (get-one inmem :test {:query {:key "value"}}) {:key "updated"}))))

(deftest deleting
  (is (= {:test []} (delete inmem :test (get-one inmem :test {:query {:key "updated"}})))))

(deftest querying
  (create inmem :q {:a 1 :b 1})
  (create inmem :q {:a 1 :b 2})
  (create inmem :q {:a 1 :b 3})
  (create inmem :q {:a 1 :b 4})
  (is (= '() (get-many inmem :q {:query {:a {:$ne 1}}})))
  (is (= '({:a 1 :b 4} {:a 1 :b 3} {:a 1 :b 2} {:a 1 :b 1}) (get-many inmem :q {:sort {:b -1}})))
  (is (= '({:a 1 :b 2} {:a 1 :b 3}) (get-many inmem :q {:skip 1 :limit 2 :sort {:b 1}}))))

(defn test-ns-hook [] ; order matters here
  (reset-inmem-db)
  (creation)
  (reading)
  (updating)
  (deleting)
  (querying)
  (reset-inmem-db))
