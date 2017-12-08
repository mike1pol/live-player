(ns live-player.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 :name
 (fn [db]
   (:name db)))

(rf/reg-sub
 :current-page
 (fn [db]
   (:page db)))

(rf/reg-sub
 :file
 (fn [db]
   (:file db)))

(rf/reg-sub
 :get-in
 (fn [db [_ path]]
   (get-in db path)))
