package scenarios;

import entities.RoadNode;
import entities.agent;
import prefabs.*;

public class Olmsted implements Scenario{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3403262381849600207L;

	public void loadScenario() {
		
		
		new agent(0,0);
		
		//Road Network
		RoadNode beltwayS = new RoadNode(20,0);
		RoadNode beltwayE = new RoadNode(20,800);
		RoadNode enter = new RoadNode(20,533);
		RoadNode intersection = new RoadNode(300,500);
		RoadNode Site1 = new RoadNode(1260,500);
		RoadNode StaffSiteS = new RoadNode(315,425);
		RoadNode StaffSiteE = new RoadNode(100,400);
		
		beltwayS.south = enter;
		enter.north = beltwayS;
		enter.south = beltwayE;
		beltwayE.north = enter;
		enter.east = intersection;
		intersection.west = enter;
		intersection.east = Site1;
		Site1.west = intersection;
		intersection.north = StaffSiteS;
		StaffSiteS.south = intersection;
		StaffSiteS.west = StaffSiteE;
		StaffSiteE.east = StaffSiteS;

		//Staff Site
		new Apartment(255, 455, "Female Staff Site");
		new Apartment(230, 440, "Female Staff Site");
		new Apartment(225, 465, "Female Staff Site");
		

	}

}
