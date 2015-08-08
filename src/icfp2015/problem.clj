(ns icfp2015.problem)

(defn lcg [modulus multiplier increment]
  (fn [seed]
    (let [next-item (fn [previous-item] (mod (+ (* multiplier previous-item) increment) modulus))]
      (iterate next-item seed))))

(defn source-sequence [seed]
  (let [raw-seq (lcg (bit-shift-left 1 32) 1103515245 12345)
        truncate-bits (fn [x] (bit-and (bit-shift-right x 16) 0x7FFF))]
    (map truncate-bits (raw-seq seed))))

; a problem is just the parsed representation of the input
; { :id 0
;   :width 10
;   :height 10
;   :sourceSeeds [ 0 17 5235 ]
;   :sourceLength 100
;   :filled [ { :x 5 :y 4 } { :x 6 :y 2 } ]
;   :units [ { :members [ { :x 1 :y 2 } { :x 0 :y 5 } ]
;              :pivot { :x 0 :y 0 } } 
;            { :members [ { :x 0 :y 1 } { :x 5 :y 2 } ]
;              :pivot { :x 0 :y 1 } } ] }
;
; A game looks like this. Notice coordinate pairs are just vectors now
; { :width 10
;   :height 10
;   :filled #{ [ 5 4 ] [ 6 2 ] }
;   :units [ { :members #{ [ 1 2 ] [ 0 5 ] }
;              :pivot [ 0 0 ] } ] }
(defn get-games
  "Returns (count (:sourceSeeds problem)) games given a problem."
  [problem]
  nil)
