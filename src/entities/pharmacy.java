package entities;

import java.util.ArrayList;

import entities.Decoration.Design;
import entities.Disease.Dificulty;
import entities.marker.Style;

public class pharmacy extends Job{

	public static ArrayList<pharmacy> PharmaHandler = new ArrayList<pharmacy>();

	marker seat;
	marker Attendent;
	marker restock;
	marker Desk;
	public agent patient = null;

	public int stock = 0;
	
	public pharmacy(int x,int y,int x1,int y1,int x2, int y2,int x3,int y3) {
		super();
		seat = new marker(x,y,Style.none);
		Attendent = new marker(x1,y1,Style.register);
		restock = new marker(x2,y2,Style.none);
		new Decoration(x2-6,y2-5,3,Design.shelf);
		Desk = new marker(x3,y3,Style.desk);
		pharmacy.PharmaHandler.add(this);
	}

	public boolean seakTreatment(agent A) {
		if (patient == null) {
			patient = A;
			patient.xPos = seat.xPos;
			patient.yPos = seat.yPos;
			return true;
		}
		return false;
	}

	@Override
	public void routine(int cycle) {
		Job.changeBalance(1);
		if (stock < 1) {
			this.worker.xPos = restock.xPos;
			this.worker.yPos = restock.yPos;
			stock += 5;
		} else {
		if (patient != null) {
			worker.usePPE();
			worker.xPos = Attendent.xPos;
			worker.yPos = Attendent.yPos;
			for (DiseaseInstance i: patient.Diseases) {
				if (i.diagnosed && i.virus.hasCure && i.virus.cureDificulty == Dificulty.Pharma) {
					if (Math.random()*100 < i.virus.cureEffectiveness) {
						patient.cure(i);
					}
				}
			}
			patient = null;
		} else {
			worker.xPos = Desk.xPos;
			worker.yPos = Desk.yPos;
		}
		}
	}

}
