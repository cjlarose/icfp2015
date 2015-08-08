(ns icfp2015.problem)

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
; a game looks like this
; { :width 10
;   :height 10
;   :filled [ { :x 5 :y 4 } { :x 6 :y 2 } ]
;   :units [ { :members [ { :x 1 :y 2 } { :x 0 :y 5 } ]
;              :pivot { :x 0 :y 0 } } ] }
(defn get-games
  "Returns (count (:sourceSeeds problem)) games given a problem."
  [problem]
  nil)
