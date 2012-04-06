(defproject todo15m "0.1.0-SNAPSHOT"
  :description "The traditional todo webapp, in Clojure"
  :url "http://github.com/bodil/todo15m-clojure"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [noir "1.3.0-alpha10"]
                 [com.novemberain/monger "1.0.0-beta2"]]
  :main ^{:skip-aot true} todo15m.server)
