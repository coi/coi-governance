package gov.agent;

public class Agent extends Thread{

	public String name = null;
	
	Agent() {
	
	}
	
	Agent(String agentName) {
		
		super(agentName); // Initialize thread.
		name = new String(agentName);

		start();
		
	}
	
	public void run() {
	
		//read and write jess here
		
	}
	
}
