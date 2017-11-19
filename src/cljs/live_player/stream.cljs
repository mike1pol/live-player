(ns live-player.stream
  (:require [reagent.core :as r]
            [re-frame.core :as rf]))

(def path (js/require "path"))
(def web-torrent (js/require "webtorrent"))

(def stream (web-torrent.))

(do
  (.on stream "error" #(.log js/console %)))

(defn torrent-start [torrent]
  (.log js/console torrent))

(defn start [file]
  (let [tmp-dir (rf/subscribe [:get-in [:tmp-dir]])
        options {:path @tmp-dir
                 :maxWebConns 10}]
    (.forEach
     (aget stream "torrents")
     (fn [torrent]
       (let [hash (aget torrent "infoHash")]
         (println hash)
         (.remove stream hash (fn [err]
                                (if-not (empty? err)
                                  (.error js/console err)
                                  (.log js/console "removed: ", hash)))))))
    (.add stream (clj->js (:info file)) (clj->js options) torrent-start)
    (println "Start stream")))

(defn stop []
  (println "stop stream"))
