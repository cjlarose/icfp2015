(ns icfp2015.board-test
  (:require [clojure.test :refer :all]
            [icfp2015.board :refer :all]))

(deftest make-board-test
  (testing "Initial state"
    (is (= 1 1))))

(deftest translation-test
  (testing "move west"
    (let [unit { :members #{ [0 0] [1 1] } :pivot [0 1] }
          f    (translate -1 0)
          actual-unit (f unit)
          expected-unit { :members #{ [-1 0] [0 1] } :pivot [-1 1] }]
      (is (= expected-unit actual-unit)))))

(deftest collistion-test
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
    (let [board { :width 10
                  :height 15
                  :current-unit { :members #{ [ 5 5 ] [ 6 6 ] [ 0 0 ] [ 9 14 ] }
                                  :pivot [ 5 4 ] } }]
      (is (current-unit-in-bounds? board))))
  (testing "over east bounds"
    (let [board { :width 10
                  :height 15
                  :current-unit { :members #{ [ 5 5 ] [ 6 6 ] [ 0 0 ] [ 10 14 ] }
                                  :pivot [ 5 4 ] } }]
      (is (false? (current-unit-in-bounds? board)))))
  (testing "over south bounds"
    (let [board { :width 10
                  :height 15
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
