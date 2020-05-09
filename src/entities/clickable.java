package entities;

import java.awt.Graphics;
import java.util.ArrayList;

public abstract class clickable extends Renderable {

	public static ArrayList<clickable> ClickableHandler = new ArrayList<clickable>();
	
	public int xPos;
	public int yPos;

	public clickable() {
		super();
		xPos=0;
		yPos=0;
		clickable.ClickableHandler.add(this);
		
	}
	
	public clickable(int x, int y) {
		super();
		xPos=x;
		yPos=y;
		clickable.ClickableHandler.add(this);
	}

	public abstract void clickedOn(int x1,int y1);
	
	public abstract void draw(Graphics g);

}
