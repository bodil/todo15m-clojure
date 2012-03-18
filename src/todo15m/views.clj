(ns todo15m.views
  (:require [noir.response :as response]
            [monger.collection :as monger])
  (:use [noir.core :only [defpage defpartial]]
        [hiccup.page-helpers :only [html5 include-css]]
        [hiccup.form-helpers])
  (:import [org.bson.types ObjectId]))

(defpartial todo-entry [{:keys [_id todo done]}]
  [:li {:class (if done "done" "open")}
   (form-to [:post (str "/delete/" _id)]
     [:input.delete {:type "submit" :value "\u267b"}])
   (form-to [:post (str "/done/" _id)]
     [:input.check {:type "submit" :value (if done "\u2611" "\u2610")}])
   [:span todo]])

(defpage "/" []
  (html5
   [:head
    [:title "15m Todo List"]
    (include-css "/15m.css")]
   [:body
    [:h1 "15 Minute Todo List"]
    [:ul
     (map todo-entry (monger/find-maps "todo"))]
    (form-to [:post "/new"]
      (text-field "todo"))
    [:script "document.querySelector(\"input[type=text]\").focus()"]]))

(defpage [:post "/new"] {:keys [todo]}
  (monger/insert "todo" { :todo todo :done false })
  (response/redirect "/"))

(defpage [:post "/done/:id"] {:keys [id]}
  (let [id (ObjectId. id)
        doc (monger/find-map-by-id "todo" id)]
    (monger/save "todo" (assoc doc :done (not (:done doc))))
    (response/redirect "/")))

(defpage [:post "/delete/:id"] {:keys [id]}
  (let [id (ObjectId. id)
        doc (monger/find-map-by-id "todo" id)]
    (monger/remove-by-id "todo" id)
    (response/redirect "/")))

