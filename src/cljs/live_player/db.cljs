(ns live-player.db)

(def app (aget (aget (js/require "electron") "remote") "app"))
(def path (js/require "path"))
(def mkdirp (js/require "mkdirp"))

(def tmp-dir (.join path (.getPath app "appData") "com.live-player" "temp"))
(mkdirp tmp-dir (fn [error]
                  (when error (.error js/console error))))

(def default-db
  {:name "re-frame"
   :page :index
   :tmp-dir tmp-dir
   :file nil
   :state nil
   :time nil})
