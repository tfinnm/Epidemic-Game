package epidemic;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import entities.clickable;


@SuppressWarnings("serial")
public class Panel extends JPanel{

	private BufferedImage image =  new BufferedImage(1280, 800, BufferedImage.TYPE_INT_RGB);
	public Graphics g = image.getGraphics();
	
	public Panel() {

		setFocusable(true);
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				for(clickable temp: clickable.ClickableHandler) {
					temp.clickedOn(arg0.getX(), arg0.getY());
				}
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}			
		});


	}
	
	public  void paintComponent(Graphics g) {
		g.drawImage(image, 0, 0, UIManager.frame.getWidth(), UIManager.frame.getHeight(), null);
	}

	
}
