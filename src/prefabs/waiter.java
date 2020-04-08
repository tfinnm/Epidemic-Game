package prefabs;

import entities.Comerce;
import entities.Job;
import entities.marker;
import entities.marker.Style;

public class waiter extends Job {

	public marker main;
	
	public waiter(int x, int y) {
		super();
		main = new marker(x,y,Style.podium);
		new Comerce(x,y);
	}

	@Override
	public void routine(int cycle) {
		this.worker.xPos = this.main.xPos;
		this.worker.yPos = this.main.yPos;
	}

}
