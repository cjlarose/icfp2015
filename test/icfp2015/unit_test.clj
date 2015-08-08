(ns icfp2015.unit-test
  (:require [clojure.test :refer :all]
            [icfp2015.unit :refer :all]))

(deftest translation-test
  (let [{:keys [west east southwest southeast]} translations]
  (testing "translate west"
    (let [unit { :members #{ [0 0] [1 1] } :pivot [0 1] }
          f    (apply translate west)
          actual-unit (f unit)
          expected-unit { :members #{ [-1 0] [0 1] } :pivot [-1 1] }]
      (is (= expected-unit actual-unit))))

  (testing "translate east"
    (let [unit { :members #{ [0 0] [1 1] } :pivot [0 1] }
          f    (apply translate east)
          actual-unit (f unit)
          expected-unit { :members #{ [1 0] [2 1] } :pivot [1 1] }]
      (is (= expected-unit actual-unit))))

  (testing "translate southwest"
    (let [unit { :members #{ [0 0] [1 1] } :pivot [0 1] }
          f    (apply translate southwest)
          actual-unit (f unit)
          expected-unit { :members #{ [0 1] [1 2] } :pivot [0 2] }]
      (is (= expected-unit actual-unit))))

  (testing "translate southeast"
    (let [unit { :members #{ [0 0] [1 1] } :pivot [0 1] }
          f    (apply translate southeast)
          actual-unit (f unit)
          expected-unit { :members #{ [1 1] [2 2] } :pivot [1 2] }]
      (is (= expected-unit actual-unit))))))
