(ns icfp2015.board-test
  (:require [clojure.test :refer :all]
            [icfp2015.board :refer :all]))

(deftest make-board-test
  (testing "Tnitial state"
    (is (= 1 1))))
