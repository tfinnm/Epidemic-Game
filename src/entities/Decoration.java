package entities;

import java.awt.Color;
import java.awt.Graphics;

public class Decoration extends Renderable {

	int xPos;
	int yPos;
	int size;
	
	public static enum Design {
		confrenceTable,
		wallHor,
		wallVert,
		dividerHor,
		dividerVert,
		shelf
	}
	
	private Design design;
	
	public Decoration(int x,int y, int s, Design d) {
		xPos = x;
		yPos = y;
		design = d;
		size = s;
	}

	@Override
	public void draw(Graphics g) {
		if (design == Design.confrenceTable) {
			g.setColor(Color.black);
			int s = size*8;
			g.drawOval(xPos-4, yPos-s/2, 8, s);
		} else if (design == Design.wallHor) {
			g.setColor(Color.black);
			g.drawRect(xPos, yPos, size, 0);
		} else if (design == Design.wallVert) {
			g.setColor(Color.black);
			g.drawRect(xPos, yPos, 0, size);
		} else if (design == Design.dividerHor) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(xPos, yPos, size, 0);
		} else if (design == Design.dividerVert) {
			g.setColor(Color.LIGHT_GRAY);
			g.drawRect(xPos, yPos, 0, size);
		} else if (design == Design.shelf) {
			g.setColor(Color.black);
			int s = 1+2*size;
			g.drawRect(xPos, yPos, 3, s);
			for (int i = 1; i < s; i += 2) {
				if ((i-1)/2%3 == 1) {
					g.setColor(Color.red);
				} else if ((i-1)/2%3 == 2) {
					g.setColor(Color.green);
				} else if ((i-1)/2%3 == 0) {
					g.setColor(Color.blue);
				}				
				g.fillRect(xPos+1, yPos+i, 2, 2);
			}
		}
	}

}
