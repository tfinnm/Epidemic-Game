package prefabs;

import entities.RoadNode;
import epidemic.UIManager;

public class RoadGridGenerator {

	public RoadGridGenerator(int xGap,int yGap) {
		RoadNode[] pastrow = new RoadNode[(1+(UIManager.WIDTH-16)/(xGap+32))+xGap+32];
		RoadNode[] thisrow = new RoadNode[(1+(UIManager.WIDTH-16)/(xGap+32))+xGap+32];
		for (int j = 0; j < UIManager.HEIGHT+xGap+32; j+=(yGap+32)) {
			for (int i = 16; i < UIManager.WIDTH+xGap+32; i+=(xGap+32)) {
				thisrow[((i-16)/(xGap+32))] = new RoadNode(i,j);
				if (i > 16) {
					thisrow[((i-16)/(xGap+32))].west = thisrow[((i-16)/(xGap+32))-1];
					thisrow[((i-16)/(xGap+32))-1].east = thisrow[((i-16)/(xGap+32))];
				}
				if (j > 0) {
					thisrow[((i-16)/(xGap+32))].north = pastrow[((i-16)/(xGap+32))];
					pastrow[((i-16)/(xGap+32))].south = thisrow[((i-16)/(xGap+32))];
				}
			}
			pastrow = thisrow.clone();
		}
	}

}
