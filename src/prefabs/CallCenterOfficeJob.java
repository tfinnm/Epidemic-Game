package prefabs;

import entities.Job;
import entities.marker;
import entities.marker.Style;

public class CallCenterOfficeJob extends Job {

	public marker main;
	
	
	public CallCenterOfficeJob(int x, int y) {
		super();
		main = new marker(x,y,Style.desk);
	}

	public void routine(int cycle) {
			this.worker.xPos = this.main.xPos;
			this.worker.yPos = this.main.yPos;
	}

}
