(ns icfp2015.cell)

(defn translate [di dj]
  (fn [[i j]] [(+ i di) (+ j dj)]))

(defn translate-dir [direction]
  (case direction
    :west (translate 0 -1)
    :east (translate 0 1)
    :southwest (fn [[i j]]
                 (if (even? i)
                   ((translate 1 -1) [i j])
                   ((translate 1 0) [i j])))
    :southeast (fn [[i j]]
                 (if (even? i)
                   ((translate 1 0) [i j])
                   ((translate 1 1) [i j])))))

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

