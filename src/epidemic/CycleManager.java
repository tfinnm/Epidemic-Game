package epidemic;

import entities.*;

public class CycleManager implements Runnable{

	private static int cycle = 0;

	public static int newAgents = 0;

	public static int getCycle() {
		return cycle;
	}

	public static void advance() {
		for (agent tempAgent: agent.AgentHandler) {
			tempAgent.health(cycle);
			tempAgent.hasPPE = false;
			for (DiseaseInstance tempDisease: tempAgent.Diseases) {
				tempDisease.virus.spread(tempAgent,tempDisease);
			}
			if (tempAgent.ALS == null && tempAgent.BLS == null) {
				if (tempAgent.respirations < 12) {
					if (tempAgent.FA == null) {
						for (TriageChair tempFA: TriageChair.TriageHandler) {
							if (tempFA.seakTreatment(tempAgent)) {
								break;
							}
						}
					}
				} else if (cycle % 24 > 9 && cycle % 24 < 17) { 
					if (cycle/24 < 6) {
						if (tempAgent.job == null) {
							for (Job tempJob: Job.JobHandler) {
								if (tempJob.apply(tempAgent)) {
									break;
								}
							}
						} else {
							tempAgent.job.routine(cycle);
						}
					} else {
						if (tempAgent.event == null) {
							for (Event tempEvent: Event.EventHandler) {
								if (tempEvent.attend(tempAgent)) {
									break;
								}
							}
						} else {
							tempAgent.event.routine(cycle);
						}
					}
				} else {
					if (cycle%24 == 17) {
						if (tempAgent.shop == null) {
							for (Comerce tempComerce: Comerce.ComerceHandler) {
								if (tempComerce.shop(tempAgent)) {
									tempAgent.shop.routine(cycle);
									break;
								}
							}
						} else {
							tempAgent.shop.routine(cycle);
						} 
					} else {
						if (tempAgent.home == null) {
							for (House tempHome: House.HouseHandler) {
								if (tempHome.join(tempAgent)) {
									break;
								}
							}
						} else {
							tempAgent.home.routine(cycle);
						}
					}
				}
			} else {
				if (tempAgent.ALS != null) {
					tempAgent.xPos = tempAgent.ALS.bed.xPos;
					tempAgent.yPos = tempAgent.ALS.bed.yPos;
				}
				if (tempAgent.BLS != null) {
					tempAgent.xPos = tempAgent.BLS.bed.xPos;
					tempAgent.yPos = tempAgent.BLS.bed.yPos;
				}
			}
		}

		for (int i = 0; i < newAgents; i++) {
			new agent(0,0);
		}

		newAgents = 0;

		if (cycle % 24 == 16) {
			for (Event tempEvent: Event.EventHandler) {
				tempEvent.leave();
			}
		}
		if (cycle % 24 == 0) {
			for (Comerce tempComerce: Comerce.ComerceHandler) {
				tempComerce.leave();
			}
		}

		for (int i = 0; i < agent.AgentHandler.size(); i++) {
			agent.AgentHandler.get(i).die();
		}

		//always runs last
		cycle++;
		if(cycle>7*24) cycle = 0;
	}

	@Override
	public void run() {
		while(true) {
			advance();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
