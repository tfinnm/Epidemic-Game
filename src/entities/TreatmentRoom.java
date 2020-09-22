package entities;

import java.util.ArrayList;

import entities.Disease.Dificulty;
import entities.marker.Style;

public class TreatmentRoom {

	public static ArrayList<TreatmentRoom> TRHandler = new ArrayList<TreatmentRoom>();

	marker bed;
	marker Attendent;
	marker Attendent2;
	marker Desk;
	marker Desk2;
	public agent patient = null;
	boolean treated = false;

	public TreatmentRoom(int x,int y,int x1,int y1,int x2,int y2) {
		super();
		new Job() {
			@Override
			public void routine(int cycle) {
				Job.changeBalance(1);
				this.worker.balance++;
				if (patient != null) {
					worker.usePPE();
					worker.xPos = Attendent.xPos;
					worker.yPos = Attendent.yPos;
					for (DiseaseInstance i: patient.Diseases) {
						if (i.diagnosed && i.virus.hasCure && i.virus.cureDificulty == Dificulty.Surgery) {
							if (Math.random()*100 < i.virus.cureEffectiveness) {
								patient.cure(i);
							}
						}
					}
					treated = true;
				} else {
					worker.xPos = Desk.xPos;
					worker.yPos = Desk.yPos;
				}
			}
		};
		new Job() {
			@Override
			public void routine(int cycle) {
				Job.changeBalance(1);
				this.worker.balance++;
				if (patient != null) {
					worker.usePPE();
					worker.xPos = Attendent2.xPos;
					worker.yPos = Attendent2.yPos;
					if (treated) {
						patient.triage();
						patient = null;
						treated = false;
					}
				} else {
					worker.xPos = Desk2.xPos;
					worker.yPos = Desk2.yPos;
				}
			}

		};
		bed = new marker(x,y,Style.medBed3);
		Attendent = new marker(x-6,y,Style.none);
		Attendent2 = new marker(x+6,y+3,Style.none);
		Desk = new marker(x1,y1,Style.desk);
		Desk2 = new marker(x2,y2,Style.desk);
		TreatmentRoom.TRHandler.add(this);
	}

	public boolean seakTreatment(agent A) {
		if(patient != null && patient.remove) {
			patient = null;
		}
		if (patient == null) {
			patient = A;
			//			patient.FA = this;
			patient.xPos = bed.xPos;
			patient.yPos = bed.yPos;
			treated = false;
			return true;
		}
		return false;
	}
}
