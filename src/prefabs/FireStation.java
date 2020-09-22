package prefabs;

import entities.*;

public class FireStation {

	public FireStation(int x, int y, int s) {
		if (s == 1) {
			new building(x,y,50,20,"Fire Station");
		}
		if (s == 2) {
			new building(x,y,50,75,"Fire Station");
		}
		if (s == 3) {
			new building(x,y,50,80,"Fire Station");
		}
		new FireTruck(x+25,y+10);
		if (s > 1) {
		new FireTruck(x+25,y+30);
		new Ambulance(x+25,y+50);
		if (s > 2) {
			new FireTruck(x+25,y+70);
		}
		}
	}

}
