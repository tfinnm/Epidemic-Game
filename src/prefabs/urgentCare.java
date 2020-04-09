package prefabs;

import entities.*;

public class urgentCare {

	public urgentCare(int x, int y, String n) {
		new building(x,y,50,25,n);
		new TriageChair(x+20,y+22,x+20,y+17,x+8,y+4);
		new TriageChair(x+26,y+22,x+26,y+17,x+8,y+12);
		new TriageChair(x+32,y+22,x+32,y+17,x+8,y+20);
		new BLSBed(x+20,y+5);
		new BLSBed(x+30,y+5);
		new ALSBed(x+43,y+7);
	}

}
