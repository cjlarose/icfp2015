(ns icfp2015.board)

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

; these are possible commands
; [ [:move :west] [:move :east] [:move :southwest] [:move :southeast]
;   [:rotate :clockwise] [:rotate :counterclockwise] ]
(defn transition-board
  "Given a board and command, returns a new board. Raises if performing
  the command puts the board into an illegal state"
  [board [command direction]]
  nil)

(defn lock-and-spawn
  "Locks the current unit. Clears rows. Moves cells down. Spawns new unit.
  Raises if new-unit cannot be spawned"
  [board new-unit]
  nil)
