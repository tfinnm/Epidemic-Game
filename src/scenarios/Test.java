package scenarios;

import entities.ALSBed;
import entities.BLSBed;
import entities.Disease;
import entities.DiseaseInstance;
import entities.Effect;
import entities.RoadNode;
import entities.TriageChair;
import entities.agent;
import prefabs.*;

public class Test {

	public static void loadScenario() {
		Disease DT = new Disease("Covid-19/SARS-2",10,10,0);
		DT.Symptoms.add(new Effect() {

			@Override
			public void initial(agent A) {	
			}

			@Override
			public void cycle(int cycle, agent A) {
				if (cycle % 3 == 0) {
					A.respirations--;
				}
				if (cycle % 5 == 0) {
					A.lungCapacity--;
				}
			}
			
		});
		new agent(150,150);
		new agent(500,500);
		new agent(500,600);
		new agent(600,500);
		new agent(600,600);
		new agent(700,500);
		new agent(700,600);
		new agent(800,500);
		new agent(800,600);
		new agent(900,500).Diseases.add(new DiseaseInstance(DT));
		new agent(900,600);
		for (int i = 0; i < 100; i++) {
			new agent(0,0);
		}
		new firstaid(80,320,"Mayo");
		new urgentCare(100,320,"Health Lodge");
		new ward(150,320,"Hospital");
		new ICU(200,320,"Hospital");
		new SmallDoctorsOffice(250,320,"Hospital");
		new SmallDoctorsOffice(250,330,"Hospital");
		new SmallCafe(100,350,"Forster Hall");
		new Apartment(100,100, "Apartment");
		new Apartment(120,100, "Apartment");
		new Apartment(140,100, "Apartment");
		new Apartment(160,100, "Apartment");
		new Apartment(180,100, "Apartment");
		new Apartment(100,80, "Apartment");
		new Apartment(120,80, "Apartment");
		new Apartment(140,80, "Apartment");
		new Apartment(160,80, "Apartment");
		new Apartment(180,80, "Apartment");
		new Apartment(100,60, "Apartment");
		new Apartment(120,60, "Apartment");
		new Apartment(140,60, "Apartment");
		new Apartment(160,60, "Apartment");
		new Apartment(180,60, "Apartment");
		new SmallOffice(100,120, "Epson Inc.");
		new SmallOffice(150,120, "Tfinnm Development");
		new Theater(200,60, "IMAX");
		new BasicShop(250,60, "The General Store");
		new BasicShop(250,110, "The General Store 2: Electric Boogaloo");
		new BasicShop(250,160, "The General Store 3: Yeet?");
		new SmallOffice(100,160, "Linus Media Group");
		new SmallOffice(150,160, "Marriot Scout Service Center");
		new SmallOffice(200,160, "r/place");
		new Apartment(100,200, "Apartment");
		new Apartment(120,200, "Apartment");
		new Apartment(140,200, "Apartment");
		new Apartment(160,200, "Apartment");
		new Apartment(180,200, "Apartment");
		RoadNode c = new RoadNode(500,500);
		RoadNode u = new RoadNode(500,400);
		RoadNode l = new RoadNode(400,500);
		RoadNode r = new RoadNode(600,500);
		RoadNode d = new RoadNode(500,600);
		c.north = u;
		u.south = c;
		c.south = d;
		d.north = c;
		c.east = r;
		r.west = c;
		c.west = l;
		l.east = c;
		r.north = u;
		u.east = r;
		l.north = u;
		u.west = l;
		d.east = r;
		r.south = d;
		d.west = l;
		l.south = d;
		

	}

}
