package gov.auth.service;

import gov.auth.data.Status;
import gov.auth.engine.JESSEngine;
import gov.auth.internal.JESSRegistry;

public class AuthorizationService{

	/*
	 * This is presented as the facade to the rest of the system.
	 * It uses the JESSRegistry to store objects 
	 * Operations: Query, Add, Revoke, Attest
	 * Data Object: Authorization Triple 
	 */
	private static final long serialVersionUID = 1L;
	
	public AuthorizationService() {
    	
    }

    public int grantAuthorization(String org_id, String subject, String predicate, String object)
    {
    	JESSEngine jess = null;
    	jess = JESSRegistry.getEngineForOrg(org_id);
    	if(jess == null)
    		System.out.println("instance is null");
    	
    	return jess.grantAuthorization(subject, predicate, object);
    }
    
    public int revokeAuthorization(String org_id, String subject, String predicate, String object)
    {
    	JESSEngine jess = null;
    	jess = JESSRegistry.getEngineForOrg(org_id);
    	if(jess == null)
    		System.out.println("instance is null");
    	
    	return jess.revokeAuthorization(subject, predicate, object);
    }
    
    public int attestAttribute(String org_id, String subject_id, String key, String value)
    {
    	JESSEngine jess = null;
    	jess = JESSRegistry.getEngineForOrg(org_id);
    	if(jess == null)
    		System.out.println("instance is null");
    	
    	return jess.attestAttribute(subject_id, key, value);
    }
    
    public int addAttribute(String org_id, String subject_id, String key, String value)
    {
    	JESSEngine jess = null;
    	jess = JESSRegistry.getEngineForOrg(org_id);
    	if(jess == null)
    		System.out.println("instance is null");
    	
    	return jess.addAttribute(subject_id, key, value);
    	
    }
    
    public int checkAuthorization(String org_id, String subject, String predicate, String object)
    {
    	JESSEngine jess = null;
    	jess = JESSRegistry.getEngineForOrg(org_id);
    	if(jess == null)
    		System.out.println("instance is null");
    	
    	return jess.checkAuthorization(subject, predicate, object);
    }
    
    public int registerOrg(String org_id)
    {
    	try {
			JESSRegistry.createEngineForOrg(org_id);
			return Status.SUCCESS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Status.FAILURE;
		}
    }
    
    public int deregisterOrg(String org_id)
    {
    	//design choices remaining
    	
    	try {
			JESSRegistry.removeEngineForOrg(org_id);
			return Status.SUCCESS;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Status.FAILURE;
		}
    }
    
    
    
    
}
