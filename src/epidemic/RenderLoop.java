package epidemic;

import java.awt.Color;
import java.awt.Graphics;

import entities.*;

public class RenderLoop implements Runnable{

	private Graphics g;

	@Override
	public void run() {
		g = UIManager.ui.g;
		
		new Thread(new CycleManager()).start();
		while (true) {
			//code goes here
			g.setColor(new Color(255,255,255));
			g.fillRect(0, 0, UIManager.WIDTH, UIManager.HEIGHT);
			
			for (int i = 0; i < agent.AgentHandler.size(); i++) {
				agent.AgentHandler.get(i).rdie();
			}
			
			for (Renderable temp: UIManager.drawlist) {
				temp.draw(g);
			}
	
			g.setColor(Color.red);
			g.drawString("Cycle "+CycleManager.getCycle(), 10, 10);
			g.drawString("day "+CycleManager.getCycle()/24+" "+((CycleManager.getCycle()%12)+1)+":00", 10, 20);
			
			UIManager.ui.repaint();
			//System.out.print("hello");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
