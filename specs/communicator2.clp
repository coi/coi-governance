(assert (membership_accept_policy))

(assert (discover_policy))
(assert (subscribe_policy (topic topic-1)))
(assert (request_membership (org org@User-PC:1099/JADE)))

(run)