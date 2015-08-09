(ns icfp2015.gameplay-test
  (:require [clojure.test :refer :all]
            [icfp2015.gameplay :refer :all :as gameplay]))

(deftest handle-unit-test
  (testing "handles a single unit"
    (let [board { :width 5
                  :height 6
                  :filled #{ }
                  :current-unit nil }
          unit { :members #{ [ 0 0 ] [ 0 1 ] }
                 :pivot [ 0 0 ] }
          cmd [:move :southeast]
          mock-ai (constantly cmd)
          actual-commands (handle-unit mock-ai board unit)
          expected-commands (repeat 5 cmd)]
      (is (= expected-commands actual-commands)))))
