package prefabs;

import entities.Comerce;
import entities.Job;
import entities.marker;
import entities.marker.Style;

public class ShopClerkJob extends Job {

	public marker main;
	
	
	public ShopClerkJob(int x, int y) {
		super();
		main = new marker(x,y,Style.register);
		new Comerce(x,y,false);
	}

	public void routine(int cycle) {
			Job.changeBalance(3);
			this.worker.xPos = this.main.xPos;
			this.worker.yPos = this.main.yPos;
	}

}
