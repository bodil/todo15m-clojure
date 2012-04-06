(ns todo15m.server
  (:use [todo15m.helpers])
  (:require [noir.server :as server]
            [noir.cljs.core :as cljs]
            [todo15m.views]))

(defn -main [& m]
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "1337"))
        mongo-uri (get (System/getenv) "MONGOLAB_URI" "mongodb://127.0.0.1/todo15m")]
    (monger-connect! mongo-uri)
    (cljs/start mode)
    (server/start port {:mode mode
                        :ns 'todo15m})))
