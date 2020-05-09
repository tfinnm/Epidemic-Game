package prefabs;

import entities.Comerce;
import entities.Job;
import entities.marker;
import entities.marker.Style;

public class chef extends Job {

	public marker main;
	
	public chef(int x, int y) {
		super();
		main = new marker(x,y,Style.kitchen);
		new Comerce(x,y,false);
	}

	@Override
	public void routine(int cycle) {
		Job.changeBalance(3);
		this.worker.xPos = this.main.xPos;
		this.worker.yPos = this.main.yPos;
	}

}
