(ns icfp2015.gameplay
  (:require [icfp2015.board :refer [make-board transition-board]]))

; (defprotocol AI
;   (get-move [board] "Returns a [new board, command] vector"))

(defn handle-unit
  [ai initial-board unit]
  (let [f (fn [[board commands]]
            ; (println board commands)
            (let [new-command (ai board)
                  new-board (transition-board board new-command)]
              [new-board (conj commands new-command)]))]
    (second (last (take-while
      (fn [[{:keys [current-unit]} _]] current-unit)
      (rest (iterate f [(assoc initial-board :current-unit unit) '()])))))))

; (defn get-commands
;   "Yields a seq of commands given a game and an AI"
;   [{:keys [width height filled units]} ai]
;   (let [f (fn [[board commands] unit] 
;             (let [[new-board addtl-commands] (handle-unit board unit)]
;               [new-board (concat commands addtl-commands)]))]
;     (reduce f [(make-board width height filled) '()] units)))
    
;; make board
;; for every unit
;;   set current unit on board
;;   until current-until is nil
;;     feed board into ai 
;;     append commands to list
