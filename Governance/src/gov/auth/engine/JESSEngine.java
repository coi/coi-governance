package gov.auth.engine;

import gov.auth.data.Status;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import jess.Jesp;
import jess.JessException;
import jess.QueryResult;
import jess.Rete;
import jess.ValueVector;

public class JESSEngine {

	//Engine instance
	private String org_id = new String();
	private Rete engine = null;
	private JESSEngine jess = null;
	
	public JESSEngine()
	{
		engine = new Rete();
		loadFile("org.clp");
		
	}
	
	public int setOrg_id(String org_id)
	{
		if(org_id != null && !org_id.isEmpty())
		{
			this.org_id = org_id;
			return Status.SUCCESS;
		}

		return Status.FAILURE;
	}
	
	public void loadFile(String resource)
	{    
		try
		{
//			InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource);
//			InputStreamReader isr = new InputStreamReader(is);
			File file = new File(resource);
			FileReader fr = new FileReader(file);
			Jesp j = new Jesp(fr, engine);

			j.parse(true);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}	
	
	public List fetchQuery(String query, HashMap<String, String> params)
	{
		/*
		 * Querying is changing in the clp files,
		 * so it may change here. The idea is to keep querying generic and not 
		 * tied down to precompiled queries. This may be possible by smart query 
		 * design (??). 
		*/ 
		QueryResult result;
		ArrayList<String> resList = new ArrayList<String>();
		try {
			result = engine.runQueryStar(query, new ValueVector().add("Smith"));
			while (result.next()) {
	            System.out.println(result.getString("fn") + " " + result.getString("ln")
	                               + ", age" + result.getInt("age"));
			
	        }
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Return an array or some collection 
        return null;
	}
	
	public int grantAuthorization(String subject, String predicate, String object)
	{
		String authorization = new String("(Authorization "+ 
									"(subject " +subject.trim()+") "+
									"(predicate "+ predicate.trim()+") "+
									"(object "+ object.trim()+"))");
		
		try {
			engine.assertString(authorization);
		} catch (JessException e) {
			e.printStackTrace();
			return Status.FAILURE;
		}
		
		return Status.SUCCESS;
	}
	
	public int revokeAuthorization(String subject, String predicate, String object)
	{
		String authorization = new String("(revoke (Authorization "+ 
									"(subject " +subject.trim()+") "+
									"(predicate "+ predicate.trim()+") "+
									"(object "+ object.trim()+")))");
		
		try {
			engine.assertString(authorization);
		} catch (JessException e) {
			return Status.FAILURE;
		}
		
		return Status.SUCCESS;
	}
	
	public int attestAttribute(String subject, String key, String value )
	{
		//Here check for the validity of the attribute.
		//Should Org-level sharing be enabled(??)
		System.out.println("Received request for key:"+key+" value:"+ value+"about subject:"+subject);
		QueryResult result;
		ValueVector vector = new ValueVector();
		
		try {
			vector.add(subject);
			vector.add(key);
			vector.add(value);
			
			result = engine.runQueryStar("attribute_check", vector);
			while(result.next())
			{
				return Status.SUCCESS;
			}
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return Status.FAILURE;
	}

	public int checkAuthorization(String subject, String predicate,
			String object) {
		// Checks the JESS instance for a recorded authorization and responds accordingly.
		QueryResult result;
		ValueVector vector = new ValueVector();
		
		try {
			vector.add(subject);
			vector.add(predicate);
			vector.add(object);
			
			result = engine.runQueryStar("authorization_check", vector);
			while(result.next())
			{
				return Status.SUCCESS;
			}
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return Status.FAILURE;
	}

	public int addAttribute(String subject, String key, String value) {
		String authorization = new String("(assert (Attribute "+ 
				"(subject " +subject.trim()+") "+
				"(key "+ key.trim()+") "+
				"(value "+ value.trim()+")))");

		try {
			engine.assertString(authorization);
		} catch (JessException e) {
			e.printStackTrace();
			return Status.FAILURE;
		}

		return Status.SUCCESS;
	}

}