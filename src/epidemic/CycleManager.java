package epidemic;

import entities.*;
import entities.agent.trait;

public class CycleManager implements Runnable{

	private static int cycle = 0;

	public static int newAgents = 0;

	public static int getCycle() {
		return cycle;
	}

	public static void advance() {
		resetCycle();
		for (int k = 0; k < agent.AgentHandler.size(); k++) {
			agent tempAgent = agent.AgentHandler.get(k);
			tempAgent.hasPPE = false;
			for (DiseaseInstance tempDisease: tempAgent.Diseases) {
				tempDisease.virus.spread(tempAgent,tempDisease);
			}

			if(tempAgent.health(cycle)) {
				if (tempAgent.ALS == null && tempAgent.BLS == null && !tempAgent.remove) {
					if(tempAgent.prescribed) {
						for (pharmacy tempP: pharmacy.PharmaHandler) {
							if (tempP.seakTreatment(tempAgent)) {
								UIManager.log(tempAgent+" Sent Treatment Pharmacy");
								break;
							}
						}
					}
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
					} else {
						tempAgent.Routine.doCycle(tempAgent, cycle);
						tempAgent.LogMarker(tempAgent.xPos, tempAgent.yPos);
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
		
		EOCDiseaseTasks();

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
				if(UIManager.rt.getState()==Thread.State.TERMINATED){
					UIManager.rt = new Thread(new RenderLoop());
					UIManager.rt.start();
				}
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
	
	private static void resetCycle() {
		for (Ambulance TA: Ambulance.dispatch) {
			TA.reset();
		}
		for (FireTruck TA: FireTruck.dispatch) {
			TA.reset();
		}
		for (Event tempEvent: Event.EventHandler) {
			tempEvent.leave();
		}
		for (Comerce tempComerce: Comerce.ComerceHandler) {
			tempComerce.leave();
		}
	}
	private static void EOCDiseaseTasks() {
		for (Disease d: Disease.DiseaseHandler) {
			d.research();
		}
		for (InfectionArea IA: InfectionArea.IAHandler) {
			IA.spread();
		}
	}

}
