package entities;

import java.util.ArrayList;

import entities.marker.Style;

public class BLSBed {

	public static ArrayList<BLSBed> beds = new ArrayList<BLSBed>();
	
	agent patient = null;
	marker bed;
	
	public BLSBed(int x,int y) {
		bed = new marker(x,y,Style.medBed1);
		BLSBed.beds.add(this);
	}
	
	public boolean seakTreatment(agent A) {
		if (patient == null) {
			patient = A;
			patient.BLS = this;
			patient.xPos = bed.xPos;
			patient.yPos = bed.yPos;
			return true;
		}
		return false;
	}


}