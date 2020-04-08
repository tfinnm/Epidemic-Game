package entities;

import java.awt.Color;
import java.awt.Graphics;

public class marker extends Renderable{

	public int xPos;
	public int yPos;

	public static enum Style {
		desk,
		register,
		podium,
		kitchen,
		seat,
		bed,
		none
	}

	public Style style;

	public marker(int x,int y, Style s) {
		xPos = x;
		yPos = y;
		style = s;
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.black);
		if (style == Style.desk) {
			g.drawOval(xPos-2, yPos-2, 4, 4);
			g.drawRect(xPos-8, yPos-4, 4, 8);
		} else if (style == Style.kitchen) {
			g.drawRect(xPos-6, yPos-8, 12, 4);
		} else if (style == Style.podium) {
			g.drawRect(xPos-6, yPos-1, 2, 2);
		} else if (style == Style.register) {
			g.drawRect(xPos+4, yPos-4, 4, 8);
			int[] xPoints = {xPos+6,xPos+4,xPos+6,xPos+8};
			int[] yPoints = {yPos-4,yPos-2,yPos,yPos-2};
			g.drawPolygon(xPoints, yPoints, 4);
		} else if (style == Style.seat) {
			g.drawOval(xPos-2, yPos-2, 4, 4);
		} else if (style == Style.bed) {
			g.drawRect(xPos-2,yPos-5,4,10);
			g.drawRect(xPos-2, yPos-5, 4, 3);
		} else {
		}
	}

}
