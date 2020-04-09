package prefabs;

import entities.*;

public class ward {

	public ward(int x, int y, String n) {
		new building(x,y,50,20,n);
		new BLSBed(x+3,y+5);
		new BLSBed(x+13,y+5);
		new BLSBed(x+23,y+5);
		new BLSBed(x+33,y+5);
		new BLSBed(x+43,y+5);
	}

}
