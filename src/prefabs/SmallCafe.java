package prefabs;

import entities.*;
import entities.Decoration.Design;

public class SmallCafe {

	public SmallCafe(int x,int y,String n) {
		new building(x,y,100,75,n);
		new waiter(x+10,y+10);
		new chef(x+6,y+68);
		new CafeTable(x+25,y+30);
		new CafeTable(x+40,y+15);
		new CafeTable(x+55,y+30);
		new chef(x+18,y+68);
		new CafeTable(x+70,y+15);
		new CafeTable(x+85,y+30);
		new CafeTable(x+40,y+45);
		new chef(x+30,y+68);
		new CafeTable(x+70,y+45);
		new Decoration(x,y+60,100,Design.wallHor);
	}

}
