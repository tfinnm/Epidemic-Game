package prefabs;

import entities.building;
import entities.pharmacy;

public class HospitalPharmacy {

	public HospitalPharmacy(int x,int y,String n) {
		new building(x,y,30,20,n);
		new pharmacy(x+20,y+10,x+10,y+10,x+6,y+5,x+8,y+15);
	}

}
