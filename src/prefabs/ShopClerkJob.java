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
		new Comerce(x,y);
	}

	public void routine(int cycle) {
			this.worker.xPos = this.main.xPos;
			this.worker.yPos = this.main.yPos;
	}

}
