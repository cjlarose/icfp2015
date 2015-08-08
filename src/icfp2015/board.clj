(ns icfp2015.board
  (:require [clojure.set :refer [intersection]]))

; board state is represented as a current-unit together with its currently
; filled-and-locked cells
; e.g. { :width 10
;        :height 15
;        :current-unit { :members #{ [2 0] [0 1] [2 2] } :pivot [1 1] }
;        :filled #{ [5 4] [4 3] } }
(defn make-board
  "Creates a new new board with an initial-unit"
  [w h filled initial-unit]
  { :width w
    :height h
    :filled filled
    :current-unit initial-unit })

(defn translate [dx dy]
  (fn [unit]
    (let [f (fn [[x y]] [(+ x dx) (+ y dy)])]
      { :members (set (map f (:members unit)))
        :pivot (f (:pivot unit)) })))

(def translations
  { :west [-1 0]
    :east [1 0]
    :southwest [0 1]
    :southeast [1 1] })

(defn has-collision?
  "Does the current unit collide with filled cells?"
  [{:keys [filled current-unit]}]
  (not (= (count (intersection filled (:members current-unit))) 0)))

(defn current-unit-in-bounds?
  "Is the current unit of a board within the bounds of the board"
  [{:keys [width height current-unit]}]
  (let [in-board? (fn [[x y]] (and (>= x 0) (< x width) (>= y 0) (< y height)))]
    (every? in-board? (:members current-unit))))

; these are possible commands
; [ [:move :west] [:move :east] [:move :southwest] [:move :southeast]
;   [:rotate :clockwise] [:rotate :counterclockwise] ]
(defn transition-board
  "Given a board and command, returns a new board. Raises if performing
  the command puts the board into an illegal state"
  [board [command direction]]
  (if (= command :move)
    (let [unit-fn (apply translate (translations direction))]
      { :width (:width board)
        :height (:height board)
        :filled (:filled board)
        :current-unit (unit-fn (:current-unit board)) })))

(defn lock-and-spawn
  "Locks the current unit. Clears rows. Moves cells down. Spawns new unit.
  Raises if new-unit cannot be spawned"
  [board new-unit]
  nil)
