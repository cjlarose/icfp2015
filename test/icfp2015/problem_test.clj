(ns icfp2015.problem-test
  (:require [clojure.test :refer :all]
            [icfp2015.problem :refer :all]))

(deftest lcg-test
  (testing "Correct sequence"
    (let [f (lcg (bit-shift-left 1 32) 1103515245 12345)]
      (is (= [17 1579902326 3232257911 794669028 2765327437] (take 5 (f 17)))))))

(deftest source-seq-test
  (testing "Correct sequence"
    (is (= [0 24107 16552 12125 9427 13152 21440 3383 6873 16117] (take 10 (source-sequence 17))))))

(deftest get-games-test
  (testing "gets correct games"
    (let [problem { :id 124
                    :width 10
                    :height 15
                    :sourceSeeds [ 0 17 5235 ]
                    :sourceLength 5
                    :filled [ { :x 5 :y 4 } { :x 6 :y 2 } ]
                    :units [ { :members [ { :x 1 :y 2 } { :x 0 :y 5 } ]
                               :pivot { :x 0 :y 0 } } 
                             { :members [ { :x 0 :y 1 } { :x 5 :y 2 } ]
                               :pivot { :x 0 :y 1 } } 
                             { :members [ { :x 11 :y 6 } { :x 1 :y 4 } ]
                               :pivot { :x 6 :y 1 } } 
                             { :members [ { :x 4 :y 7 } { :x 11 :y 8 } ]
                               :pivot { :x 4 :y 2 } } ] }
          actual-games (get-games problem)
          expected-games [ { :width 10
                             :height 15
                             :problemId 124
                             :seed 0
                             :filled #{[4 5] [2 6]}
                             :units '({ :members #{ [5 0] [2  1] } :pivot [0 0] }
                                      { :members #{ [5 0] [2  1] } :pivot [0 0] }
                                      { :members #{ [5 0] [2  1] } :pivot [0 0] }
                                      { :members #{ [5 0] [2  1] } :pivot [0 0] }
                                      { :members #{ [2 5] [1  0] } :pivot [1 0] }) }
                           { :width 10
                             :height 15
                             :problemId 124
                             :seed 17
                             :filled #{[4 5] [2 6]}
                             :units '({ :members #{ [5 0] [2 1 ] } :pivot [0 0] }
                                      { :members #{ [7 4] [8 11] } :pivot [2 4] }
                                      { :members #{ [5 0] [2 1 ] } :pivot [0 0] }
                                      { :members #{ [2 5] [1 0 ] } :pivot [1 0] }
                                      { :members #{ [7 4] [8 11] } :pivot [2 4] }) }
                           { :width 10
                             :height 15
                             :problemId 124
                             :seed 5235
                             :filled #{[4 5] [2 6]}
                             :units '({ :members #{ [5 0] [2 1 ] } :pivot [0 0] }
                                      { :members #{ [2 5] [1 0 ] } :pivot [1 0] }
                                      { :members #{ [7 4] [8 11] } :pivot [2 4] }
                                      { :members #{ [4 1] [6 11] } :pivot [1 6] }
                                      { :members #{ [2 5] [1 0 ] } :pivot [1 0] }) } ]]
      (is (= expected-games actual-games)))))
