package prefabs;

import entities.Decoration;
import entities.Decoration.Design;
import entities.RevivalRoom;
import entities.building;

public class TraumaRoom {

	public TraumaRoom(int x, int y, String n) {
		new building(x,y,40,20,n);
		new RevivalRoom(x+25,y+7,x+8,y+4,x+8,y+12);
		new Decoration(x+15,y,20,Design.dividerVert);
	}

}
