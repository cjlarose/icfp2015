(ns icfp2015.unit
  (:require [clojure.set :refer [intersection union]]
            [icfp2015.cell :refer [translate-dir rotate-cell]]))

(defn- move [unit f]
  { :members (set (map f (:members unit)))
    :pivot (f (:pivot unit)) })

(defn translate
  ([direction] (fn [unit]
                 (move unit (translate-dir direction))))
  ([di dj] (fn [unit]
             (move unit (fn [cell] (mapv + cell [di dj]))))))

(defn rotate [direction]
  (fn [unit]
    (move unit (rotate-cell (:pivot unit) direction))))
