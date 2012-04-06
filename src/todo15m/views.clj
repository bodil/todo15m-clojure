(ns todo15m.views
  (:require [monger.collection :as monger]
            [noir.cljs.core :as cljs])
  (:use [noir.core :only [defpage]]
        [hiccup.page :only [html5 include-css]]
        [hiccup.form :only [text-field]]
        [noir.fetch.remotes :only [defremote]])
  (:import [org.bson.types ObjectId]))

(defpage "/" []
  (html5
   [:head
    [:title "15m Todo List"]
    (include-css "/15m.css")]
   [:body
    [:h1 "15 Minute Todo List"]
    [:ul#todos]
    [:form#new (text-field "todo")]
    (cljs/include-scripts :with-jquery)]))

(defn get-todo-list []
  (map #(assoc % :_id (str (:_id %))) (monger/find-maps "todo")))

(defremote todo-list []
  (get-todo-list))

(defremote toggle-todo [id]
  (let [id (ObjectId. id)
        doc (monger/find-map-by-id "todo" id)]
    (monger/save "todo" (assoc doc :done (not (:done doc))))
    (get-todo-list)))

(defremote delete-todo [id]
  (let [id (ObjectId. id)
        doc (monger/find-map-by-id "todo" id)]
    (monger/remove-by-id "todo" id)
    (get-todo-list)))

(defremote add-todo [todo]
  (monger/insert "todo" { :todo todo :done false })
  (get-todo-list))


