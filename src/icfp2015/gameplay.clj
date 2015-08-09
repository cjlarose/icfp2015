(ns icfp2015.gameplay
  (:require [icfp2015.board :refer [make-board transition-board]]))

(defn handle-unit
  [ai initial-board unit]
  (let [f (fn [[board commands]]
            ; (println board commands)
            (let [new-command (ai board)
                  new-board (transition-board board new-command)]
              [new-board (conj commands new-command)]))
        has-current-unit (fn [[{:keys [current-unit]} _]] current-unit)
        while-unit-active (fn [coll]
                            (let [[left right] (split-with has-current-unit coll)]
                              (conj (vec left) (first right))))]
    (-> (iterate f [(assoc initial-board :current-unit unit) '()])
        (rest)
        (while-unit-active)
        (last))))

(defn get-commands
  "Yields a seq of commands given a game and an AI"
  [ai {:keys [width height filled units]}]
  (let [f (fn [[board commands] unit]
            (let [[new-board addtl-commands] (handle-unit ai board unit)]
              [new-board (concat commands addtl-commands)]))]
    (second (reduce f [(make-board width height filled) '()] units))))
