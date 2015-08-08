(ns icfp2015.board
  (:require [clojure.set :refer [intersection union]]
            [icfp2015.unit :as unit]))

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

(defn has-collision?
  "Does the current unit collide with filled cells?"
  [{:keys [filled current-unit]}]
  (not (= (count (intersection filled (:members current-unit))) 0)))

(defn current-unit-in-bounds?
  "Is the current unit of a board within the bounds of the board"
  [{:keys [width height current-unit]}]
  (let [in-board? (fn [[x y]] (and (>= x 0) (< x width) (>= y 0) (< y height)))]
    (every? in-board? (:members current-unit))))

(defn valid-position? [board]
  (and (not (has-collision? board)) (current-unit-in-bounds? board)))

; these are possible commands
; [ [:move :west] [:move :east] [:move :southwest] [:move :southeast]
;   [:rotate :clockwise] [:rotate :counterclockwise] ]
(defn transition-board
  "Given a board and command, returns a new (possibly invalid) board"
  [board [command direction]]
  (if (= command :move)
    (let [unit-fn (apply unit/translate (unit/translations direction))]
      { :width (:width board)
        :height (:height board)
        :filled (:filled board)
        :current-unit (unit-fn (:current-unit board)) })))

(defn lock-current-unit
  "Just merges the current unit into the locked cells"
  [{:keys [width height filled current-unit] :as board}]
  {:pre [(valid-position? board)]}
  { :width width
    :height height
    :filled (union filled (:members current-unit))
    :current-unit nil })

(defn clear-rows
  [{:keys [width height filled] :as board}]
  (let [row-is-filled (fn [i] (->> (range width)
                                   (map (fn [j] [i j]))
                                   (every? #(contains? filled %))))
        filled-rows (filter row-is-filled (range height))
        partition-cells (fn [cells i] [(set (filter (fn [[ii _]] (< ii i)) cells))
                                       (set (filter (fn [[ii _]] (> ii i)) cells))])
        remove-row (fn [cells i]
                     (let [[above below] (partition-cells cells i)]
                       (union
                         below
                         ;; TODO: Replace with (tranlate (if (even? i) :southeast :southwest))
                         (set (map (fn [[ii jj]] [(inc ii) jj]) above)))))
        new-filled (reduce remove-row filled filled-rows)]
    (assoc board :filled new-filled)))

(defn lock-and-spawn
  "Locks the current unit. Clears rows. Moves cells down. Spawns new unit.
  Raises if new-unit cannot be spawned"
  [board new-unit]
  nil)
