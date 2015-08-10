(ns icfp2015.core
  (:require [clojure.data.json :as json]
            [clojure.tools.cli :refer [parse-opts]]
            [icfp2015.problem :refer [get-games]]
            [icfp2015.ai :refer [slightly-smarter-ai]]
            [icfp2015.gameplay :refer [get-commands]])
  (:gen-class))

(defn read-games-from-file [f]
  (-> f
      (slurp)
      (json/read-str :key-fn keyword)
      (get-games)))

(defn command-to-letter [command]
  (case command
    [:move :west] \p
    [:move :east] \b
    [:move :southwest] \a
    [:move :southeast] \l
    [:rotate :clockwise] \d
    [:rotate :counterclockwise] \k))

(defn solve-game [game]
  { :problemId (:problemId game)
    :seed (:seed game)
    :solution (apply str (map command-to-letter (get-commands slightly-smarter-ai game))) })

(def cli-options
  [["-f" "--file FILE" "Input filename"
    :id :input-files
    :default []
    :assoc-fn (fn [m k v] (update-in m [k] conj v))]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [args (parse-opts args cli-options)
        input-files (get-in args [:options :input-files])
        games (mapcat read-games-from-file input-files)
        solutions (map solve-game games)]
    (println (json/write-str solutions))))
