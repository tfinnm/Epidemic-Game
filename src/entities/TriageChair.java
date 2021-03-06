package entities;

import java.util.ArrayList;

import entities.marker.Style;

public class TriageChair extends Job{

	public static ArrayList<TriageChair> TriageHandler = new ArrayList<TriageChair>();
	public static int PPE = 10;

	marker seat;
	marker Attendent;
	marker Desk;
	public agent patient = null;

	public TriageChair(int x,int y,int x1,int y1,int x2,int y2, routines.routine r) {
		super();
		seat = new marker(x,y,Style.seat);
		Attendent = new marker(x1,y1,Style.none);
		Desk = new marker(x2,y2,Style.desk);
		TriageChair.TriageHandler.add(this);
		Routine = r;
	}

	public boolean seakTreatment(agent A) {
		if (worker != null && patient == null && ((worker.xPos == Desk.xPos && worker.yPos == Desk.yPos)||(worker.xPos == Attendent.xPos && worker.yPos == Attendent.yPos))) {
			patient = A;
			patient.FA = this;
			patient.xPos = seat.xPos;
			patient.yPos = seat.yPos;
			return true;
		}
		return false;
	}

	@Override
	public void routine(int cycle) {
		Job.changeBalance(1);
		this.worker.balance++;
		if (patient != null) {
			worker.usePPE();
			worker.xPos = Attendent.xPos;
			worker.yPos = Attendent.yPos;
			patient.triage();
			patient.FA = null;
			patient = null;
		} else {
			worker.xPos = Desk.xPos;
			worker.yPos = Desk.yPos;
		}
	}

}
