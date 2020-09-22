package entities;

import java.util.ArrayList;

import epidemic.UIManager;
import routines.defaultRoutine;

public abstract class Job {

	public static ArrayList<Job> JobHandler = new ArrayList<Job>();
	public static int bankBalance = 1000;
	
	public static void changeBalance(int ammount) {
		bankBalance += ammount;
		UIManager.CashAvailible.setText("Balance: "+Job.bankBalance+" | Jobs: "+Job.JobHandler.size());
		if (Job.bankBalance > agent.AgentHandler.size()*10) {
			UIManager.massTesting.setEnabled(true);
		} else {
			UIManager.massTesting.setEnabled(false);
		}
	}
	
	
	public int payOut = 1;
	//produces goes here
	
	public agent worker = null;
	routines.routine Routine;
	
	public Job() {
		Job.JobHandler.add(this);
		Routine = new defaultRoutine();
	}
	
	public boolean apply(agent Agent) {
		if (worker == null) {
			worker = Agent;
			Agent.job = this;
			Agent.Routine = Routine;
			return true;
		}
		return false;
	}
	
	public abstract void routine(int cycle);

}
