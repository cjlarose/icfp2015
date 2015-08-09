(ns icfp2015.gameplay
  (:require [icfp2015.board :refer [make-board transition-board valid-position?]]))

(defn handle-unit
  [ai initial-board unit]
  (let [f (fn [[board commands]]
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
  (let [f (fn [[board commands] unit]
            (let [[new-board addtl-commands] (handle-unit ai board unit)]
              [new-board (concat commands addtl-commands)]))]
    (second (reduce f [(make-board width height filled) '()] units))))
