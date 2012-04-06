(defproject todo15m "0.1.0-SNAPSHOT"
  :description "The traditional todo webapp, in Clojure"
  :url "http://github.com/bodil/todo15m-clojure"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [com.novemberain/monger "1.0.0-beta2"]
                 [noir-cljs "0.3.0"]
                 [domina "1.0.0-beta1"]]
  :main ^{:skip-aot true} todo15m.server)
