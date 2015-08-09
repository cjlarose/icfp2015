(ns icfp2015.cell)

(defn offset->cube
  "Convert offset coordinate plane into cube coordinate plane"
  [[row col]]
  (let [x (- col (/ (- row (bit-and row 1)) 2))
        z row
        y (- (- x) z)]
    [x y z]))

(defn cube->offset
  "Convert cube coordinate place to offset coordinate plane"
  [[x y z]]
  (let [row z
        col (+ x (/ (- z (bit-and z 1)) 2))]
    [row col]))

(def offsets
  { :west [-1 1 0]
    :east [1 -1 0]
    :southwest [-1 0 1]
    :southeast [0 -1 1] })

(defn rotate-cell
  "Returns a function that rotates a cell around a pivot point"
  [pivot direction]
  (fn [cell]
    (mapv + cell
      (let [pivot (offset->cube pivot)
          cell  (offset->cube cell)
          hex-vector (mapv - cell pivot)
          [x y z] hex-vector]
      (cube->offset
        (case direction
          :counterclockwise (mapv - [z x y])
          :clockwise (mapv - [y z x])))))))

(defn translate-dir [direction]
  (fn [cell]
    (->> cell
      (offset->cube)
      (mapv + (direction offsets))
      (cube->offset))))
