(ns live-player.components.core
  (:require [re-frame.core :as rf]
            [reagent.crypt :as crypt]
            [clojure.string :as str]
            [live-player.stream :as stream]
            [reagent.core :as r]))

(def fs (js/require "fs"))
(def path (js/require "path"))
(def parse-torrent (js/require "parse-torrent"))

;; change page
(defn change-page [event page]
  (.preventDefault event)
  (rf/dispatch [:change-page page]))

;; drop-file
(defn drop-file [files]
  (let [file-js (aget files 0)
        tmp-dir (rf/subscribe [:get-in [:tmp-dir]])
        file {:name (aget file-js "name")
              :hash (crypt/hash (aget file-js "name") :md5 true)
              :last-modified (aget file-js "lastModified")
              :last-modified-date (aget file-js "lastModifiedDate")
              :size (aget file-js "size")
              :type (aget file-js "type")}
        file-orig (.readFileSync fs (aget file-js "path"))
        info (parse-torrent file-orig)
        file-path (.join path @tmp-dir (str (:hash file) ".torrent"))]
    (when (str/ends-with? (:name file) ".torrent")
      (when-not (.existsSync fs file-path)
        (.writeFileSync fs file-path file-orig))
      (let [file (merge file {:path file-path
                              :info (js->clj info :keywordize-keys true)})]
        (rf/dispatch [:drop-file file])))))

;; interop react-dropzone component
(def dropzone (r/adapt-react-class (js/require "react-dropzone")))
