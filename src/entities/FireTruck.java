package entities;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.ArrayList;

public class FireTruck extends marker {

	public static ArrayList<FireTruck> dispatch = new ArrayList<FireTruck>();
	
	int phase = 0;

	public int homeX;
	public int homeY;

	public FireTruck(int x, int y) {
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
		g.drawRect(this.xPos-16, this.yPos-6, 22, 12);
		g.drawRect(this.xPos+6, this.yPos-6, 4, 12);
		g.drawRect(this.xPos+11, this.yPos-5, 7, 10);
		g.fillRect(this.xPos+12, this.yPos-6, 3, 1);
		g.fillRect(this.xPos-14, this.yPos-7, 3, 1);
		g.fillRect(this.xPos+12, this.yPos+6, 3, 1);
		g.fillRect(this.xPos-14, this.yPos+7, 3, 1);
		g.setColor(Color.DARK_GRAY);
		g.fillRect(this.xPos+6, this.yPos-6, 4, 12);
		g.fillRect(this.xPos+11, this.yPos-5, 7, 10);
		g.setColor(new Color(163,30,30));
		g.fillRect(this.xPos-16, this.yPos-6, 22, 12);
		g.setColor(Color.red);
		//g.fillRect(this.xPos-12, this.yPos-6, 24, 12);
		g.setColor(Color.black);
		g.setFont(new Font("Impact",Font.PLAIN,10));
		g.drawString("FIRE", xPos-14, yPos+5);
		g.setColor(Color.red);
		g.fillRect(xPos+11, yPos-3, 2, 3);
		g.setColor(Color.blue);
		g.fillRect(xPos+11, yPos, 2, 3);
		if (this.xPos != homeX || this.yPos != homeY) {
			if (phase < 20) {
				g.setColor(new Color(255,0,0,50));
				g.fillOval(xPos+7, yPos-6, 8, 8);
			} else {
				g.setColor(new Color(0,0,255,50));
				g.fillOval(xPos+7, yPos-2, 8, 8);
			}
			g.setColor(new Color(255,0,0,50));
			g.fillArc(xPos-20, yPos-11, 10, 10, 9*phase, 90);
			g.fillArc(xPos-20, yPos+1, 10, 10, -9*phase, 90);
		}
		phase++;
		if (phase > 49) phase = 0;
	}

}
