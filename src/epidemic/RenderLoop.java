package epidemic;

import java.awt.Color;
import java.awt.Graphics;


import entities.*;

public class RenderLoop implements Runnable{

	private Graphics g;

	@Override
	public void run() {
		g = UIManager.ui.g;

		while (true) {
			//code goes here
			g.setColor(new Color(255,255,255));
			g.fillRect(0, 0, UIManager.WIDTH, UIManager.HEIGHT);

			//for (Renderable temp: UIManager.drawlist) { //concurrent modification exception
			for (int i =0; i < UIManager.drawlist.size(); i++) {
				if (UIManager.drawlist.get(i) != null) UIManager.drawlist.get(i).draw(g);
			}

			g.setColor(Color.red);
			if (UIManager.debug) g.drawString("Cycle "+CycleManager.getCycle(), 10, 10);
			g.drawString("day "+CycleManager.getCycle()/24+" "+((CycleManager.getCycle()%12)+1)+":00", 10, 20);
			g.drawString("Deaths "+agent.deaths, 10, 30);
	
			
			UIManager.ui.repaint();

			if (Thread.interrupted()) {
				updateAcrossThreads();
			}
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				updateAcrossThreads();
			}
		}
	}
	
	private void updateAcrossThreads() {
		if (UIManager.masstest) {
			if (Job.bankBalance > agent.AgentHandler.size()*10) {
				Job.changeBalance(-agent.AgentHandler.size()*10);
				for(agent a: agent.AgentHandler) {
					a.triage();
				}
			}
			UIManager.masstest = false;
		}
		for (int i = 0; i < agent.AgentHandler.size(); i++) {
			if (agent.AgentHandler.get(i).die()) {
				i--;
			}
		}
		for (int i = 0; i < agent.newAgents; i++) {
			new agent(0,0);
			UIManager.log("New Agent");
			agent.newAgents = 0;
		}
		UIManager.Population.addPoint(UIManager.Population.getMaxX()+1, agent.AgentHandler.size());
		UIManager.Deaths.addPoint(UIManager.Deaths.getMaxX()+1, agent.deaths);
		UIManager.Money.addPoint(UIManager.Money.getMaxX()+1, Job.bankBalance);
		UIManager.PPETrace.addPoint(UIManager.PPETrace.getMaxX()+1, TriageChair.PPE);
		int sum = 0;
		for (Disease d: Disease.DiseaseHandler) {
			int cases = 0;
			for (int j = 0; j < agent.AgentHandler.size(); j++) {
				for (DiseaseInstance i: agent.AgentHandler.get(j).Diseases) {
					if (i.virus == d && i.diagnosed) {
						cases++;
						sum++;
						//System.out.print("hello");
					}
				}
			}
			d.ConfirmedCases.addPoint(d.ConfirmedCases.getMaxX()+1, cases);
		}
		UIManager.knownInfected.addPoint(UIManager.knownInfected.getMaxX()+1, sum);
		int tc = 0; 
		for (TriageChair t: TriageChair.TriageHandler) {
			if (t.patient == null) tc++;
		}
		UIManager.Doctors.addPoint(UIManager.Doctors.getMaxX()+1, tc);
		tc = 0; 
		for (BLSBed t: BLSBed.beds) {
			if (t.patient == null) tc++;
		}
		UIManager.Beds.addPoint(UIManager.Beds.getMaxX()+1, tc);
		tc = 0; 
		for (ALSBed t: ALSBed.beds) {
			if (t.patient == null) tc++;
		}
		UIManager.ICU.addPoint(UIManager.ICU.getMaxX()+1, tc);
		tc = 0; 
		for (Ambulance t: Ambulance.dispatch) {
			if (t.xPos == t.homeX && t.yPos == t.homeY) tc++;
		}
		for (FireTruck t: FireTruck.dispatch) {
			if (t.xPos == t.homeX && t.yPos == t.homeY) tc++;
		}
		UIManager.ambus.addPoint(UIManager.ambus.getMaxX()+1, tc);
		tc = 0; 
		for (RevivalRoom t: RevivalRoom.CCHandler) {
			if (t.patient == null) tc++;
		}
		UIManager.resus.addPoint(UIManager.resus.getMaxX()+1, tc);
		tc = 0; 
		for (TreatmentRoom t: TreatmentRoom.TRHandler) {
			if (t.patient == null) tc++;
		}
		UIManager.treat.addPoint(UIManager.treat.getMaxX()+1, tc);
		tc = 0; 
		for (pharmacy t: pharmacy.PharmaHandler) {
			if (t.patient == null) tc++;
		}
		UIManager.pharm.addPoint(UIManager.pharm.getMaxX()+1, tc);
	}

}
