(deftemplate Authorization (slot subject) (slot predicate) (slot object))

(defquery policy_check
"Search for a pattern and return an enumeration"
    (declare (variables ?X ?Z))
    (Authorization (subject ?Y) (predicate ?X) (object ?Z) )
)

(assert (Authorization  (subject a) (predicate b)(object c)))
(assert (Authorization  (subject a) (predicate b)(object c1)))
(assert (Authorization  (subject a) (predicate b)(object c2)))
(assert (Authorization  (subject a) (predicate b)(object c3)))
(assert (Authorization  (subject a) (predicate b)(object c4)))
(assert (Authorization  (subject a) (predicate b)(object c5)))
(assert (Authorization  (subject a) (predicate b)(object c6)))
(assert (Authorization  (subject a) (predicate b)(object c7)))
(assert (Authorization  (subject a) (predicate b)(object c8)))
(assert (Authorization  (subject a) (predicate b)(object c9)))
(assert (Authorization  (subject a) (predicate b)(object c0)))

(assert (Authorization  (subject a0) (predicate b)(object c)))
(assert (Authorization  (subject a9) (predicate b)(object c)))
(assert (Authorization  (subject a8) (predicate b)(object c)))
(assert (Authorization  (subject a7) (predicate b)(object c)))
(assert (Authorization  (subject a6) (predicate b)(object c)))
(assert (Authorization  (subject a5) (predicate b)(object c)))
(assert (Authorization  (subject a4) (predicate b)(object c)))
(assert (Authorization  (subject a3) (predicate b)(object c)))
(assert (Authorization  (subject a2) (predicate b)(object c)))
(assert (Authorization  (subject a1) (predicate b)(object c)))


