package entities;

import java.awt.Color;
import java.awt.Graphics;

public class building extends clickable {

	private int width;
	private int height;
	private String Name;
	
	public building(int x, int y, int w, int h, String name) {
		super(x,y);
		width = w;
		height = h;
		Name = name;
	}
	
	public void clickedOn(int x,int y) {
		if (x > this.xPos && x < this.xPos+width && y > this.yPos && y < this.yPos+height) {
			System.out.println("Clicked on "+this);
		}
	}
	
	public String toString() {
		return this.getClass()+": "+Name;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(this.xPos, this.yPos, width, height);
	}

}
