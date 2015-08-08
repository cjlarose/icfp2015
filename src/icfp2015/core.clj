(ns icfp2015.core
  (:require [clojure.data.json :as json])
  (:gen-class))

(defn load-json
  "Parse JSON problems"
  [json]
  (json/read-str json :key-fn keyword))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (load-json (last args)))
