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
