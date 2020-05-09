package entities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import epidemic.UIManager;

public abstract class Grant {

	public static ArrayList<Grant> grants = new ArrayList<Grant>();

	public String name;
	public String description;
	public boolean accepted = false;
	private JPanel grantPanel = null;
	public boolean received = false;
	public boolean oneTime;

	public Grant(String n, String desc, boolean ot) {
		name = n;
		description = desc;
		oneTime = ot;
		grants.add(this);
	}
	
	private boolean offer() {
		return (!received || !oneTime);
	}


	JButton a;
	public void update() {
		if (isAvailible() && offer()) {
			if (grantPanel == null) {
				UIManager.GrantPanel.add(createGrantPanel());
				UIManager.GrantPanel.repaint();
			}
			a.setEnabled(applyConditions());
		} else { 
			if (grantPanel != null) {
				UIManager.GrantPanel.remove(grantPanel);
				UIManager.GrantPanel.repaint();
				grantPanel = null;
			}
			accepted = false;
		}
		if (accepted && completeConditions()) {
			onReward();
			received = true;
			accepted = false;
			UIManager.GrantPanel.remove(grantPanel);
			UIManager.GrantPanel.repaint();
			grantPanel = null;
		}
	}

	public JPanel createGrantPanel() {
		grantPanel = new JPanel(new BorderLayout());
		grantPanel.setPreferredSize(new Dimension(100,50));
		grantPanel.setBorder(BorderFactory.createLineBorder(Color.black));
		grantPanel.add(new JLabel(name), BorderLayout.NORTH);
		grantPanel.add(new JLabel(description), BorderLayout.WEST);
		a = new JButton("Apply");
		a.setEnabled(applyConditions());
		a.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				accepted = true;
				a.setEnabled(false);
			}

		});
		grantPanel.add(a,BorderLayout.EAST);
		return grantPanel;
	}

	public abstract boolean completeConditions();
	public abstract boolean applyConditions();
	public abstract boolean isAvailible();
	public abstract void onReward();

}
