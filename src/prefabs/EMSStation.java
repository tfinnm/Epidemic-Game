package prefabs;

import entities.*;

public class EMSStation {

	public EMSStation(int x, int y, int s) {
		if (s == 1) {
			new building(x,y,50,20,"EMS Station");
		}
		if (s == 2) {
			new building(x,y,50,40,"EMS Station");
		}
		if (s == 3) {
			new building(x,y,50,60,"EMS Station");
		}
		new Ambulance(x+25,y+10);
		if (s > 1) {
		new Ambulance(x+25,y+30);
		if (s > 2) {
			new Ambulance(x+25,y+50);
		}
		}
	}

}
