(ns icfp2015.core-test
  (:require [clojure.test :refer :all]
            [icfp2015.core :refer :all]))

(deftest lcg-test
  (testing "Correct sequence"
    (let [f (lcg (bit-shift-left 1 32) 1103515245 12345)]
      (is (= [17 1579902326 3232257911 794669028 2765327437] (take 5 (f 17)))))))
