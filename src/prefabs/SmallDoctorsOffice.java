package prefabs;

import entities.BLSBed;
import entities.TriageChair;
import entities.building;

public class SmallDoctorsOffice {

	public SmallDoctorsOffice(int x, int y, String n) {
		new building(x,y,20,10,n);
		new TriageChair(x+17,y+7,x+17,y+3,x+8,y+5);
	}

}
