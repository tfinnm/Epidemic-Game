package prefabs;

import entities.*;

public class ICU {

	public ICU(int x, int y, String n) {
		new building(x,y,50,20,n);
		new ALSBed(x+10,y+7);
		new ALSBed(x+25,y+7);
		new ALSBed(x+40,y+7);
	}

}
