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
          [actual-board actual-commands] (handle-unit mock-ai board unit)
          expected-board {:width 5
                          :height 6
                          :filled #{[5 4] [5 3]}
                          :current-unit nil}
          expected-commands (repeat 6 cmd)]
      (is (= expected-board actual-board))
      (is (= expected-commands actual-commands))))
  (testing "refuses units if they cannot be spawned"
    (let [board { :width 5
                  :height 6
                  :filled #{ [ 0 2 ] }
                  :current-unit nil }
          unit { :members #{ [ 0 0 ] [ 0 1 ] }
                 :pivot [ 0 0 ] }
          cmd [:move :southeast]
          mock-ai (constantly cmd)
          [actual-board actual-commands] (handle-unit mock-ai board unit)
          expected-board board]
      (is (= expected-board actual-board))
      (is (nil? actual-commands)))))

(deftest get-commands-test
  (testing "multiple units"
    (let [game {:width 5
                :height 6
                :filled #{}
                :units [{:members #{[0 0] [0 1]}
                         :pivot   [0 0]}
                        {:members #{[0 3] [0 4]}
                         :pivot   [0 3]}]}
          cmd [:move :southeast]
          mock-ai (constantly cmd)
          actual-commands (get-commands mock-ai game)
          expected-commands (repeat 11 cmd)]
      (is (= expected-commands actual-commands)))))
