package prefabs;

import entities.Comerce;
import entities.Decoration;
import entities.Decoration.Design;
import entities.marker.Style;

public class CafeTable {

	public CafeTable(int x, int y) {
		new Decoration(x,y,1,Design.confrenceTable);
		new Comerce(x-8,y,true).spot.style = Style.seat;
		new Comerce(x+8,y,true).spot.style = Style.seat;
		new Comerce(x,y-8,true).spot.style = Style.seat;
		new Comerce(x,y+8,true).spot.style = Style.seat;
	}

}
