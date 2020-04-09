package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import epidemic.UIManager;

public class agent extends clickable {

	public static ArrayList<agent> AgentHandler = new ArrayList<agent>();

	public ArrayList<DiseaseInstance> Diseases = new ArrayList<DiseaseInstance>();
	public ArrayList<Disease> Antibodies = new ArrayList<Disease>();

	public Job job = null;
	public House home = null;
	public Event event = null;
	public Comerce shop = null;
	public TriageChair FA = null;
	public BLSBed BLS = null;
	public ALSBed ALS = null;


	public int respirations = 16;
	public int lungCapacity = 20;
	public double immunity = 1.0;

	public boolean remove = false;
	public boolean remove2 = false;

	public void triage() {
		if (lungCapacity < 10 && ALS == null) {
			if (BLS != null) {
				BLS.patient = null;
				BLS = null;
			}
			for (ALSBed tempALS: ALSBed.beds) {
				if (tempALS.seakTreatment(this)) {
					System.out.println(this+" Sent ALS for "+respirations+"/"+lungCapacity);
					break;
				}
			}
		}
		if (respirations < 10 && ALS == null && BLS == null) {
			for (BLSBed tempBLS: BLSBed.beds) {
				if (tempBLS.seakTreatment(this)) {
					System.out.println(this+" Sent BLS for "+respirations+"/"+lungCapacity);
					break;
				}
			}
		}
		if (respirations < 12 && respirations < lungCapacity && BLS == null && ALS == null) {
			respirations++;
			System.out.println(this+" Treated FA for "+respirations+"/"+lungCapacity);
		}
		for (DiseaseInstance tempDI: Diseases) {
		}
	}

	int healthycycles = 0;
	public void BLS() {
		System.out.println(this+" Treated BLS for "+respirations+"/"+lungCapacity);
		if (respirations < 12) {
			healthycycles = 0;
			respirations = lungCapacity;
		} else {
			if (healthycycles < 1) {
				healthycycles++;
			} else {
				if (BLS != null) {
					BLS.patient = null;
					BLS = null;
				}
			}
		}
		triage();
	}

	public void ALS() {
		System.out.println(this+" Treated ALS for "+respirations+"/"+lungCapacity);
		if (lungCapacity  < 18) {
			lungCapacity++;
		} else {
			ALS.patient = null;
			ALS = null;
		}
		BLS();
	}

	public void health(int c) {
		if (ALS != null) {
			ALS();
		}
		if (BLS != null) {
			BLS();
		}
		for (DiseaseInstance DI: Diseases) {
			DI.advance(c, this);
		}
		if (respirations > lungCapacity) {
			respirations = lungCapacity;
		}
		if (respirations == 0) {
			remove = true;
		}
	}

	public void die() {
		if (remove && remove2) {
			if (job != null) {
				job.worker = null;
			}
			if (event != null) {
				event.Attendee = null;
			}
			if (shop != null) {
				shop.Customer = null;
			}
			if (FA != null) {
				FA.patient = null;
			}
			if (BLS != null) {
				BLS.patient = null;
			}
			if (ALS != null) {
				ALS.patient = null;
			}
			if (home != null) {
				if(gender == Gender.male) {
					home.man = null;
				} else {
					home.woman = null;
				}
			}
			AgentHandler.remove(this);
			clickable.ClickableHandler.remove(this);
		}
	}

	public void rdie() {
		if (remove) {
			UIManager.drawlist.remove(this);
			remove2 = true;
		}
	}

	public static enum Gender {
		male,
		female
	}

	public Gender gender;

	public agent(int x, int y) {
		super(x, y);
		agent.AgentHandler.add(this);
		if (Math.random()*2 < 1) {
			gender = Gender.male; 
		} else {
			gender = Gender.female;
		}
	}

	@Override
	public void clickedOn(int x, int y) {
		if (Math.sqrt(Math.pow(this.xPos-x,2)+Math.pow(this.yPos-y,2)) < 2) {
			System.out.println(this+": "+Diseases);
		}
	}

	@Override
	public void draw(Graphics g) {
		if (gender == Gender.male) {
			g.setColor(Color.blue);
		} else {
			g.setColor(Color.pink);
		}
		g.fillOval(this.xPos-2, this.yPos-2, 4, 4);
		if (UIManager.debug && Diseases.size() > 0) {
			g.setColor(Color.red);
			g.drawOval(this.xPos-4, this.yPos-4, 8, 8);
		}
	}



}
