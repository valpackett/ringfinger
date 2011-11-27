(ns authfinger.test
  (:use midje.sweet, ring.mock.request
        basefinger.inmem,
        corefinger.core,
        (authfinger core routes))
  (:import org.apache.commons.codec.digest.DigestUtils))

(with-salt "salt"
  (make-user inmem :ringfinger_auth {:username "test"} "demo")

  (defapp testapp {:static-dir "lib" :middleware wrap-auth}
          (route "/user" (fn [req h]
                           {:status 200 :headers {}
                            :body (get-in req [:user :username])})))
  (fact
    (:body (testapp (header (request :get "/user") "Authorization" "Basic dGVzdDpkZW1v")))
    => "test")

  (let [usr (get-user inmem :ringfinger_auth "test" "demo")]
    (facts "about reading user info"
      (:username usr) => "test"
      (:password_hash usr) => (DigestUtils/sha256Hex (str (:password_salt usr) "saltdemo")))))

(binding [*request* (header (request :post "/auth/login") "Referer" "http://localhost/demo")]
  (fact
    (get-action "redirect")) => "/auth/login?redirect=%2Fdemo")

(reset-inmem-db)
