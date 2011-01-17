package auth.engine;

import jess.*;

import java.util.Iterator;

public class JessEngine {

	//Engine instance
	private static Rete engine = null;
	private static JessEngine jess = null;
	
	public static int success = 1;
	public static int failure = 0;

	private JessEngine()
	{
		engine = new Rete();
	}
	
	public static JessEngine getInstance()
	{
		if(jess == null){
			jess = new JessEngine();
		}
		
		return jess;
		
	}
	
	public int fetchQuery()
	{
		QueryResult result;
		try {
			result = engine.runQueryStar("search-by-name", new ValueVector().add("Smith"));
			while (result.next()) {
	            System.out.println(result.getString("fn") + " " + result.getString("ln")
	                               + ", age" + result.getInt("age"));
			
	        }
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// TODO Return an array or some collection 
        return 1;
	}
	
	public int grantAuthorization(String subject, String predicate, String object)
	{
		String authorization = new String("(assert (Authorization "+ 
									"(subject " +subject.trim()+") "+
									"(predicate "+ predicate.trim()+") "+
									"(object "+ object.trim()+")))");
		
		try {
			JessEngine.engine.assertString(authorization);
		} catch (JessException e) {
			return failure;
		}
		
		return success;
	}
	
	public int revokeAuthorization(String subject, String predicate, String object)
	{
		String authorization = new String("(revoke (Authorization "+ 
									"(subject " +subject.trim()+") "+
									"(predicate "+ predicate.trim()+") "+
									"(object "+ object.trim()+")))");
		
		try {
			JessEngine.engine.assertString(authorization);
		} catch (JessException e) {
			return failure;
		}
		
		return success;
	}
	
	public int attestAttribute(String key, String value)
	{
		//Here check for the validity of the attribute.
		System.out.println("Received request for key:"+key+" value:"+ value);
		return success;
	}
}