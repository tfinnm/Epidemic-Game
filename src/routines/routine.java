package routines;

import java.io.Serializable;

import entities.Comerce;
import entities.Event;
import entities.House;
import entities.Job;
import entities.agent;

public abstract class routine implements Serializable{

	private static final long serialVersionUID = 1L;

	public abstract void doCycle(agent A, int cycle);
	
	public void work(agent A, int cycle) {
		if (A.job == null) {
			for (Job tempJob: Job.JobHandler) {
				if (tempJob.apply(A)) {
					break;
				}
			}
		} else {
			A.job.routine(cycle);
		}
	}
	public void event(agent A, int cycle ) {
		if (A.event == null) {
			for (Event tempEvent: Event.EventHandler) {
				if (tempEvent.attend(A)) {
					break;
				}
			}
		}
		if (A.event != null) {
			A.event.routine(cycle);
		}
	}
	public void shop(agent A, int cycle) {
		if (A.shop == null) {
			for (Comerce tempComerce: Comerce.ComerceHandler) {
				if (tempComerce.shop(A)) {
					A.shop.routine(cycle);
					break;
				}
			}
		} else {
			A.shop.routine(cycle);
		}
	}
	public void home(agent A,int cycle,boolean sleep) {
		if (A.home == null) {
			for (House tempHome: House.HouseHandler) {
				if (tempHome.join(A)) {
					break;
				}
			}
		} else {
			A.home.routine(cycle,sleep);
		}
	}
	
}
