(ns icfp2015.core
  (:gen-class))

(defn lcg [modulus multiplier increment]
  (fn [seed]
    (let [next-item (fn [previous-item] (mod (+ (* multiplier previous-item) increment) modulus))]
      (iterate next-item seed))))

(defn source-sequence [seed]
  (let [raw-seq (lcg (bit-shift-left 1 32) 1103515245 12345)
        truncate-bits (fn [x] (bit-and (bit-shift-right x 16) 0x7FFF))]
    (map truncate-bits (raw-seq seed))))

; board state is represented as a current-unit together with its currently
; filled-and-locked cells
; e.g. { :current-unit { :members [2 0] [0 1] [2 2] :pivot [1 1] }
;        :filled #{ [5 4] [4 3] } }
(defn make-board
  "Creates a new new board with an initial-unit"
  [w h filled initial-unit]
  nil)

; these are possible commands
; [ [:move :west] [:move :east] [:move :southwest] [:move :southeast]
;   [:rotate :clockwise] [:rotate :counterclockwise] ]
(defn transition-board
  "Given a board and command, returns a new board. Raises if performing
  the command puts the board into an illegal state"
  [board command]
  nil)

(defn lock-and-spawn
  "Locks the current unit. Clears rows. Moves cells down. Spawns new unit.
  Raises if new-unit cannot be spawned"
  [board new-unit]
  nil)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
