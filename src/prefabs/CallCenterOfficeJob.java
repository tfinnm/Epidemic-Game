package prefabs;

import entities.Job;
import entities.marker;
import entities.marker.Style;
import epidemic.UIManager;

public class CallCenterOfficeJob extends Job {

	public marker main;
	
	
	public CallCenterOfficeJob(int x, int y) {
		super();
		main = new marker(x,y,Style.desk);
	}

	public void routine(int cycle) {
		if (!UIManager.closeNonEssential.isSelected()) {
			Job.changeBalance(5);
			this.worker.xPos = this.main.xPos;
			this.worker.yPos = this.main.yPos;
		}
	}

}
