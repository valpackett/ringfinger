(ns basefinger.test
  (:use basefinger.core, basefinger.inmem, midje.sweet))

(reset-inmem-db)

(facts "about CRUD"
  (create  inmem :test {:key "value"}) => {:key "value"}
  (get-one inmem :test {:query {:key "value"}}) => {:key "value"}
  (update  inmem :test (get-one inmem :test {:query {:key "value"}}) {:key "updated"}) => {:test [{:key "updated"}]}
  (delete  inmem :test (get-one inmem :test {:query {:key "updated"}})) => {:test []})

(create inmem :atm {:num 1})

(facts "about atomic modifications"
  (modify inmem :atm {:num 1} {:$inc {:num 10}})  => (contains {:atm '({:num 11})})
  (modify inmem :atm {:num 11} {:$dec {:num 1}})  => (contains {:atm '({:num 10})})
  (modify inmem :atm {:num 10} {:$set {:num 0}})  => (contains {:atm '({:num 0})})
  (modify inmem :atm {:num 0} {:$unset {:num 1}}) => (contains {:atm '({})}))

(create inmem :q {:a 1 :b 1})
(create inmem :q {:a 1 :b 2})
(create inmem :q {:a 1 :b 3})
(create inmem :q {:a 1 :b 4})

(facts "about querying"
  (get-many inmem :q {:query {:a {:$ne 1}}}) => '()
  (get-many inmem :q {:sort {:b -1}}) => '({:a 1 :b 4} {:a 1 :b 3} {:a 1 :b 2} {:a 1 :b 1})
  (get-many inmem :q {:skip 1 :limit 2 :sort {:b 1}}) => '({:a 1 :b 2} {:a 1 :b 3}))

(reset-inmem-db)
