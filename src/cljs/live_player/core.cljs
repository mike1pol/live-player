(ns live-player.core
  (:require [live-player.events]
            [live-player.subs]
            [live-player.config :as config]
            [reagent.core :as r]
            [re-frame.core :as rf]
            [live-player.components.root :refer [app-root]]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (rf/clear-subscription-cache!)
  (r/render [app-root] (.getElementById js/document "app")))

(defn ^:export init []
  (rf/dispatch-sync [:initialize-db])
  (dev-setup)
  (mount-root))
