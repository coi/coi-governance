(deftemplate Affiliate (slot community))
(deftemplate request_membership (slot org))
(deftemplate Owns (slot member) (slot resource))
(deftemplate use_resource (slot resource ) (slot capability))


(deftemplate Member (slot community) (slot member))

(deftemplate Topic (slot name))
(deftemplate inState (slot state) (slot resource) (slot capability))
(deftemplate Allowed (slot member) (slot resource) (slot capability))


(deftemplate R_ValidTopic (slot topic ))
(deftemplate R_RegisterTopic (slot communicator) (slot distributor) (slot topic))
(deftemplate R_DeregisterTopic(slot communicator) (slot distributor) (slot topic))
(deftemplate R_Publish(slot communicator) (slot topic) (slot message))
(deftemplate R_Subscribe(slot distributor) (slot communicator) (slot topic))
(deftemplate R_Unsubscribe(slot distributor) (slot communicator) (slot topic))
(deftemplate R_Notify(slot distributor) (slot communicator) (slot topic) (slot message))


(deftemplate Authorization (slot who) (slot what))

(assert (Authorization (who this) (what CFP)))
(assert (Authorization (who this) (what ACCEPT)))
(assert (Authorization (who this) (what REJECT)))
(assert (Authorization (who this) (what DISCOVER)))
(assert (Authorization (who this) (what APPLY)))
(assert (Authorization (who this) (what REGISTER)))
(assert (Authorization (who this) (what PUBLISH)))
(assert (Authorization (who this) (what SUBSCRIBE)))


(defrule membership
?m <- (request_membership (org ?c))
(MyAgent (name ?n))
(Authorization (who this) (what CFP))
=>
(assert (ACLMessage (communicative-act CFP) (sender ?n) (receiver ?c) (conversation-id 1000) (type membership)))
(retract ?m)
)

(defrule membership_accept

?m <- (ACLMessage (communicative-act PROPOSE) (sender ?s) (receiver ?n) (conversation-id ?c) (type membership))
(MyAgent (name ?n))
(membership_accept_policy)
(Authorization (who this) (what ACCEPT))
=>

(assert (ACLMessage (communicative-act ACCEPT-PROPOSAL) (sender ?n) (receiver ?s) (conversation-id ?c) (fromRole distributor) (type membership)))
(assert (Member (community ?s) (member ?n)))
(retract ?m)
)

(defrule membership_reject
?m <- (ACLMessage (communicative-act PROPOSE) (sender ?s) (receiver ?n) (conversation-id ?c) (type membership))
(membership_reject_policy)
(Authorization (who this) (what REJECT))

=>

(assert (ACLMessage (communicative-act REJECT-PROPOSAL) (sender ?n) (receiver ?s) (conversation-id ?c) (type membership) ))
(retract ?m)
)

(defrule apply

	(Member (member ?member) (community ?community))
	(MyAgent (name ?n))	
	(use_resource (resource  ?r))
	(Authorization (who this) (what APPLY))
	=>
	(assert (ACLMessage (communicative-act REQUEST) (sender ?n) (receiver org@User-PC:1099/JADE) (conversation-id 1002) (content ?r) (type resource)))
	
)


(defrule discover_request
	(MyAgent (name ?n))	
	?m <- (discover_policy)
	(Member (community ?community)(member ?n))
	(Authorization (who this) (what DISCOVER))
	=>
	
	(assert (ACLMessage (communicative-act REQUEST) (sender ?n) (receiver ?community) (conversation-id 1010) (type discover)))
	(retract ?m)
)


(defrule register
	(MyAgent (name ?n))	
	?m <- (ACLMessage (communicative-act REQUEST) (sender ?s) (receiver ?n) (conversation-id ?con) (content ?content) (type register))
	(Authorization (who this) (what REGISTER))	
	=>
	(assert (R_RegisterTopic (communicator ?s) (distributor ?n) (topic ?content)))
	(assert (ACLMessage (communicative-act INFORM) (sender ?n) (receiver ?s) (conversation-id ?con) (type register)))
	(retract ?m)
)

( defrule subscribe
(MyAgent (name ?n))
?m <- (ACLMessage (communicative-act SUBSCRIBE) (sender ?s) (receiver ?n) (conversation-id ?con) (content ?content) (type subscribe))
(Authorization (who this) (what SUBSCRIBE))
=>

(assert (R_Subscribe(distributor ?n) (communicator ?s) (topic ?content)))
(retract ?m)
)

(defrule publish
(MyAgent (name ?n))
?m <- (ACLMessage (communicative-act INFORM) (sender ?s) (receiver ?n) (conversation-id ?con) (content ?content) (type publish))
(R_Subscribe (distributor ?n) (communicator ?p) (topic ?content))
(Authorization (who this) (what PUBLISH))

=>

(assert (R_Publish (communicator ?s) (topic ?content) (message message))) 
(assert (ACLMessage (communicative-act INFORM) (sender ?n) (receiver ?p) (conversation-id 1020) (type notify)))
(assert (R_Notify (communicator ?s) (distributor ?n) (topic ?content) (message message))) 
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