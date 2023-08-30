(defn sum-is-equal-to-target?
  [x y target numbers]
  (= (+ (numbers x) (numbers y)) target))

(defn two-sum [numbers target]
  (let [limit (- (count numbers) 1)]
    (loop [x 0
           y 1]
      (when-not (>= x limit)
        (if (sum-is-equal-to-target? x y target numbers)
          [x y]
          (if (< y limit)
            (recur x (inc y))
            (recur (inc x) (+ x 2))))))))

(two-sum [1 2 3 4 5] 9)
