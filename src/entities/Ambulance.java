package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class Ambulance extends marker {

	public static ArrayList<Ambulance> dispatch = new ArrayList<Ambulance>();
	
	int phase = 0;

	public int homeX;
	public int homeY;

	public Ambulance(int x, int y) {
		super(x,y,Style.none);
		homeX = x;
		homeY = y;
		dispatch.add(this);
	}

	public void reset() {
		this.xPos = homeX;
		this.yPos = homeY;
	}
	
	public boolean seakTreatment(agent A) {
		if (xPos == homeX && yPos == homeY) {
			int closestX = Integer.MAX_VALUE;
			int closestY = Integer.MAX_VALUE;
			for (RoadNode t: RoadNode.RoadNetwork) {
				if (Math.sqrt(Math.pow(A.xPos-t.xPos,2)+Math.pow(A.yPos-t.yPos,2)) < Math.sqrt(Math.pow(A.xPos-closestX,2)+Math.pow(A.yPos-closestY,2))) {
					closestX=t.xPos;
					closestY=t.yPos;
				}
			}
			xPos = closestX;
			yPos = closestY;
			return true;
		}
		return false;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		g.drawRect(this.xPos-13, this.yPos-6, 19, 12);
		g.drawRect(this.xPos+6, this.yPos-5, 7, 10);
		g.fillRect(this.xPos+8, this.yPos-6, 3, 1);
		g.fillRect(this.xPos-11, this.yPos-7, 3, 1);
		g.fillRect(this.xPos+8, this.yPos+6, 3, 1);
		g.fillRect(this.xPos-11, this.yPos+7, 3, 1);
		g.setColor(Color.white);
		g.fillRect(this.xPos-12, this.yPos-5, 18, 11);
		g.fillRect(this.xPos+7, this.yPos-4, 6, 9);
		g.setColor(Color.BLACK);
		g.setFont(new Font("Impact",Font.PLAIN,10));
		g.drawString("EMS", xPos-12, yPos+5);
		g.setColor(Color.red);
		g.fillRect(xPos+9, yPos-3, 2, 3);
		g.setColor(Color.blue);
		g.fillRect(xPos+9, yPos, 2, 3);
		if (this.xPos != homeX || this.yPos != homeY) {
			if (phase < 10) {
				g.setColor(new Color(255,0,0,50));
				g.fillOval(xPos+6, yPos-6, 9, 9);
			} else {
				g.setColor(new Color(0,0,255,50));
				g.fillOval(xPos+6, yPos-3, 9, 9);
			}
		}
		phase++;
		if (phase > 19) phase = 0;
	}

}
