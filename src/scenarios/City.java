package scenarios;

import entities.*;
import prefabs.*;

public class City implements Scenario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6743163858059666577L;

	public void loadScenario() {
		
		
		for (int i = 0; i < 1000; i++) {
			new agent(0,0);
		}
		new RoadGridGenerator(100,100);
		new Ambulance(82,8);
		new Apartment(32,16,"apartment");
		new Apartment(52,16,"apartment");
		new Apartment(72,16,"apartment");
		new Apartment(92,16,"apartment");
		new Apartment(112,16,"apartment");
		new Apartment(32,36,"apartment");
		new Apartment(52,36,"apartment");
		new Apartment(72,36,"apartment");
		new Apartment(92,36,"apartment");
		new Apartment(112,36,"apartment");
		new Apartment(32,56,"apartment");
		new Apartment(52,56,"apartment");
		new Apartment(72,56,"apartment");
		new Apartment(92,56,"apartment");
		new Apartment(112,56,"apartment");
		new Apartment(32,76,"apartment");
		new Apartment(52,76,"apartment");
		new Apartment(72,76,"apartment");
		new Apartment(92,76,"apartment");
		new Apartment(112,76,"apartment");
		new Apartment(32,96,"apartment");
		new Apartment(52,96,"apartment");
		new Apartment(72,96,"apartment");
		new Apartment(92,96,"apartment");
		new Apartment(112,96,"apartment");
		new commercialPharmacy(164,16,"CVS");
		new BasicShop(164,66,"General's Store");
		new SmallCafe(296,16,"Panera");

	}

}
