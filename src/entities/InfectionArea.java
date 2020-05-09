package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import epidemic.UIManager;

public class InfectionArea extends Renderable{

	public static ArrayList<InfectionArea> IAHandler = new ArrayList<InfectionArea>();
	
	public int xPos;
	public int yPos;
	public int chance;
	public int range;
	public Disease virus;

	public InfectionArea(int x,int y,int c,int r,Disease v) {
		xPos = x;
		yPos = y;
		chance = c;
		range = r;
		virus = v;
		InfectionArea.IAHandler.add(this);
	}

	public void spread() {
		//for (agent tempAgent: agent.AgentHandler) { //concurrent mod exception
		for (int i = 0; i < agent.AgentHandler.size(); i++) {
			agent tempAgent = agent.AgentHandler.get(i);
			if (Math.sqrt(Math.pow(xPos-tempAgent.xPos,2)+Math.pow(yPos-tempAgent.yPos,2)) < range) {
				if (!tempAgent.Antibodies.contains(virus) && !tempAgent.hasPPE) {
					boolean has = false;
					for (DiseaseInstance td: tempAgent.Diseases) {
						if (td.virus == virus) has = true;
					}
					if ((!has) && (Math.random()*(chance*tempAgent.immunity)) < 1) {
						tempAgent.Diseases.add(new DiseaseInstance(virus));
					}
				}
			}
		}
	}

	@Override
	public void draw(Graphics g) {
		if (UIManager.debug) {
			g.setColor(new Color(0,255,0,50));
			g.fillOval(xPos-range, yPos-range, range*2, range*2);
		}
	}

}
