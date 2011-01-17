(deftemplate Authorization (slot subject) (slot predicate) (slot object))
(deftemplate Revoke (slot subject) (slot predicate) (slot object))
(deftemplate Attribute (slot subject) (slot name) (slot value))


(defquery authorization_check
"Search for authorizations and return an enumeration"
    (declare (variables ?X ?Y ?Z))
    (Authorization (subject ?X) (predicate ?y) (object ?Z))
)

(defquery attribute_check
"Search for attribute key value pair and return an enumeration"
    (declare (variables ?X ?Y ?Z))
    (Attribute (subject ?X) (name ?y) (value ?Z))
)

(defquery attribute_query
"Search for attributes and return an enumeration"
    (declare (variables ?X ?Y))
    (Attribute (subject ?X) (name ?y) (value ?Z))
)

(defrule fact_check
  ?f <- (Authorization (subject a) (predicate b) (object c))

  =>

  (retract ?f)
)

(assert (Authorization (subject a) (predicate b) (object c)))
(assert (Authorization (subject a) (predicate b) (object c1)))
(assert (Authorization (subject a) (predicate b) (object c2)))
(assert (Authorization (subject a) (predicate b) (object c3)))
(assert (Authorization (subject a) (predicate b) (object c4)))
(assert (Authorization (subject a) (predicate b) (object c5)))
(assert (Authorization (subject a) (predicate b) (object c6)))
(assert (Authorization (subject a) (predicate b) (object c7)))
(assert (Authorization (subject a) (predicate b) (object c8)))
(assert (Authorization (subject a) (predicate b) (object c9)))
(assert (Authorization (subject a) (predicate b) (object c0)))

(assert (Authorization (subject a) (predicate b0) (object c)))
(assert (Authorization (subject a) (predicate b0) (object c1)))
(assert (Authorization (subject a) (predicate b0) (object c2)))
(assert (Authorization (subject a) (predicate b0) (object c3)))
(assert (Authorization (subject a) (predicate b0) (object c4)))
(assert (Authorization (subject a) (predicate b0) (object c5)))
(assert (Authorization (subject a) (predicate b0) (object c6)))
(assert (Authorization (subject a) (predicate b0) (object c7)))
(assert (Authorization (subject a) (predicate b0) (object c8)))
(assert (Authorization (subject a) (predicate b0) (object c9)))
(assert (Authorization (subject a) (predicate b0) (object c0)))

(assert (Authorization (subject a) (predicate b1) (object c)))
(assert (Authorization (subject a) (predicate b1) (object c1)))
(assert (Authorization (subject a) (predicate b1) (object c2)))
(assert (Authorization (subject a) (predicate b1) (object c3)))
(assert (Authorization (subject a) (predicate b1) (object c4)))
(assert (Authorization (subject a) (predicate b1) (object c5)))
(assert (Authorization (subject a) (predicate b1) (object c6)))
(assert (Authorization (subject a) (predicate b1) (object c7)))
(assert (Authorization (subject a) (predicate b1) (object c8)))
(assert (Authorization (subject a) (predicate b1) (object c9)))
(assert (Authorization (subject a) (predicate b1) (object c0)))