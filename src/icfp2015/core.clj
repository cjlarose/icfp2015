(ns icfp2015.core
  (:gen-class))

(defn lcg [modulus multiplier increment]
  (fn [seed]
    (let [next-item (fn [previous-item] (mod (+ (* multiplier previous-item) increment) modulus))]
      (iterate next-item seed))))

(defn source-sequence [seed]
  (let [raw-seq (lcg (bit-shift-left 1 32) 1103515245 12345)
        truncate-bits (fn [x] (bit-and (bit-shift-right x 16) 0x7FFF))]
    (map truncate-bits (raw-seq seed))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
