(ns icfp2015.unit
  (:require [clojure.set :refer [intersection union]]))

(defn translate [dx dy]
  (fn [unit]
    (let [f (fn [[x y]] [(+ x dx) (+ y dy)])]
      { :members (set (map f (:members unit)))
        :pivot (f (:pivot unit)) })))

(def translations
  { :west [-1 0]
    :east [1 0]
    :southwest [0 1]
    :southeast [1 1] })

