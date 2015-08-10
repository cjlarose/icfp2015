(ns icfp2015.gameplay
  (:require [icfp2015.board :refer [board->str make-board transition-board valid-position?]]))

(def DEBUG false)

(defn handle-unit
  [ai initial-board unit]
  (let [f (fn [[board commands]]
            (if DEBUG
              (do
                (println (board->str board))
                (println)
                (Thread/sleep 100)))
            (let [new-command (ai board)
                  new-board (transition-board board new-command)]
              [new-board (conj commands new-command)]))
        has-current-unit (fn [[{:keys [current-unit]} _]] current-unit)
        while-unit-active (fn [coll]
                            (let [[left right] (split-with has-current-unit coll)]
                              (conj (vec left) (first right))))
        board-with-spawn (assoc initial-board :current-unit unit)]
    (if (valid-position? board-with-spawn)
      (-> (iterate f [board-with-spawn '()])
          (rest)
          (while-unit-active)
          (last))
      [initial-board nil])))

(defn get-commands
  "Yields a seq of commands given a game and an AI"
  [ai {:keys [width height filled units]}]
  (loop [board (make-board width height filled)
         commands '() ; TODO: use vector
         remaining-units units]
    (if DEBUG
      (do
        (println (board->str board))
        (println)))
    (if (empty? remaining-units)
      commands
      (let [current-unit (first remaining-units)
            [new-board addtl-commands] (handle-unit ai board current-unit)]
        (if addtl-commands
          (recur new-board (concat commands addtl-commands) (rest remaining-units))
          commands)))))
