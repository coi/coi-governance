package gov.agent;


import jade.core.AID;
import jade.lang.acl.ACLMessage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import jess.Context;
import jess.Deftemplate;
import jess.Fact;
import jess.Funcall;
import jess.Jesp;
import jess.JessException;
import jess.RU;
import jess.Rete;
import jess.Userfunction;
import jess.Value;
import jess.ValueVector;

public class JessWrapper {

	Rete jess; // The reference to the agent's knowledge base.
	
	String agentName = null;
    int m_maxJessPasses = 0; 
    int executedPasses = -1; 
    
    Hashtable AIDCache;
    Queue inQ = new ConcurrentLinkedQueue<ACLMessage>();
    Queue outQ = new ConcurrentLinkedQueue<Map>();

    public JessWrapper()
    {
    	jess = new Rete();
        
        try {
            jess.executeCommand(ACLJessTemplate());
            jess.executeCommand("(deftemplate MyAgent (slot name))");
            jess.addUserfunction(new JessSend(this));
            
//            jess.executeCommand("(deffacts MyAgent \"All facts about this agent\" (MyAgent (name " +
//                	agentName + ")))");
            
        } catch (JessException re) {
        	re.printStackTrace();
        }
        System.out.println("done");
    }
    
    public String getName()
    {
    	return agentName;
    }


    public JessWrapper(int maxJessPasses) {
        m_maxJessPasses = maxJessPasses;
    }
    
    public Object[] fetchInItems()
    {
    	Object[] returnArray = inQ.toArray();
    	return returnArray;
    }
   
    public Object[] fetchOutItems()
    {
    	Object[] returnArray = outQ.toArray();
    	outQ.clear();
    	return returnArray;
    }
    
    public void loadFile(String resource)
    {    	
    	try
        {
    		InputStream is = this.getClass().getClassLoader().getResourceAsStream(resource);
    		InputStreamReader isr = new InputStreamReader(is);

    		Jesp j = new Jesp(isr, jess);
        
    		j.parse(true);
        	
        }catch(Exception e)
        {

        	e.printStackTrace();
            System.out.println("clp file parse failure...");
        }
    }
    
    public void post(String msg)
    {
    	inQ.add(msg);
    	System.out.println("Message handed over to Java: "+msg);
    }

    public void executeCommand(String cmd)
    {
    	try{
    		jess.executeCommand(cmd);
    		System.out.println("Command Executed: " + cmd);
    	}catch(JessException e)
    	{	
    		e.printStackTrace();
    	}
    	
    }
    
    public void post(ACLMessage msg , boolean dummy)
    {
    	String fact = ACL2JessString(msg);
    	makeassert(fact);
    	try{
    		jess.executeCommand("(run)");
    	}catch(JessException e)
    	{	
    		e.printStackTrace();
    	}
    	
    	System.out.println("Message handed over to Java: "+msg);
    }
    
    public ACLMessage receive()
    {
    	
    	ACLMessage msg = null;
    	try
    	{
    		msg = (ACLMessage) inQ.poll();
    	}
    	catch (Exception e){
    		e.printStackTrace();
    	}

    	return msg;
    }
    
    public void send(Map msg)
    {
    	outQ.add(msg);
    }
    
    public void action() {
        ACLMessage msg; 

        // wait for a message
            
        msg = receive();
        
        if(msg != null)
        {
            // assert the fact message in Jess
            makeassert(ACL2JessString(msg));

        }

        try{

    		jess.executeCommand("(run)");
    		
    	}catch(Exception e)
    	{
    		e.printStackTrace();
    	}
       
    }

    
    private boolean isEmpty(String string) {
        return (string == null) || string.equals("");
    }

    /**
      Used to replace all the quotation marks with backslash quote
    */
    private String stringReplace(String str, char oldChar, String s) {
        int len = str.length();
        int i = 0;
        int j = 0;
        int k = 0;
        char[] val = new char[len];
        str.getChars(0, len, val, 0); // put chars into val

        char[] buf = new char[len * s.length()];

        while (i < len) {
            if (val[i] == oldChar) {
                s.getChars(0, s.length(), buf, j);
                j += s.length();
            } else {
                buf[j] = val[i];
                j++;
            }

            i++;
        }

        return new String(buf, 0, j);
    }

    /**
      * asserts a fact representing an ACLMessage in Jess. 
      */
    public void makeassert(String fact) {
        try {
            jess.executeCommand(fact);
        } catch (JessException re) {
            re.printStackTrace(System.err);
        }
    }

    
    /**
     * asserts a fact representing an ACLMessage in Jess. 
     */
   public void assertFact(String fact) {
       try {
           jess.executeCommand("(assert "+fact+" )");
       } catch (JessException re) {
           re.printStackTrace(System.err);
       }
   }

   
    /**
     * Replace all backslash quote with quote
     */
    private String unquote(String str) {
        String t1 = str.trim();

        if (t1.startsWith("\"")) {
            t1 = t1.substring(1);
        }

        if (t1.endsWith("\"")) {
            t1 = t1.substring(0, t1.length() - 1);
        }

        int len = t1.length();
        int i = 0;
        int j = 0;
        int k = 0;
        char[] val = new char[len];
        t1.getChars(0, len, val, 0); // put chars into val

        char[] buf = new char[len];

        boolean maybe = false;

        while (i < len) {
            if (maybe) {
                if (val[i] == '\"') {
                    j--;
                }

                buf[j] = val[i];
                maybe = false;
                i++;
                j++;
            } else {
                if (val[i] == '\\') {
                    maybe = true;
                }

                buf[j] = val[i];
                i++;
                j++;
            }
        }

        return new String(buf, 0, j);
    }

