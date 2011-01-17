(assert (membership_accept_policy))
(assert (affiliate_accept_policy))

(assert (discover_policy))
(assert (register_policy))
(assert (request_membership (org org@User-PC:1099/JADE)))

(assert (publish_policy (topic topic-1)(message hi)))


(run)