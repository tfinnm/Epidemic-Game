package prefabs;

import entities.*;
import entities.Decoration.Design;

public class BasicShop {

	public BasicShop(int x, int y, String n) {
		new building(x,y,100,50, n);
		new ShopClerkJob(x+3,y+10);
		new Comerce(x+15,y+10);
		new Decoration(x,y+29,10,Design.shelf);
		new Comerce(x+6,y+35);
		new Decoration(x+9,y+29,10,Design.shelf);
		new Decoration(x+12,y+29,10,Design.shelf);
		
		new Decoration(x+25,y,10,Design.shelf);
		new Decoration(x+25,y+29,10,Design.shelf);
		new Decoration(x+28,y,10,Design.shelf);
		new Decoration(x+28,y+29,10,Design.shelf);
		new Comerce(x+34,y+15);
		new Comerce(x+34,y+35);
		new Decoration(x+37,y,10,Design.shelf);
		new Decoration(x+37,y+29,10,Design.shelf);
		new Decoration(x+40,y,10,Design.shelf);
		new Decoration(x+40,y+29,10,Design.shelf);
		new Comerce(x+46,y+15);
		new Comerce(x+46,y+35);
		new Decoration(x+49,y,10,Design.shelf);
		new Decoration(x+49,y+29,10,Design.shelf);
		new Decoration(x+52,y,10,Design.shelf);
		new Decoration(x+52,y+29,10,Design.shelf);
		new Comerce(x+58,y+15);
		new Comerce(x+58,y+35);
		new Decoration(x+61,y,10,Design.shelf);
		new Decoration(x+61,y+29,10,Design.shelf);
		new Decoration(x+64,y,10,Design.shelf);
		new Decoration(x+64,y+29,10,Design.shelf);
		new Comerce(x+70,y+15);
		new Comerce(x+70,y+35);
		new Decoration(x+73,y,10,Design.shelf);
		new Decoration(x+73,y+29,10,Design.shelf);
		new Decoration(x+76,y,10,Design.shelf);
		new Decoration(x+76,y+29,10,Design.shelf);
		new Comerce(x+82,y+15);
		new Comerce(x+82,y+35);
		new Decoration(x+85,y,10,Design.shelf);
		new Decoration(x+85,y+29,10,Design.shelf);
		new Decoration(x+88,y,10,Design.shelf);
		new Decoration(x+88,y+29,10,Design.shelf);
		new Comerce(x+94,y+15);
		new Comerce(x+94,y+35);
		new Decoration(x+97,y,10,Design.shelf);
		new Decoration(x+97,y+29,10,Design.shelf);
	}

}
