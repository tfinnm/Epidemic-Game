package entities;

import java.util.ArrayList;

import entities.agent.Gender;
import entities.marker.Style;

public class House {

	public static ArrayList<House> HouseHandler = new ArrayList<House>();

	public agent man = null;
	public agent woman = null;
	public marker idle1;
	public marker idle2;
	public marker bed1;
	public marker bed2;

	public House(int x,int y,int x1,int y1,int x2,int y2,int x3, int y3) {
		idle1 = new marker(x,y,Style.seat);
		idle2 = new marker(x1,y1,Style.seat);
		bed1 = new marker(x2,y2,Style.bed);
		bed2 = new marker(x3,y3,Style.bed);
		House.HouseHandler.add(this);
	}

	public boolean join(agent Agent) {
		if (Agent.gender == Gender.male) {
			if (man == null) {
				man = Agent;
				Agent.home = this;
				return true;
			}
		} else {
			if (woman == null) {
				woman = Agent;
				Agent.home = this;
				return true;
			}
		}
		return false;
	}

	public void routine(int cycle, boolean sleep) {
		if (sleep) {
			if (man != null) {
				this.man.xPos = this.bed1.xPos;
				this.man.yPos = this.bed1.yPos;
			}
			if (woman != null) {
				this.woman.xPos = this.bed2.xPos;
				this.woman.yPos = this.bed2.yPos;
			}
		} else {
			if (man != null) {
				this.man.xPos = this.idle1.xPos;
				this.man.yPos = this.idle1.yPos;
			}
			if (woman != null) {
				this.woman.xPos = this.idle2.xPos;
				this.woman.yPos = this.idle2.yPos;
			}
		}
		if (cycle %24 == 22 && man != null && woman != null && Math.random()*100 < 1) agent.newAgents++;
	}

}
