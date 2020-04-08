package epidemic;

import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import entities.Renderable;

public class UIManager {

	public static ArrayList<Renderable> drawlist = new ArrayList<Renderable>();
	
	//heights
	public static final int WIDTH = 1280;
	public static final int HEIGHT = 800;
	static Panel ui = new Panel();
	public static JFrame frame;
	public static boolean debug = true;
	
	public static void createFrame() {
		frame = new JFrame("Epidemic");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLocation(0, 0);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(true);
		frame.setContentPane(ui);
		frame.setVisible(true);
		frame.setBackground(Color.white);
	}
	
	public static void main(String[] args) {
		createFrame();
		scenarios.Test.loadScenario();
		//scenarios.Olmsted.loadScenario();
		new Thread(new RenderLoop()).start();
	}

}
