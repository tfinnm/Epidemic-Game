package entities;

import java.util.ArrayList;

public abstract class Job {

	public static ArrayList<Job> JobHandler = new ArrayList<Job>();
	
	public int payOut = 1;
	//produces goes here
	
	public agent worker = null;
	
	public Job() {
		Job.JobHandler.add(this);
	}
	
	public boolean apply(agent Agent) {
		if (worker == null) {
			worker = Agent;
			Agent.job = this;
			return true;
		}
		return false;
	}
	
	public abstract void routine(int cycle);

}
