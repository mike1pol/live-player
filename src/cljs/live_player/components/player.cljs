(ns live-player.components.player
  (:require [live-player.components.core :refer [change-page]]))


(defn player []
  (let []
    [:div
     [:p "Player page"]
     [:button {:on-click #(change-page % :index)}
      "Index page"]]))
