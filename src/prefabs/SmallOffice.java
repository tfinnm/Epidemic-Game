package prefabs;

import entities.*;
import entities.Decoration.Design;

public class SmallOffice {

	public SmallOffice(int x, int y, String n) {
		new building(x,y,50,40, n);
		new Decoration(x+30, y+20, 3, Design.confrenceTable);
		new Decoration (x,y+8,10,Design.wallHor);
		new Decoration (x,y+16,10,Design.wallHor);
		new Decoration (x,y+24,10,Design.wallHor);
		new Decoration (x,y+32,10,Design.wallHor);
		new StandardOfficeJob(x+8, y+4, x+30, y+4, x+37, y+28);
		new StandardOfficeJob(x+8, y+12, x+30, y+36, x+37, y+12);
		new StandardOfficeJob(x+8, y+20, x+22, y+20, x+23, y+28);
		new StandardOfficeJob(x+8, y+28, x+38, y+20, x+23, y+12);
		new CallCenterOfficeJob(x+8, y+36);
	}

}
