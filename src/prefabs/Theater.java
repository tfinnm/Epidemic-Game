package prefabs;

import entities.*;
import entities.Decoration.Design;

public class Theater {

	public Theater(int x,int y, String n) {
		new building(x,y,50,100, n);
		new Event(x+10,y+9,false);
		new Event(x+20,y+5,false);
		new Event(x+30,y+13,false);
		new Event(x+40,y+7,false);
		new Decoration(x,y+17,50,Design.dividerHor);
		for (int j = 25; j < 98; j += 6) {
			for (int i = 4; i < 48; i+=6) {
				new Event(x+i,y+j,true);
			}
		}
	}

}
