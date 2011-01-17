(deftemplate S_Member (slot community) (slot role) (slot member))

(deftemplate S_Authorization (slot who) (slot what))

(assert (S_Authorization (who this) (what PROPOSE)))
(assert (S_Authorization (who this) (what INFORM)))
(assert (S_Authorization (who this) (what REJECT)))
(assert (S_Authorization (who this) (what DISCOVER)))
(assert (S_Authorization (who this) (what SANCTION)))


(defrule membership_propose
  (MyAgent (name ?n))
  ?m <- (ACLMessage (communicative-act PROPOSE) (sender ?s) (receiver ?n)
		    (conversation-id ?c) (type membership)) 
  (S_Authorization (who this) (what PROPOSE))

  =>

  (assert (ACLMessage (communicative-act PROPOSE) (sender ?n)
		       (receiver ?s) (conversation-id ?c)
		       (type membership))) 
  (retract ?m)
  )

(defrule membership_accept
  (MyAgent (name ?n))
  ?m <- (ACLMessage (communicative-act ACCEPT-PROPOSAL) (sender ?s)
		    (receiver ?n) (conversation-id ?c) (fromRole ?r)
		    (type membership)) 
  (S_Authorization (who this) (what INFORM))

  =>

  (assert (ACLMessage (communicative-act INFORM) (sender ?n) (receiver ?s)
		      (conversation-id ?c) (type membership))) 
  (assert (S_Member (community ?n) (role ?r) (member ?s)))
  (retract ?m)
  )

(defrule membership_reject
  (MyAgent (name ?n))
  ?m <- (ACLMessage (communicative-act REJECT-PROPOSAL) (sender ?s)
		    (receiver ?n) (conversation-id ?c) (type membership))
  (S_Authorization (who this) (what INFORM))

  =>

  (assert (ACLMessage (communicative-act INFORM) (sender ?n)
		       (receiver ?s) (conversation-id ?c)))
  (retract ?m)
  )

(defrule sanction
  (MyAgent (name ?n))
  ?m <- (ACLMessage (communicative-act PROPAGATE) (sender ?s) (receiver ?n)
		    (conversation-id ?c) (content ?content) (reply-to ?r)) 
  (not (S_Member (community OOI) (member ?r)))
  (S_Authorization (who this) (what SANCTION))

  =>

  (assert (ACLMessage (communicative-act CANCEL) (sender ?n)
		      (receiver ?content) (conversation-id ?c)
		      (type sanction)))  
  (retract ?m)
  )

(defrule sanction_2
  (MyAgent (name ?n))
  ?m <- (ACLMessage (communicative-act PROPAGATE) (sender ?s) (receiver ?n)
		    (conversation-id ?c) (content ?content) (reply-to ?r))
  (not (S_Member (community OOI) (member ?content)))
  (S_Authorization (who this) (what SANCTION))

  =>

  (assert (ACLMessage (communicative-act CANCEL) (sender ?n) (receiver ?r)
		      (conversation-id ?c) (type sanction)))

  (retract ?m)

  )

(defrule discover_response
  (MyAgent (name ?n))
  ?m <- (ACLMessage (communicative-act REQUEST) (sender ?s)
		    (receiver ?n) (conversation-id ?con) (type discover))
  (S_Authorization (who this) (what DISCOVER))

  =>

  (assert (ACLMessage (communicative-act INFORM) (sender ?n)
		      (receiver ?s) (conversation-id ?con)
		      (content dist@User-PC:1099/JADE) (type discover)))
  (retract ?m)
  )

(defrule send-a-message
  (MyAgent (name ?n))
  ?m <- (ACLMessage (sender ?n))

  =>

  (send ?m)
  (retract ?m)
  )

(watch all)

(reset)

(run)