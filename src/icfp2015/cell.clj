(ns icfp2015.cell)

(defn offset->cube
  "Convert offset coordinate plane into cube coordinate plane"
  [[row col]]
  (let [x (- col (/ (- row (bit-and row 1)) 2))
        y row
        z (- (- x) y)]
    [x y z]))

(defn cube->offset
  "Convert cube coordinate place to offset coordinate plane"
  [[x y z]]
  (let [col (+ x (/ (- y (bit-and y 1)) 2))
        row y]
    [row col]))

