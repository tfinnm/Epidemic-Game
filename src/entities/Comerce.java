package entities;

import java.util.ArrayList;

import entities.marker.Style;
import epidemic.UIManager;

public class Comerce {

	public static ArrayList<Comerce> ComerceHandler = new ArrayList<Comerce>();

	public agent Customer = null;
	public marker spot;
	
	public boolean dining;

	public Comerce(int x,int y,boolean d) {
		spot = new marker (x,y,Style.none);
		Comerce.ComerceHandler.add(this);
		dining = d;
	}

	public boolean shop(agent Agent) {
		if (Customer == null && !(UIManager.closeDiningRooms.isSelected() && dining)) {
			Customer = Agent;
			Agent.shop = this;
			return true;
		}
		return false;
	}

	public void routine(int cycle) {
		this.Customer.xPos = this.spot.xPos;
		this.Customer.yPos = this.spot.yPos;
	}
	public void leave() {
		if (this.Customer != null) {
			Customer.shop = null;
			this.Customer = null;
		}
	}

}
