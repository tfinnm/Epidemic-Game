package entities;

import java.awt.Graphics;

import epidemic.UIManager;

public abstract class Renderable {

	public Renderable() {
		UIManager.drawlist.add(this);
	}
	
	public abstract void draw(Graphics g);

}
