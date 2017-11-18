(ns live-player.components.root
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [live-player.components.core :as core]
            [live-player.components.index :refer [index]]
            [live-player.components.player :refer [player]]
            [live-player.components.loader :refer [loader]]))


(def drop-container {:display "flex"
                     :position "fixed"
                     :border "2px solid transparent"
                     :top 0
                     :bottom 0
                     :left 0
                     :right 0})

(def drop-container-active {:border "2px dashed #000"})

(def drop-body {:align-self "center"
                :font-size "20px"
                :width "100%"
                :text-align "center"})

(defn app-root []
  (let [current-page (rf/subscribe [:current-page])
        has-drop (r/atom nil)]
    (fn [_]
      [core/dropzone
       {:on-drop #(do
                    (reset! has-drop nil)
                    (core/drop-file %))
        :disableClick true
        :onDragEnter #(reset! has-drop true)
        :onDragLeave #(reset! has-drop nil)
        :activeStyle drop-container-active
        :style drop-container}
       (if @has-drop
         [:div {:style drop-body}
          "Drop you file"]
         (case @current-page
           :index [index]
           :player [player]
           :loader [loader]))])))
