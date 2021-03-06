(ns structured-data)

(defn do-a-thing [x]
  (let [xx (+ x x)]
    (Math/pow xx xx)))

(defn spiff [v]
  (+ (get v 0) (get v 2)))

(defn cutify [v]
  (conj v "<3"))

(defn spiff-destructuring [v]
  (let [[x _ y] v]
    (+ x y)))

(defn point [x y]
  [x y])

(defn rectangle [bottom-left top-right]
  [bottom-left top-right])

(defn width [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (- x2 x1)))

(defn height [rectangle]
  (let [[[x1 y1] [x2 y2]] rectangle]
    (- y2 y1)))

(defn square? [rectangle]
  (= (width rectangle) (height rectangle)))

(defn area [rectangle]
  (* (width rectangle) (height rectangle)))

(defn contains-point? [rectangle point]
  (let [[[x1 y1] [x2 y2]] rectangle [px py] point]
    (if (and (<= x1 px x2) (<= y1 py y2)) true false)))

(defn contains-rectangle? [outer inner]
  (let [[[x1 y1] [x2 y2]] inner]
    (if 
      (and (contains-point? outer [x1 y1]) (contains-point? outer [x2 y2]))
      true
      false)))

(defn title-length [book]
  (count (:title book)))

(defn author-count [book]
  (count (:authors book)))

(defn multiple-authors? [book]
  (not (= (author-count book) 1)))

(defn add-author [book new-author]
  (let [authors (:authors book)]
    (if 
      (nil? authors)
      (assoc book :authors [new-author])
      (assoc book :authors (conj authors new-author))
      )))

(defn alive? [author]
  (not (contains? author :death-year)))

(defn element-lengths [collection]
  (map count collection))

(defn second-elements [collection]
  (let [sec (fn [col] (get col 1))]
    (map sec collection)))

(defn titles [books]
  (map :title books))

(defn monotonic? [a-seq]
  (cond
    (apply <= a-seq) true
    (apply >= a-seq) true
    :else false))

(defn stars [n]
  (apply str (repeat n "*")))

(defn toggle [a-set elem]
  (if
    (contains? a-set elem)
    (disj a-set elem)
    (conj a-set elem)))

(defn contains-duplicates? [a-seq]
  (not (=
        (count (set a-seq))
        (count a-seq))))

(defn old-book->new-book [book]
  (assoc book :authors (set (:authors book))))

(defn has-author? [book author]
  (contains? (:authors book) author))

(defn authors [books]
  (apply clojure.set/union (map :authors books)))

(defn all-author-names [books]
  (set (map :name (authors books))))

(defn author->string [author]
  (let [has-birth (contains? author :birth-year)
        has-death (contains? author :death-year)]
    (str (:name author)
         (if
           has-birth
           (str " ("
                (:birth-year author)
                " - "
                (if has-death
                  (str (:death-year author) ")")
                  ")"))
           ""))))

(defn authors->string [authors]
  (apply str (interpose
               ", "
               (map author->string authors))))

(defn book->string [book]
  (str
    (:title book)
    ", written by "
    (authors->string (:authors book))))

(defn books->string [books]
  (cond
    (= 1 (count books)) 
      (str "1 book. " (book->string (first books)) ".")
    (< 1 (count books)) 
      (str (count books) 
           " books. " 
           (apply str (interpose ", " (map book->string books)))
           ".")
    :else "No books."))

  (defn books-by-author [author books]
    (filter (fn [x] (if (contains? (:authors x) author) true false)) books))

  (defn author-by-name [name authors]
    (first (filter (fn [author] (= name (:name author))) authors)))

  (defn living-authors [authors]
    (filter alive? authors))

  (defn has-a-living-author? [book]
    (not (empty? 
      (living-authors 
        (:authors book)))))

  (defn books-by-living-authors [books]
    (filter has-a-living-author? books))

  ; %________%
