package entities;

import java.awt.Graphics;

public class RoadNode extends Renderable{

	public RoadNode north = null;
	public RoadNode south = null;
	public RoadNode east = null;
	public RoadNode west = null;

	public int xPos;
	public int yPos;

	public RoadNode(int x,int y) {
		super();
		xPos = x;
		yPos = y;
	}

	public void draw(Graphics g) {
		if (north != null) {
			g.drawLine(xPos-16, yPos-16, xPos-16, yPos-24);	
			g.drawLine(xPos+16, yPos-16, xPos+16, yPos-24);
			g.drawLine(xPos, yPos-20, xPos, yPos-24);
		} else {
			g.drawLine(xPos+16, yPos-16, xPos-16, yPos-16);
		}
		if (south != null) {
			g.drawLine(xPos+16, yPos+16, xPos+16, yPos+24);	
			g.drawLine(xPos-16, yPos+16, xPos-16, yPos+24);
			g.drawLine(xPos, yPos+20, xPos, yPos+24);
		} else {
			g.drawLine(xPos+16, yPos+16, xPos-16, yPos+16);
		}
		if (east != null) {
			g.drawLine(xPos+16, yPos+16, xPos+24, yPos+16);	
			g.drawLine(xPos+16, yPos-16, xPos+24, yPos-16);
			g.drawLine(xPos+20, yPos, xPos+24, yPos);
		} else {
			g.drawLine(xPos+16, yPos-16, xPos+16, yPos+16);
		}
		if (west != null) {
			g.drawLine(xPos-16, yPos-16, xPos-24, yPos-16);	
			g.drawLine(xPos-16, yPos+16, xPos-24, yPos+16);
			g.drawLine(xPos-20, yPos, xPos-24, yPos);
		} else {
			g.drawLine(xPos-16, yPos-16, xPos-16, yPos+16);
		}
		if (south != null) {
			int x = south.xPos;
			int y = south.yPos;
			if (south.north == this) {
				g.drawLine(xPos, yPos+24, x, y-24);
				g.drawLine(xPos+16, yPos+24, x+16, y-24);
				g.drawLine(xPos-16, yPos+24, x-16, y-24);
			} else if (south.west == this) {
				g.drawLine(xPos, yPos+24, x-24, y);
				g.drawLine(xPos+16, yPos+24, x-24, y-16);
				g.drawLine(xPos-16, yPos+24, x-24, y+16);
			}
		}
		if (west != null && west.north == this) {
			int x = west.xPos;
			int y = west.yPos;
			g.drawLine(xPos-24, yPos, x, y-24);
			g.drawLine(xPos-24, yPos+16, x+16, y-24);
			g.drawLine(xPos-24, yPos-16, x-16, y-24);
		}
		if (east != null) {
			int x = east.xPos;
			int y = east.yPos;
			if (east.north == this) {
				g.drawLine(xPos+24, yPos, x, y-24);
				g.drawLine(xPos+24, yPos+16, x-16, y-24);
				g.drawLine(xPos+24, yPos-16, x+16, y-24);
			} else if (east.west == this) {
				g.drawLine(xPos+24, yPos, x-24, y);
				g.drawLine(xPos+24, yPos+16, x-24, y+16);
				g.drawLine(xPos+24, yPos-16, x-24, y-16);
			} else if (east.south == this) {
				g.drawLine(xPos+24, yPos, x, y+24);
				g.drawLine(xPos+24, yPos+16, x+16, y+24);
				g.drawLine(xPos+24, yPos-16, x-16, y+24);
			}
		} 
	}

}
