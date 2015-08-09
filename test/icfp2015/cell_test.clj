(ns icfp2015.cell-test
  (:require [clojure.test :refer :all]
            [icfp2015.cell :refer :all]))

(deftest offset->cube-test
  (testing "convert origin cell"
    (let [cell [0 0]
          actual-cell (offset->cube cell)
          expected-cell [0 0 0]]
      (is (= expected-cell actual-cell))))

  (testing "convert [1 1] cell"
    (let [cell [1 1]
          actual-cell (offset->cube cell)
          expected-cell [1 -2 1]]
      (is (= expected-cell actual-cell))))

  (testing "convert some cell"
    (let [cell [4 -3]
          actual-cell (offset->cube cell)
          expected-cell [-5 1 4]]
      (is (= expected-cell actual-cell)))))

(deftest cube->offset-test
  (testing "convert origin cell"
    (let [cell [0 0 0]
          actual-cell (cube->offset cell)
          expected-cell [0 0]]
      (is (= expected-cell actual-cell))))

  (testing "convert [1 1 -2] cell"
    (let [cell [1 1 -2]
          actual-cell (cube->offset cell)
          expected-cell [-2 0]]
      (is (= expected-cell actual-cell))))

  (testing "convert some cell"
    (let [cell [-1 5 -2]
          actual-cell (cube->offset cell)
          expected-cell [-2 -2]]
      (is (= expected-cell actual-cell)))))

