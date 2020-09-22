package prefabs;

import entities.TriageChair;
import entities.building;
import routines.*;

public class SmallDoctorsOffice {

	public SmallDoctorsOffice(int x, int y, String n) {
		new building(x,y,20,10,n);
		new TriageChair(x+17,y+7,x+17,y+3,x+8,y+5, new docRoutineA());
	}

}
