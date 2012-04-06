(ns todo15m.client
  (:use-macros [crate.macros :only [defpartial]]
               [fetch.macros :only [remote]])
  (:require ;[clojure.browser.repl :as repl]
            [fetch.remotes :as remotes]
            [crate.core :as crate]
            [domina :as d]
            [domina.events :as events]
            [domina.css :as css]))

;(repl/connect "http://localhost:9000/repl")

(defpartial todo-entry [{:keys [_id todo done]}]
  [:li {:class (if done "done" "open")}
   [:span.delete "\u267b"]
   [:span.check (if done "\u2611" "\u2610")]
   [:span.todo todo]])

(defn toggle-todo [id]
  (remote (toggle-todo id) [result]
          (update-todos result)))

(defn delete-todo [id]
  (remote (delete-todo id) [result]
          (update-todos result)))

(defn update-todos [todos]
  (let [ul (d/by-id "todos")]
    (d/destroy! (css/sel ul "li"))
    (doseq [todo todos
            :let [node (todo-entry todo)]]
      (d/append! ul node)
      (events/listen! (css/sel node "span.check") :click #(toggle-todo (:_id todo)))
      (events/listen! (css/sel node "span.delete") :click #(delete-todo (:_id todo)))))
  (.focus (d/by-id "todo")))

(defn on-submit [event]
  (.preventDefault event)
  (let [input (d/by-id "todo")
        todo (d/value input)]
    (d/set-value! input "")
    (remote (add-todo todo) [result]
            (update-todos result))))

(.addEventListener (d/by-id "new") "submit" on-submit)

(remote (todo-list) [result] (update-todos result))

