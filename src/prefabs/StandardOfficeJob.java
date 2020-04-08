package prefabs;

import entities.Job;
import entities.marker;
import entities.marker.Style;

public class StandardOfficeJob extends Job {

	public marker main;
	public marker meeting1;
	public marker meeting2;
	
	public StandardOfficeJob(int x, int y, int x1, int y1, int x2, int y2) {
		super();
		main = new marker(x,y,Style.desk);
		meeting1 = new marker(x1,y1,Style.seat);
		meeting2 = new marker(x2,y2,Style.seat);
	}

	public void routine(int cycle) {
		if (cycle % 3 == 0) {
			this.worker.xPos = this.main.xPos;
			this.worker.yPos = this.main.yPos;
		} else if (cycle % 3 == 1) {
			this.worker.xPos = this.meeting1.xPos;
			this.worker.yPos = this.meeting1.yPos;
		} else if (cycle % 3 == 2) {
			this.worker.xPos = this.meeting2.xPos;
			this.worker.yPos = this.meeting2.yPos;
		}
	}

}
