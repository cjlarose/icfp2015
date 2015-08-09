(ns icfp2015.unit
  (:require [clojure.set :refer [intersection union]]
            [icfp2015.cell :refer [translate-dir]]))

(defn translate [direction]
  (fn [unit]
    (let [f (translate-dir direction)]
      { :members (set (map f (:members unit)))
        :pivot (f (:pivot unit)) })))

(defn rotate
  "Rotate a unit around a pivot point"
  [rotation, [[pi pj]]]
  nil)

(def rotations
  [:counter-clockwise
    { :origin [0 0]
      :northwest :southwest
      :northwest-offset :south
      :north :southwest-offset
      :west :southeast
      :southwest :east
      :southwest-offset :southeast-offset
      :southeast :northeast
      :south :northeast-offset
      :southeast-offset :north
      :east :northwest
      :northeast :west
      :northeast-offset :northwest-offset }
  :clockwise
    { :origin [0 0]
      :northwest :east
      :northwest-offset :northeast-offset
      :north :southeast-offset
      :west :southwest
      :southwest :northwest
      :southwest-offset :north
      :southeast :west
      :south :northwest-offset
      :southeast-offset :southwest-offset
      :east :southwest
      :northeast :southeast
      :northeast-offset :south }])
