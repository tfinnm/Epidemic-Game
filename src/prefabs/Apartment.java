package prefabs;

import entities.*;

public class Apartment {

	public Apartment(int x, int y, String n) {
		new building(x, y, 20, 20, n);
		new House(x+3, y+4, x+3, y+10, x+10, y+7, x+14, y+7);
	}

}
