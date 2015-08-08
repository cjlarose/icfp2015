(ns icfp2015.core
  (:gen-class))

(defn lcg [modulus multiplier increment]
  (fn [seed]
    (let [next-item (fn [previous-item] (mod (+ (* multiplier previous-item) increment) modulus))]
      (iterate next-item seed))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
