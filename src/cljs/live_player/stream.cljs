(ns live-player.stream
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(def path (js/require "path"))
(def crypto-js (js/require "crypto"))
(def peerflix (js/require "peerflix"))

(def stream (r/atom nil))

(defn start [file]
  (let [tmp-dir (rf/subscribe [:get-in [:tmp-dir]])
        options {:connections 100
                 :dht true
                 :tracker true
                 :port 0
                 :trackers ["udp://tracker.openbittorrent.com:80"
                            "udp://tracker.coppersurfer.tk:6969"
                            "udp://open.demonii.com:1337"]
                 :tmp @tmp-dir
                 :path (.join path @tmp-dir (get-in file [:info :infoHash]))
                 :buffer (.toString (* 1.5  1024 1024))
                 :index 0
                 :name (get-in file [:info :infoHash])
                 :id (.toString (.pseudoRandomBytes crypto-js 10) "hex")}]
    (println options)
    ;; verify - event
    ;; ready - event
    ;; uninterested - event
    ;; interested - event
    (println "Start stream")))

(defn stop []
  (println "stop stream"))
