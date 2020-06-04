package epidemic;

import java.io.IOException;

import entities.*;
import entities.agent.trait;

public class CycleManager implements Runnable{

	private static int cycle = 0;

	public static int newAgents = 0;

	public static int getCycle() {
		return cycle;
	}

	public static void advance() {
		for (Ambulance TA: Ambulance.dispatch) {
			TA.reset();
		}
		for (FireTruck TA: FireTruck.dispatch) {
			TA.reset();
		}
		for (Grant g: Grant.grants) {
			g.update();
		}
		for (int k = 0; k < agent.AgentHandler.size(); k++) {
			agent tempAgent = agent.AgentHandler.get(k);
			tempAgent.hasPPE = false;
			for (DiseaseInstance tempDisease: tempAgent.Diseases) {
				tempDisease.virus.spread(tempAgent,tempDisease);
			}

			if(tempAgent.health(cycle)) {
				if (tempAgent.ALS == null && tempAgent.BLS == null && !tempAgent.remove) {
					if (!tempAgent.traits.contains(trait.antiestablishment) && (tempAgent.respirations < 11 || (!tempAgent.traits.contains(trait.tough) && !UIManager.discourageHospital.isSelected() && tempAgent.respirations < 12))) {
						if (TriageChair.TriageHandler.contains(tempAgent.job)) {
							tempAgent.job.worker = null;
							tempAgent.job = null;
						}
						if (tempAgent.FA == null) {
							for (TriageChair tempFA: TriageChair.TriageHandler) {
								if (tempFA.seakTreatment(tempAgent)) {
									break;
								}
							}
						}
					} else if (cycle % 24 > 9 && cycle % 24 < 17 && !tempAgent.stayHome()) { 
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
						if (cycle%24 == 17 && !tempAgent.stayHome()) {
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
					if (Client.multiplayer && tempAgent.home == null) {
						for (House tempHome: House.HouseHandler) {
							if (tempHome.join(tempAgent)) {
								break;
							}
						}
						if (tempAgent.home == null) {
							try {
								tempAgent.ALS = null;
								tempAgent.BLS = null;
								tempAgent.FA = null;
								tempAgent.job = null;
								tempAgent.event = null;
								tempAgent.shop = null;
								tempAgent.home = null;
								System.out.print(tempAgent.Diseases);
								Object[] data = {"agent",tempAgent};
								Client.out.writeObject(data);
								Client.out.flush();
								tempAgent.remove = true;
								
							} catch (IOException e) {
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
			for (int i = 0; i < tempAgent.Diseases.size(); i++) {
				DiseaseInstance di = tempAgent.Diseases.get(i); 
				if (di.cured) tempAgent.Diseases.remove(di);
			}
		}

		//		for (int i = 0; i < newAgents; i++) {
		//			new agent(0,0);
		//		}
		//
		//		newAgents = 0;

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
		
		for (Disease d: Disease.DiseaseHandler) {
			d.research();
		}
	
		
		for (InfectionArea IA: InfectionArea.IAHandler) {
			IA.spread();
		}

		//always runs last
		cycle++;
		if(cycle>7*24) cycle = 0;
	}

	@Override
	public void run() {
		while(true) {
			if(!UIManager.pause.getState() || UIManager.advanceOnce) {
				UIManager.advanceOnce = false;
				advance();
				UIManager.rt.interrupt();
			}
			try {
				if (UIManager.hispeed.isSelected()) {
					Thread.sleep(100);
				} else {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

}
