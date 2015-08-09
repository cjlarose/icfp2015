(ns icfp2015.cell)

(defn translate [di dj]
  (fn [[i j]] [(+ i di) (+ j dj)]))

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

(def offsets { :west [-1 1 0]
               :east [1 -1 0]
               :southwest [-1 0 1]
               :southeast [0 -1 1] })

(defn translate-dir [direction]
  (fn [coords]
    (->> coords
         (offset->cube)
         (map + (direction offsets))
         (vec)
         (cube->offset))))
