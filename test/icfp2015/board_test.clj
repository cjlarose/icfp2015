(ns icfp2015.board-test
  (:require [clojure.test :refer :all]
            [icfp2015.board :refer :all]))

(deftest make-board-test
  (testing "Initial state"
    (is (= 1 1))))

(deftest collision-test
  (testing "does not have collision"
    (let [board { :filled #{ [ 0 0 ] }
                  :current-unit { :members #{ [ 5 0 ] [ 6 0 ] }
                                  :pivot [ 0 0 ] } }]
      (is (false? (has-collision? board)))))
  (testing "has collision"
    (let [board { :filled #{ [ 6 0 ] }
                  :current-unit { :members #{ [ 5 0 ] [ 6 0 ] }
                                  :pivot [ 0 0 ] } }]
      (is (has-collision? board)))))

(deftest bounds-test
  (testing "in bounds"
    (let [board { :height 15
                  :width 10
                  :current-unit { :members #{ [ 5 5 ] [ 6 6 ] [ 0 0 ] [ 9 9 ] }
                                  :pivot [ 5 4 ] } }]
      (is (current-unit-in-bounds? board))))
  (testing "over east bounds"
    (let [board { :height 15
                  :width 10
                  :current-unit { :members #{ [ 5 5 ] [ 6 6 ] [ 0 0 ] [ 5 10 ] }
                                  :pivot [ 5 4 ] } }]
      (is (false? (current-unit-in-bounds? board)))))
  (testing "over south bounds"
    (let [board { :height 15
                  :width 10
                  :current-unit { :members #{ [ 5 5 ] [ 6 6 ] [ 0 0 ] [ 9 15 ] }
                                  :pivot [ 5 4 ] } }]
      (is (false? (current-unit-in-bounds? board))))))

(deftest transition-board-test
  (testing "moving southwest"
    (let [board { :width 10
                  :height 15
                  :filled #{ [ 6 6 ] }
                  :current-unit { :members #{ [ 5 0 ] } :pivot [6 0] } }
          actual-board (transition-board board [:move :southwest])
          expected-board { :width 10
                           :height 15
                           :filled #{ [ 6 6 ] }
                           :current-unit { :members #{ [ 5 1 ] } :pivot [6 1] } }]
      (is (= expected-board actual-board)))))

(deftest lock-test
  (testing "locks current unit"
    (let [board { :width 10
                  :height 15
                  :filled #{ [ 6 6 ] }
                  :current-unit { :members #{ [ 5 0 ] [ 6 5] }
                                  :pivot [ 0 0 ] } }
          expected-board { :width 10
                           :height 15
                           :filled #{ [6 6] [5 0] [6 5] }
                           :current-unit nil }
          actual-board (lock-current-unit board)]
      (is (= expected-board actual-board)))))

(deftest clear-rows-test
  (testing "no rows cleared if no full rows"
    (let [board { :width 5
                  :height 5
                  :filled #{ [0 0] [1 1] [2 2] [3 3] [4 4] } }
          actual-board (clear-rows board)
          expected-board board]
      (is (= expected-board actual-board))))
  (testing "rows cleared if full rows"
    (let [board { :width 5
                  :height 5
                  :filled #{ [4 0] [4 1] [4 2] [4 3] [4 4] } }
          actual-board (clear-rows board)
          expected-board { :width 5
                           :height 5
                           :filled #{} } ]
      (is (= expected-board actual-board))))
  (testing "rows cleared if multiple full rows"
    (let [board { :width 3
                  :height 5
                  :filled #{ [4 0] [4 1] [4 2] [1 0] [1 1] [1 2] } }
          actual-board (clear-rows board)
          expected-board { :width 3
                           :height 5
                           :filled #{} } ]
      (is (= expected-board actual-board))))
  (testing "even rows move southeast"
    (let [board { :width 3
                  :height 5
                  :filled #{ [4 0] [4 1] [4 2] [0 0] [0 2] } }
          actual-board (clear-rows board)
          expected-board { :width 3
                           :height 5
                           :filled #{ [1 0] [1 2] } } ]
      (is (= expected-board actual-board))))
  (testing "odd rows move southwest"
    (let [board { :width 3
                  :height 5
                  :filled #{ [4 0] [4 1] [4 2] [1 0] [1 2] } }
          actual-board (clear-rows board)
          expected-board { :width 3
                           :height 5
                           :filled #{ [2 0] [2 2] } } ]
      (is (= expected-board actual-board)))))

(deftest lock-and-spawn-test
  (testing "locks and spawns"
    (let [board { :width 3
                  :height 5
                  :filled #{ [4 0] [4 1] }
                  :current-unit { :members #{ [ 4 2 ] [ 3 0 ] }
                                  :pivot [3 1] } }
          new-unit { :members #{ [0 0] } :pivot [ 0 1 ] }
          actual-board (lock-and-spawn board new-unit)
          expected-board { :width 3
                           :height 5
                           :filled #{ [4 0] }
                           :current-unit new-unit }]
      (is (= expected-board actual-board)))))
