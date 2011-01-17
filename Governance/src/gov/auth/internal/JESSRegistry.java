package gov.auth.internal;

import gov.auth.data.Status;
import gov.auth.engine.JESSEngine;

import java.util.HashMap;
/*
 * This registry maintains the JESS objects for each Org.
 * The JESS instances are wrapped as JESSEngine.
 * */
public class JESSRegistry {

	private static JESSRegistry registry = null;
	private static HashMap<String, JESSEngine> jess_store = null;
	
	//?? auth token needed for authorizing an entire conversation
	private static HashMap<String, String> token_store = null;  
	
	static
	{
		jess_store = new HashMap<String, JESSEngine>();
	}

	public static JESSRegistry getInstance()
	{
		if(registry == null)
		{
			registry = new JESSRegistry();
		}

		return registry;
	}

	public static int createEngineForOrg(String org_id)
	{
		JESSEngine eng = new JESSEngine();
		eng.setOrg_id(org_id);
		putEngineForOrg(org_id, eng);
		return Status.SUCCESS;
	}
	
	public static int removeEngineForOrg(String org_id)
	{
		try{
		jess_store.remove(org_id);
		return Status.SUCCESS;
		}catch(Exception e)
		{
			return Status.FAILURE;
		}
		
	}
	
	public static JESSEngine getEngineForOrg(String org_id)
	{
		if (org_id != null && !org_id.isEmpty())
		{
			return jess_store.get(org_id);
		}
		
		// if org_id is empty/null return null
		return null;
	}

	public static int putEngineForOrg(String org_id, JESSEngine eng)
	{
		if (org_id != null && !org_id.isEmpty() && eng!= null)
		{
			jess_store.put(org_id, eng);
			return Status.SUCCESS;
		}
		
		// if org_id is empty/null return 0
		return Status.FAILURE;
	}
	
	public static String getTokenForOrg(String org_id)
	{
		if (org_id != null && !org_id.isEmpty())
		{
			return token_store.get(org_id);
		}
		
		// if org_id is empty/null return null
		return null;
	}

	public static int putTokenForOrg(String org_id, String token)
	{
		if (org_id != null && !org_id.isEmpty() && token!= null && !token.isEmpty())
		{
			token_store.put(org_id, token);
			return Status.SUCCESS;
		}
		
		// if org_id is empty/null return 0
		return Status.FAILURE;
	}	

}
