(ns icfp2015.board
  (:require [clojure.set :refer [intersection union]]
            [clojure.string :refer [join]]
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

(defn spawn-unit [{:keys [width] :as board} {:keys [members pivot] :as unit}]
  (let [[_ lj] (apply min-key second members)
        [_ rj] (apply max-key second members)
        unit-width (inc (- rj lj))
        remaining-cols (- width unit-width)
        new-lj (quot remaining-cols 2)
        dj (- new-lj lj)
        translated-unit ((unit/translate 0 dj) unit)]
    (assoc board :current-unit translated-unit)))

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
  [{:keys [width height filled current-unit] :as board}]
  {:pre [(nil? current-unit)]}
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

(defn board->str [{:keys [width height filled current-unit]}]
  (let [print-cell (fn [cell]
                     (cond
                       (contains? (:members current-unit) cell) "⬡"
                       (contains? filled cell) "⬢"
                       :else "."))
        print-row (fn [i]
                    (str
                      (if (odd? i) " " "")
                      (->> (range width)
                           (map (fn [j] [i j]))
                           (map print-cell)
                           (join " "))))]
    (join "\n" (map print-row (range height)))))

; these are possible commands
; [ [:move :west] [:move :east] [:move :southwest] [:move :southeast]
;   [:rotate :clockwise] [:rotate :counterclockwise] ]
(defn transition-board
  "Given a board and command, returns a new (possibly invalid) board"
  [board [command arg]]
  (let [unit-fn (case command
                  :rotate (unit/rotate arg)
                  :move (unit/translate arg))
        new-board (update-in board [:current-unit] unit-fn)]
    (if (valid-position? new-board)
      new-board
      (clear-rows (lock-current-unit board)))))
