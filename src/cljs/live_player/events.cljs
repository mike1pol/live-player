(ns live-player.events
  (:require [re-frame.core :as rf]
            [live-player.db :as db]))

(rf/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(rf/reg-event-db
 :change-page
 (fn [db [_ page]]
   (assoc-in db [:page] page)))

(rf/reg-event-db
 :drop-file
 (fn [db [_ file]]
   (let [db (assoc-in db [:file] file)]
     (assoc-in db [:page] :loader))))

(rf/reg-event-db
 :error
 (fn [db [_ path error]]
   (assoc-in db [:errors path] error)))


(rf/reg-event-db
 :update-info
 (fn [db [_ info]]
   (assoc-in db [:info] info)))
