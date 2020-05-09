package prefabs;

import entities.Comerce;
import entities.Decoration;
import entities.Decoration.Design;
import entities.building;
import entities.pharmacy;

public class commercialPharmacy {

	public commercialPharmacy(int x,int y,String n) {
			new building(x,y,50,50,n);
			new pharmacy(x+20,y+10,x+10,y+10,x+6,y+5,x+8,y+15);
			new Decoration(x,y+20,20,Design.dividerHor);
			new ShopClerkJob(x+10,y+25);
			new Comerce(x+20,y+25,false);
			new Comerce(x+30,y+10,false);
			new Decoration(x+47,y,24,Design.shelf);
			new Decoration(x,y+37,6,Design.shelf);
			new Comerce(x+6,y+40,false);
			new Decoration(x+9,y+37,6,Design.shelf);
			new Decoration(x+12,y+37,6,Design.shelf);
			new Comerce(x+18,y+45,false);
			new Decoration(x+21,y+37,6,Design.shelf);
			new Decoration(x+24,y+37,6,Design.shelf);
			new Comerce(x+30,y+40,false);
			new Decoration(x+33,y+37,6,Design.shelf);
			new Decoration(x+37,y+37,6,Design.shelf);
			new Comerce(x+43,y+45,false);
	}

}
