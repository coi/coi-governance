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
(assert (Authorization (who this) (what APPLY)))
(assert (Authorization (who this) (what REGISTER)))
(assert (Authorization (who this) (what PUBLISH)))
(assert (Authorization (who this) (what SUBSCRIBE)))

(defrule membership
(MyAgent (name ?n))
?m <- (request_membership (org ?c))
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

(assert (ACLMessage (communicative-act ACCEPT-PROPOSAL) (sender ?n) (receiver ?s) (conversation-id ?c) (fromRole communicator) (type membership)))
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


(defrule applyResource

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
	
	=>
	
	(assert (ACLMessage (communicative-act REQUEST) (sender ?n) (receiver ?community) (conversation-id 1010) (type discover)))
	(retract ?m)
)


(defrule register_topic
  (MyAgent (name ?n))
  (Member (community ?community)(member ?n))
  (register_policy)
  (Authorization (who this) (what REGISTER))
  ?m <- (ACLMessage (communicative-act INFORM)(sender ?s)
		    (receiver ?n) (conversation-id ?conv)
		    (content ?distributor) (type discover)) 
  =>
  (assert (Member (community ?community) (role distributor)
		  (member ?distributor)))
  (assert (ACLMessage (communicative-act REQUEST) (sender ?n)
		      (receiver ?distributor) (conversation-id 1011)
		      (content topic-1)(type register)))
  (retract ?m)
  )

(defrule publish_topic
  (MyAgent (name ?n))
  (publish_policy (topic ?topic)(message ?message))
  (Authorization (who this) (what PUBLISH))
  ?m <- (ACLMessage (communicative-act INFORM) (sender ?s)
		    (receiver ?n) (conversation-id ?con)(type register))
  =>
  (assert (ACLMessage (communicative-act INFORM) (sender ?n)
		      (receiver ?s) (conversation-id 1013)
		      (content ?topic)(type publish)))
  )

(defrule subscribe_topic
  (MyAgent (name ?n))
  (subscribe_policy (topic ?topic))
  (Authorization (who this) (what SUBSCRIBE))
  ?m <- (ACLMessage (communicative-act INFORM)(sender ?s)
		    (receiver ?n) (conversation-id ?conv)
		    (content ?distributor) (type discover))
  =>
  (assert (ACLMessage (communicative-act SUBSCRIBE) (sender ?n)
		      (receiver ?distributor) (conversation-id 1014)
		      (content ?topic)(type subscribe)))
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