(defn new-cache
  [limit]
  {:cache {}
   :precedence []
   :limit limit
   :counter 0})

(defn put-cache
  [cache-data
   key
   value]
  (if (= (:counter cache-data) (:limit cache-data))
    (let [precedence (:precedence cache-data)
          low-priority-element (first precedence)
          new-precedence (as-> (drop 1 precedence) prec-data
                           (into [] prec-data)
                           (conj prec-data key))
          cache (:cache cache-data)
          new-cache (-> cache (dissoc low-priority-element)
                        (assoc key value))]
      (-> cache-data
          (assoc :cache new-cache)
          (assoc :precedence new-precedence)))
    (-> cache-data
        (assoc-in [:cache key] value)
        (assoc :precedence (conj (:precedence cache-data) key))
        (assoc :counter (inc (:counter cache-data))))))

(defn get-cache
  [cache
   key]
  (let [cache-data @cache]
    (when-let [value (get-in cache-data [:cache key])]
      (let [new-precedence (as-> (:precedence cache-data) prec-data
                             (remove #(= key %) prec-data)
                             (into [] prec-data)
                             (conj prec-data key))
            new-cachedata (-> cache-data (assoc :precedence new-precedence))]
        {:new-cachedata new-cachedata
         :value value}))))

(defn cache-operation
  []
  (let [cache (atom (new-cache 3))]
    (println cache)
    (swap! cache put-cache :a 1)
    (swap! cache put-cache :b 2)
    (swap! cache put-cache :c 3)
    (let [{:keys [value new-cachedata]} (get-cache :a cache)]
      (println "get" value)
      (reset! cache new-cachedata))
    (swap! cache put-cache :d 4)
    (println cache)))