(ns icfp2015.unit-test
  (:require [clojure.test :refer :all]
            [icfp2015.unit :refer :all]))

(deftest translation-test
  (testing "translate west"
    (let [unit { :members #{ [0 1] [1 1] } :pivot [0 1] }
          f (translate :west)
          actual-unit (f unit)
          expected-unit { :members #{ [0 0] [1 0] } :pivot [0 0] }]
      (is (= expected-unit actual-unit))))

  (testing "translate east"
    (let [unit { :members #{ [0 1] [1 1] } :pivot [0 1] }
          f (translate :east)
          actual-unit (f unit)
          expected-unit { :members #{ [0 2] [1 2] } :pivot [0 2] }]
      (is (= expected-unit actual-unit))))

  (testing "translate even row southwest"
    (let [unit { :members #{ [0 1] [0 2] } :pivot [0 1] }
          f (translate :southwest)
          actual-unit (f unit)
          expected-unit { :members #{ [1 0] [1 1] } :pivot [1 0] }]
      (is (= expected-unit actual-unit))))

  (testing "translate even row southeast"
    (let [unit { :members #{ [0 0] [0 1] } :pivot [0 1] }
          f (translate :southeast)
          actual-unit (f unit)
          expected-unit { :members #{ [1 0] [1 1] } :pivot [1 1] }]
      (is (= expected-unit actual-unit)))))
