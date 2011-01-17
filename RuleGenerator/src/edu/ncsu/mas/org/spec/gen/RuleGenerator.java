package edu.ncsu.mas.org.spec.gen;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import edu.ncsu.mas.org.spec.gen.classes.AuthorizationType;
import edu.ncsu.mas.org.spec.gen.classes.CommitmentType;
import edu.ncsu.mas.org.spec.gen.classes.EventType;
import edu.ncsu.mas.org.spec.gen.classes.OrgType;
import edu.ncsu.mas.org.spec.gen.classes.PowerType;
import edu.ncsu.mas.org.spec.gen.classes.ProhibitionType;
import edu.ncsu.mas.org.spec.gen.classes.RoleType;


public class RuleGenerator {
	
	public static void main(String[] args)
	{
		// OK ... Generate the stuff and get out of here.
		// <Sigh> If only it were that simple.

		try{
			
		JAXBContext jc = JAXBContext.newInstance( "edu.ncsu.mas.org.spec.gen.classes" );

		Unmarshaller u = jc.createUnmarshaller();
		InputStream fis = RuleGenerator.class.getClassLoader().getResourceAsStream("org_spec.xml");
 		
//		Get the JAXBElement and then cast it.
		JAXBElement je = (JAXBElement)u.unmarshal( fis);

		OrgType org = (OrgType)je.getValue();
		
//		Marshaller m = jc.createMarshaller();
//		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
//		m.marshal( org, System.out );		
		
		generateRulesForOrg(org);
		
		}catch(JAXBException e){
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateRulesForOrg(OrgType org)
	{
		List<RoleType> roles = org.getRole();
		List<Rule> rules = null;
		BufferedWriter bw = null;

		for (RoleType role : roles) {
			rules = generateRulesForRole(role);
			String roleName = role.getName();
			
			File roleFile = new File(roleName);
			
			try {
				bw = new BufferedWriter( new FileWriter(roleFile));
			} catch (IOException e) {
				e.printStackTrace();
			}

			for(Rule rule: rules){
					try {
						bw.write(rule.toString());
					} catch (IOException e) {
						e.printStackTrace();
					}
//					System.out.println(rule.toString());
			}
			
			try {
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static List<Rule> generateRulesForRole(RoleType role)
	{
		// For all the powers and commitments 
		// of the roles of an org, generate the rules
		
		List<Rule> rules = new ArrayList<Rule>();
		
		//Translate Powers
		List<PowerType> powers = role.getPower();
		for (PowerType power : powers) {
			Rule rule = new Rule();
			rule.setDesc("Power: "+power.getDesc());
			List<AuthorizationType> auths = power.getAuthorization();
			for (AuthorizationType auth: auths)
			{
				EventType event = auth.getAction();
				rule.addAntecedent(event);
			}
			
			EventType event = power.getAction();
			rule.addConsequent(event);
			rules.add(rule);
		}

		
		// Translate Commitments
				
		List<CommitmentType> commitments = role.getCommitment();
		for (CommitmentType commitment : commitments) {
			Rule rule = new Rule();
			rule.setDesc("Commitment: "+ commitment.getDesc());
			List<EventType> antes = commitment.getAntecedent();
			for (EventType ante: antes)
			{
				rule.addAntecedent(ante);
			}
			
			List<EventType> conseqs = commitment.getConsequent();
			for (EventType cons: conseqs)
			{
				rule.addConsequent(cons);
			}
			
			rules.add(rule);
		}
		
		//Translate Prohibitions
		List<ProhibitionType> prohibitions= role.getProhibition();
		for (ProhibitionType prohibition : prohibitions) {
			Rule rule = new Rule();
			rule.setDesc("Prohibition: "+ prohibition.getDesc());
			List<EventType> antes = prohibition.getAction();
			for (EventType ante: antes)
			{
				rule.addAntecedent(ante);
			}
			
			List<EventType> conseqs = prohibition.getSanction();
			for (EventType cons: conseqs)
			{
				rule.addConsequent(cons);
			}
			
			rules.add(rule);
		}
		return rules;
	}
	
}
