(ns icfp2015.unit
  (:require [clojure.set :refer [intersection union]]))

(defn translate [dx dy]
  (fn [unit]
    (let [f (fn [[x y]] [(+ x dx) (+ y dy)])]
      { :members (set (map f (:members unit)))
        :pivot (f (:pivot unit)) })))

(def translations
  { :north [0 -2]
    :west [-1 0]
    :east [1 0]
    :south [0 2]
    :southwest [0 1]
    :southeast [1 1]
})

(defn rotate
  "Rotate a unit around a pivot point"
  [rotation, {:keys [pi pj] :as pivot}]
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
