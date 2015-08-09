(ns icfp2015.unit
  (:require [clojure.set :refer [intersection union]]
            [icfp2015.cell :refer [translate-dir rotate-cell]]))

(defn translate [direction]
  (fn [unit]
    (let [f (translate-dir direction)]
      { :members (set (map f (:members unit)))
        :pivot (f (:pivot unit)) })))

(defn rotate [direction]
  (fn [unit]
    (let [f (rotate-cell (:pivot unit) direction)]
      { :members (set (map f (:members unit)))
        :pivot (f (:pivot unit)) })))
