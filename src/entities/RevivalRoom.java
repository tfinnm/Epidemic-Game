package entities;

import java.util.ArrayList;

import entities.marker.Style;

public class RevivalRoom {

	public static ArrayList<RevivalRoom> CCHandler = new ArrayList<RevivalRoom>();

	marker bed;
	marker Attendent;
	marker Attendent2;
	marker Desk;
	marker Desk2;
	public agent patient = null;

	public RevivalRoom(int x,int y,int x1,int y1,int x2,int y2) {
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
					patient.revive();
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
					if(patient.respirations > 1) {
						patient.triage();
						patient = null;
					}
				} else {
					worker.xPos = Desk2.xPos;
					worker.yPos = Desk2.yPos;
				}
			}

		};
		bed = new marker(x,y,Style.medBed4);
		Attendent = new marker(x-6,y,Style.none);
		Attendent2 = new marker(x+6,y+3,Style.none);
		Desk = new marker(x1,y1,Style.desk);
		Desk2 = new marker(x2,y2,Style.desk);
		RevivalRoom.CCHandler.add(this);
	}

	public boolean seakTreatment(agent A) {
		if (patient == null) {
			patient = A;
//			patient.FA = this;
			patient.xPos = bed.xPos;
			patient.yPos = bed.yPos;
			return true;
		}
		return false;
	}
}
