(ns live-player.components.index
  (:require [re-frame.core :as rf]
            [live-player.components.core :as core]))

(defn index []
  (let [name (rf/subscribe [:name])
        ]
    [:div
     [:p "Hello from index component: " @name]
     [:button {:on-click #(core/change-page % :player)}
      "Player"]]))
