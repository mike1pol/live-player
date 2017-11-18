(ns live-player.components.loader
  (:require [live-player.components.core :refer [change-page]]
            [re-frame.core :as rf]))


(defn loader []
  (let [file (rf/subscribe [:get-in [:file]])]
    [:div
     (if (empty? (get-in @file [:info :files]))
       [:p "File " (:name @file) " is loading"]
       [:div "Select file:"
        [:ul
         (for [file (get-in @file [:info :files])]
           [:li {:key (pr-str file)} (:name file)])]])
     [:button {:on-click #(change-page % :index)}
      "Index page"]]))
