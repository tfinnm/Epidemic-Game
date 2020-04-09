package prefabs;

import entities.*;

public class firstaid {

	public firstaid(int x, int y, String n) {
		new building(x,y,20,20,n);
		new TriageChair(x+17,y+17,x+17,y+13,x+8,y+16);
		new BLSBed(x+2,y+5);
		new BLSBed(x+12,y+5);
	}

}
