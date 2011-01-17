package edu.ncsu.mas.org.spec.gen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import edu.ncsu.mas.org.spec.gen.classes.EventType;
import edu.ncsu.mas.org.spec.gen.classes.ParameterType;

public class Rule {
	
	public List<EventType> antecedent = new ArrayList<EventType>();
	public List<EventType> consequent = new ArrayList<EventType>();
	
	public static long ruleCount = 0;
	private StringBuffer sb = null;
	public String desc = null;
	

	public void addAntecedent(EventType event)
	{
		antecedent.add(event);
	}
	
	public List<EventType> getAntecedent()
	{
		return antecedent;
	}
	
	public void addConsequent(EventType event)
	{
		consequent.add(event);
	}
	
	public List<EventType> getConsequent()
	{
		return consequent;
	}
	
	
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		
		sb.append("(defrule Rule_"+getRuleCount());
		
		sb.append("\n\"Desc: "+ this.getDesc()+" \"");
		
		for (EventType event : antecedent) {
			String factName = event.getName();
			sb.append("\n\t(" + factName);
			
			List<ParameterType> params = event.getParam();
			
			
			
			for (ParameterType param : params) {

				//Reinitialize every time
				
				String slotName = null;
				String slotValue = null;
				
				slotName = param.getName();
				if(param.getIntValue() != null)
				{
					slotValue = param.getIntValue().toString();

				} 
				else 
				if(param.getFloatValue() != null)
				{
					slotValue = param.getFloatValue().toString();
				}
				else
				if(param.getStringValue() != null)
				{
					slotValue = param.getStringValue();	
				}
				
				//Add the slot				
				sb.append(" (");
				sb.append(slotName);
				sb.append(" ");
				
				
				//check if variable--------------------
				if(slotValue != null && slotValue.startsWith("var_"))
					sb.append("?"+slotValue);
				else 
					sb.append(slotValue);
				//-------------------------------------
				
				sb.append(")");
			}
			sb.append(")");
		}
		
		//Figure out what goes into the policy.
		//The set difference of the antecedent and consequent variables (?)
		
		HashSet<String> vars = new HashSet<String>();
		HashMap<String, String> var_map = new HashMap<String, String>();
		
		for(EventType event: consequent)
		{
			List<ParameterType> params = event.getParam();
			for (ParameterType param: params)
			{
				String param_name = param.getName();
				String value = param.getStringValue();
				if(value!= null && value.startsWith("var_"))
				{
					vars.add(value);
					var_map.put(value, param_name);
				}
			}
		}
		
		//check for each param in consequent
		for(EventType event: antecedent)
		{
			List<ParameterType> params = event.getParam();
			for (ParameterType param: params)
			{
			
				String value = param.getStringValue();
				if(value.startsWith("var_"))
				{
					vars.remove(value);
				}
			}
		}
		
		
		// Now add policy for whatever remaining parameters are needed.
		
		sb.append(addPolicy(vars, var_map));
		
		sb.append("\n => \n");
		
		for (EventType event : consequent){
			String factName = event.getName();
			sb.append("\n\t(assert (" + factName);
			
			List<ParameterType> params = event.getParam();
			
//			Initialize what we might need.
			String slotName = null;
			String slotValue = null;
			
			for (ParameterType param : params) {
				slotName = param.getName();
				if(param.getIntValue() != null)
				{
					slotValue = param.getIntValue().toString();

				} 
				else 
				if(param.getFloatValue() != null)
				{
					slotValue = param.getFloatValue().toString();
				}
				else
				if(param.getStringValue() != null)
				{
					slotValue = param.getStringValue();	
				}
				
				sb.append(addSlot(slotName, slotValue));
			}
			sb.append(")"); //assert end
		}		
		
		sb.append("\n)\n"); // end rule
		return sb.toString();
	}
	
	private long getRuleCount() {
		return ruleCount++;
	}

	private String addPolicy(HashSet<String> vars, HashMap<String, String> var_map) {
		
		StringBuffer s = new StringBuffer();
		s.append("\n\t(policy "); //policy start
		
		s.append("(id ");
		s.append(getRuleCount());
		s.append(")");
		
		for (Iterator iterator = vars.iterator(); iterator.hasNext();) {
			String var = (String) iterator.next();
			String var_slotName = var_map.get(var);
			if(var!= null && var.startsWith("var_"))
				s.append("("+var_slotName+" ?"+ var+")");
			else 
				s.append("("+var_slotName+" "+ var+")");
		}
		
		s.append(")"); //policy close.
		return s.toString();
	}

	public String addSlot( String slotName, String slotValue)
	{
		
		StringBuffer sb = new StringBuffer();
//		add the slot
		sb.append("(");
		sb.append(slotName);
		sb.append(" ");
		if(slotValue!= null && slotValue.startsWith("var_"))
			sb.append(" ?"+slotValue);
		else
			sb.append(slotValue);
		
		sb.append(")");
		
		return sb.toString();
	}
	public String getDesc() {
		if(desc == null)
			return "None.";
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

}
