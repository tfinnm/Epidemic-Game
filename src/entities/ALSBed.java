package entities;

import java.util.ArrayList;

import entities.marker.Style;

public class ALSBed {

	public static ArrayList<ALSBed> beds = new ArrayList<ALSBed>();
	
	agent patient = null;
	public marker bed;
	
	public ALSBed(int x,int y) {
		ALSBed.beds.add(this);
		bed = new marker(x,y,Style.medBed2);
	}
	
	public boolean seakTreatment(agent A) {
		if (patient == null) {
			patient = A;
			patient.ALS = this;
			patient.xPos = bed.xPos;
			patient.yPos = bed.yPos;
			return true;
		}
		return false;
	}


}