    private String quote(java.lang.String str) {
        //replace all chars " in \ "
        return "\"" + stringReplace(str, '"', "\\\"") + "\"";
    }


    public String ACLJessTemplate() {
        return "(deftemplate ACLMessage (slot communicative-act) (slot sender) (slot receiver) (slot conversation-id) (slot protocol) (slot content) (slot toRole) (slot fromRole)(slot type)(slot terms)(slot reply-to))";
    }

    /**
     * ACLMessage representing the passed Jess Fact. 
     */
    public ACLMessage JessFact2ACL(Context context, jess.ValueVector vv)
        throws jess.JessException {

        ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
    	
    		
        if (vv.get(1).stringValue(context) != "nil") {
        	String name = vv.get(1).stringValue(context);
        	AID sender = new AID(name,false);
            msg.setSender(sender);
        }

        if (vv.get(2).toString() != "nil") {
            String name = vv.get(2).stringValue(context);
            AID rec = new AID(name, false);
//            msg.set
        }
        
        
        if (vv.get(3).stringValue(context) != "nil") {
            msg.setConversationId(vv.get(3).stringValue(context));
        }

        if (vv.get(4).stringValue(context) != "nil") {
            msg.setProtocol(vv.get(4).stringValue(context));
        }

        if (vv.get(5).stringValue(context) != "nil") {
            msg.setContent(unquote(vv.get(5).stringValue(context)));
        }

        if (vv.get(6).stringValue(context) != "nil") {
            msg.addUserDefinedParameter("toRole",(vv.get(6).stringValue(context)));
        }

        if (vv.get(7).stringValue(context) != "nil") {
            msg.addUserDefinedParameter("fromRole",(vv.get(7).stringValue(context)));
        }
        
        if (vv.get(8).stringValue(context) != "nil") {
            msg.addUserDefinedParameter("type",(vv.get(8).stringValue(context)));
        }        

        if (vv.get(9).stringValue(context) != "nil") {
            msg.addUserDefinedParameter("terms",(vv.get(8).stringValue(context)));
        }        
        
        if (vv.get(10).stringValue(context) != "nil") {
            msg.addUserDefinedParameter("reply-to",(vv.get(8).stringValue(context)));
        }
//        msg.addUserDefinedParameter("identification_token", "98AB2C54E3F2A125");
        
        return msg;
    }

    /**
     * Jess Fact represented by the ACLMessage 
     */
    public String ACL2JessString(ACLMessage msg) {
        String fact;

        if (msg == null) {
            return "";
        }

        fact = "(assert (ACLMessage (communicative-act " +
            (msg.getPerformative());
            

        if (msg.getSender() != null) {
            fact = fact + ") (sender " + msg.getSender();
        }

        Iterator i = msg.getAllReceiver();

        if (i.hasNext()) {
            fact = fact + ") (receiver ";

            while (i.hasNext()) {
                AID aid = (AID) i.next();
                fact = fact + aid.getName();
            }
        }
        
        if (!isEmpty(msg.getConversationId())) {
            fact = fact + ") (conversation-id " + msg.getConversationId();
        }

        if (!isEmpty(msg.getProtocol())) {
            fact = fact + ") (protocol " + msg.getProtocol();
        }

        if (msg.getContent() != null) {
            fact = fact + ") (content " + quote(msg.getContent());
        }

        if (msg.getUserDefinedParameter("toRole") != null) {
            fact = fact + ") (toRole " + msg.getUserDefinedParameter("toRole");
        }
        
        if (msg.getUserDefinedParameter("fromRole") != null) {
            fact = fact + ") (fromRole " + msg.getUserDefinedParameter("fromRole");
        }
        
        if (msg.getUserDefinedParameter("type") != null) {
            fact = fact + ") (type " + msg.getUserDefinedParameter("type");
        }
        
        if (msg.getUserDefinedParameter("terms") != null) {
            fact = fact + ") (terms " + msg.getUserDefinedParameter("terms");
        }
        
        if (msg.getUserDefinedParameter("reply-to") != null) {
            fact = fact + ") (terms " + msg.getUserDefinedParameter("reply-to");
        }
        fact = fact + ")))";

        return fact;
    }

    public class JessSend implements Userfunction {
        // data
        
        JessWrapper jb;

        public JessSend( JessWrapper b) {
            jb = b;
        }

        // Returns the name by which the function is called from Jess
        public String getName() {
        	return ("send");
        }

        //Called when (send ...) is asserted from JESS
        public Value call(ValueVector vv, Context context)
            throws JessException {
        	
        	Fact fact = null;
            if (vv.get(1).type() == RU.VARIABLE) {
                fact = context.getEngine().findFactByID(vv.get(1)
                                                        .factValue(context)
                                                        .getFactId()); //JESS6.0
            }
            else if (vv.get(1).type() == RU.FUNCALL) {
                Funcall fc = vv.get(1).funcallValue(context);
                fact = fc.get(1).factValue(context);
            }

//            ACLMessage msg = jb.JessFact2ACL(context, vv);
//            Populate a map and put on Queue

            HashMap<String,String> msg = new HashMap<String, String>();
            Deftemplate deft = fact.getDeftemplate();
            
            if(deft != null)
            {
            	int nslots = deft.getNSlots();
            	String templateName = deft.getName();
            	            	
            	msg.put("factname", templateName.substring(templateName.lastIndexOf(':') +1));
            	for(int i=0; i <nslots;i++)
            	{
            		String key = deft.getSlotName(i);
            		String val = fact.get(i).stringValue(context);
            		
            		msg.put(key, val);
            	}
            }
            
            jb.send(msg);

            return Funcall.TRUE;
        }
    } 
} 
