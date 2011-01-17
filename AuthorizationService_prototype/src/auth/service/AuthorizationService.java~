package auth.service;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import auth.engine.JessEngine;

@WebServlet("/AuthorizationService")
public class AuthorizationService{

	/*
	 * Hold a JESS object here and service requests to operate on the object.
	 * Operations: Query, Add, Revoke, Attest
	 * Data Object: Authorization Triple 
	*/
	private static final long serialVersionUID = 1L;

	private static JessEngine jess = null;
       
    public AuthorizationService() {
    	jess = JessEngine.getInstance();
    }

    public int grantAuthorization(String subject, String predicate, String object)
    {
    	return jess.grantAuthorization(subject, predicate, object);
    }
    
    public int revokeAuthorization(String subject, String predicate, String object)
    {
    	return jess.revokeAuthorization(subject, predicate, object);
    }
    
    public int attestAttribute(String key, String value)
    {
    	return jess.attestAttribute(key, value);
    
    }
    
    /*public int query()
    {
    	return jess.fetchQuery();
    	
    }*/
    
	
}
