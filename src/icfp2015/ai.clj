(ns icfp2015.ai
  (:require [icfp2015.board :as board]))

(def dumb-ai (constantly [:move :southeast]))

(defn slightly-smarter-ai [board]
  (let [new-board (board/transition-board board [:move :southwest])]
    (if (nil? (:current-unit new-board))
      [:move :southeast]
      [:move :southwest])))
