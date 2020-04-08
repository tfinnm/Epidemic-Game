package entities;

import java.util.ArrayList;

import entities.marker.Style;

public class Event {

	public static ArrayList<Event> EventHandler = new ArrayList<Event>();

	public agent Attendee = null;
	public marker spot;
	
	public Event(int x,int y,boolean s) {
		if (s) {
			spot = new marker (x,y,Style.seat);
		} else {
			spot = new marker (x,y,Style.none);
		}
		Event.EventHandler.add(this);
	}

	public boolean attend(agent Agent) {
			if (Attendee == null) {
				Attendee = Agent;
				Agent.event = this;
				return true;
			}
		return false;
	}

	public void routine(int cycle) {
				this.Attendee.xPos = this.spot.xPos;
				this.Attendee.yPos = this.spot.yPos;
	}
	public void leave() {
		if (this.Attendee != null) {
			Attendee.event = null;
			this.Attendee = null;
		}
	}

}
