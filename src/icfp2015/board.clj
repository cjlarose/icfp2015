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
  "Creates a new new board with a nil initial-unit"
  [w h filled]
  { :width w
    :height h
    :filled filled
    :current-unit nil })

(defn- has-collision?
  "Does the current unit collide with filled cells?"
  [{:keys [filled current-unit]}]
  (not (= (count (intersection filled (:members current-unit))) 0)))

(defn- current-unit-in-bounds?
  "Is the current unit of a board within the bounds of the board"
  [{:keys [width height current-unit]}]
  (let [in-board? (fn [[i j]] (and (>= j 0) (< j width) (>= i 0) (< i height)))]
    (every? in-board? (:members current-unit))))

(defn valid-position? [board]
  (and (not (has-collision? board)) (current-unit-in-bounds? board)))

(defn- lock-current-unit
  "Just merges the current unit into the locked cells"
  [{:keys [width height filled current-unit] :as board}]
  {:pre [(valid-position? board)]}
  { :width width
    :height height
    :filled (union filled (:members current-unit))
    :current-unit nil })

(defn- clear-rows
  "Clears full rows. Moves cells above cleared row downward"
  [{:keys [width height filled] :as board}]
  (let [row-is-filled (fn [i] (->> (range width)
                                   (map (fn [j] [i j]))
                                   (every? (partial contains? filled))))
        filled-rows (filter row-is-filled (range height))
        partition-cells (fn [cells i] [(set (filter (fn [[ii _]] (< ii i)) cells))
                                       (set (filter (fn [[ii _]] (> ii i)) cells))])
        remove-row (fn [cells i]
                     (let [[above below] (partition-cells cells i)]
                       (union
                         below
                         (set (map (fn [[ii jj]] [(inc ii) jj]) above)))))
        new-filled (reduce remove-row filled filled-rows)]
    (assoc board :filled new-filled)))

; these are possible commands
; [ [:move :west] [:move :east] [:move :southwest] [:move :southeast]
;   [:rotate :clockwise] [:rotate :counterclockwise]
;   [:lock] ]
(defn transition-board
  "Given a board and command, returns a new (possibly invalid) board"
  [board [command arg]]
  (case command
    :move (let [unit-fn (apply unit/translate (unit/translations arg))]
             { :width (:width board)
               :height (:height board)
               :filled (:filled board)
               :current-unit (unit-fn (:current-unit board)) })
    :lock (clear-rows (lock-current-unit board))))
