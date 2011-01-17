package gov.test;


import gov.auth.engine.JESSEngine;
import gov.auth.service.AuthorizationService;

import java.io.File;
import java.io.FileReader;
import java.util.Iterator;

import jess.Fact;
import jess.Jesp;
import jess.JessException;
import jess.Rete;
import jess.Value;

public class Tester {

	String org_id = new String("ooi");
	String subject= new String("mem1");
	String predicate = new String("read");
	String object = new String("res1");
	String key = new String("role");
	String value = new String("member");
	
	public static void main(String[] args)
	{
//		Tester test = new Tester();
//		test.test1();
		
		Rete r = new Rete();

		File f = new File("org.clp");		
		try{
		FileReader fr = new FileReader(f);
		Jesp jp = new Jesp(fr, r);
		
		jp.parse(true);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		try {
			Fact fact = new Fact("Authorization", r);
			Value va = new Value("a1");
			fact.setSlotValue("subject", va);
			Value vb = new Value("b");
			fact.setSlotValue("predicate", vb);
			Value vc = new Value("c");
			fact.setSlotValue("object", vc);
			
			r.assertFact(fact);
			System.out.println("added");
			Iterator it = r.listFacts();
			int i = 1;
			while(it.hasNext())
			{	
				System.out.println("Fact : "+i);
				it.next();
				i++;
			}
			r.retractString("(Authorization (subject a1)(object c)(predicate b))");
			System.out.println("retracted");
			it = r.listFacts();
			i = 1;
			while(it.hasNext())
			{	
				System.out.println("Fact : "+i);
				it.next();
				i++;
			}
			
			
			Fact fact1 = new Fact("Authorization", r);
			Value va1 = new Value('a');
			fact1.setSlotValue("subject", va1);
			Value vb1 = new Value('b');
			fact1.setSlotValue("predicate", vb1);
			Value vc1 = new Value('c');
			fact1.setSlotValue("object", vc1);
			
//			r.retract(fact1);
			r.eval("(run)");
		} catch (JessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		try {
////			r.assertString("(Authorization (subject a)(predicate p )(object o))");
//			
//			
//		} catch (JessException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public  void test1()
	{
		JESSEngine eng = new JESSEngine();
		int res = eng.grantAuthorization(subject, predicate, object);
		System.out.println("Grant res = 1");
	}
	
	public void test()
	{
		System.out.println("Start");
		
		AuthorizationService service = new AuthorizationService();
		
		
		int res = service.registerOrg(org_id);
		System.out.println("Register result : "+ res);
		res = service.addAttribute(org_id, subject, key, value);
		System.out.println("Add attribute result: "+ res);
		res = service.attestAttribute(org_id, subject, key, value);
		System.out.println("Attest res:"+ res);
		res = service.grantAuthorization(org_id, subject, predicate, object);
		System.out.println("grant res: "+ res);
		res = service.checkAuthorization(org_id, subject, predicate, object);
		System.out.println("check res:"+ res);
		
	}
}
