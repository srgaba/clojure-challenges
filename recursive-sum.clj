;https://www.codewars.com/kata/541c8630095125aba6000c00


(ns digital-root
  (:require [clojure.string :as str]))

(defn digital-root
  [n]
  (let [nstr (str n)]
    (if (>= (count nstr) 2)
      (->> (str/split nstr #"")
        (map #(Integer/parseInt %))
        (reduce +)
        recur)
      n)))
