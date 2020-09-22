package prefabs;

import entities.Comerce;
import entities.Job;
import entities.marker;
import entities.marker.Style;
import epidemic.UIManager;

public class waiter extends Job {

	public marker main;
	
	public waiter(int x, int y) {
		super();
		main = new marker(x,y,Style.podium);
		new Comerce(x,y,true);
	}

	@Override
	public void routine(int cycle) {
		if (!UIManager.closeDiningRooms.isSelected()) {	
			Job.changeBalance(3);
			this.worker.balance++;
			this.worker.xPos = this.main.xPos;
			this.worker.yPos = this.main.yPos;
		}
	}

}
