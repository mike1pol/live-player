(ns live-player.stream
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(defn watch-streams []
  (let [file (rf/subscribe [:file])]
    (println @file)))

(def path (js/require "path"))
(def web-torrent (js/require "webtorrent"))

(def stream (web-torrent.))
(def update-hash (r/atom nil))

(do
  (.on stream "error" #(.log js/console %)))

(defn update-info [torrent]
  (when (= @update-hash (aget torrent "infoHash"))
    (let [info {:time-remaining (aget torrent "timeRemaining")
                :received (aget torrent "received")
                :downloaded (aget torrent "downloaded")
                :download-speed (aget torrent "downloadSpeed")
                :uploaded (aget torrent "uploaded")
                :upload-speed (aget torrent "uploadSpeed")
                :ratio (aget torrent "ratio")
                :progress (aget torrent "progress")
                :peers (aget torrent "numPeers")}]
      (println (aget torrent "infoHash"))
      (println info)
      (rf/dispatch [:update-info info])
      (js/setTimeout #(update-info torrent) 2000))))

(defn torrent-start [torrent]
  (.log js/console torrent)
  (js/setTimeout #(update-info torrent) 2000))

(defn start [file]
  (let [tmp-dir (rf/subscribe [:get-in [:tmp-dir]])
        options {:path @tmp-dir}]
    (.forEach
     (aget stream "torrents")
     (fn [torrent]
       (let [hash (aget torrent "infoHash")]
         (.remove stream hash (fn [err]
                                (if-not (empty? err)
                                  (.error js/console err)
                                  (.log js/console "removed: ", hash)))))))
    (reset! update-hash (get-in file [:info :infoHash]))
    (.add stream (clj->js (:info file)) (clj->js options) torrent-start)))

(defn stop []
  (println "stop stream"))
