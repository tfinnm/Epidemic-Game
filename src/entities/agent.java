package entities;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import entities.Disease.Dificulty;
import epidemic.UIManager;
import routines.*;

public class agent extends clickable implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2153089776671922788L;

	public static int newAgents = 0;

	public static int deaths = 0;
	public static int dailyDeaths = 0;

	public static ArrayList<agent> AgentHandler = new ArrayList<agent>();
	public static enum trait {
		stubbern, //will not stay home if sick
		antiestablishment, //will not seek medical care, will only show up in hospital if dies and grabbed by ambu
		tough, //will have to reach a lower breath count to seek medical care, probably not effected by autumn fever
		social //will ignore gatherings limited and still go to activities
	}

	public static int maxTrace = 72;
	public ArrayList<DiseaseInstance> Diseases = new ArrayList<DiseaseInstance>();
	public ArrayList<Disease> Antibodies = new ArrayList<Disease>();
	public ArrayList<trait> traits = new ArrayList<trait>();
	public Queue<int[]> contactTrace = new LinkedList<int[]>();
	public boolean prescribed = false;
	public void LogMarker(int x, int y) {
		int[] data = {x,y};
		contactTrace.add(data);
		while (contactTrace.size() > maxTrace) {
			contactTrace.poll();
		}
	}


	public Job job = null;
	public routine Routine = new defaultRoutine();
	public House home = null;
	public Event event = null;
	public Comerce shop = null;
	public TriageChair FA = null;
	public BLSBed BLS = null;
	public ALSBed ALS = null;
	public boolean hasPPE = false;

	public int respirations = 16;
	public int lungCapacity = 20;
	public double immunity = 1.0;
	
	public int happyness = 100;
	public int balance = 0;
	

	public boolean remove = false;

	public void usePPE() {
		if (UIManager.usePPE.isSelected() && TriageChair.PPE > 0) {
			TriageChair.PPE--;
			UIManager.PPEAvailible.setText("Availible: "+TriageChair.PPE);
			hasPPE = true;
		}
	}

	public boolean stayHome() {
		if (traits.contains(trait.stubbern)) {
			return false;
		}
		for (DiseaseInstance di: Diseases) {
			if (di.diagnosed) return true;
		}
		return false;
	}

	public void cure(DiseaseInstance i) {
		i.cured = true;
		UIManager.log(this + " Cured of "+i.virus.name);
		if (i.virus.canBeImmune && Math.random() > 0.9) {
			Antibodies.add(i.virus);
		}
	}

	public void triage() {
		if (lungCapacity < 10 && ALS == null) {
			if (BLS != null) {
				BLS.patient = null;
				BLS = null;
			}
			for (ALSBed tempALS: ALSBed.beds) {
				if (tempALS.seakTreatment(this)) {
					UIManager.log(this+" Sent ALS for "+respirations+"/"+lungCapacity);
					break;
				}
			}
		}
		if (respirations < 10 && ALS == null && BLS == null) {
			boolean cared = false;
			for (BLSBed tempBLS: BLSBed.beds) {
				if (tempBLS.seakTreatment(this)) {
					UIManager.log(this+" Sent BLS for "+respirations+"/"+lungCapacity);
					cared = true;
					break;
				}
			}
			if (!cared && respirations < 5) {
				for (ALSBed tempALS: ALSBed.beds) {
					if (tempALS.seakTreatment(this)) {
						UIManager.log(this+" Sent ALS for "+respirations+"/"+lungCapacity+" (no BLS availible)");
						break;
					}
				}
			}
		}
		if (respirations < 12 && respirations < lungCapacity && BLS == null && ALS == null) {
			respirations++;
			UIManager.log(this+" Treated FA for "+respirations+"/"+lungCapacity);
		}
		for (int i = 0; i < Diseases.size(); i++) {
			DiseaseInstance tempDI = Diseases.get(i);
			if (!tempDI.diagnosed) {
				double effectiveness = (1.0*tempDI.virus.knownEffects)/(1.0*tempDI.virus.Symptoms.size());
				effectiveness /= 4;
				if(tempDI.virus.hasTest) {
					effectiveness += (tempDI.virus.testEffectiveness);
				}
				if (tempDI.virus.alwaysDiagnose || Math.random() < effectiveness) {
					tempDI.diagnosed = true;
					UIManager.log(this+" Diagnosed with "+tempDI.virus.name);
				}
			}
			if (tempDI.diagnosed) {
				if (tempDI.virus.eliminate.isSelected()) {
					remove = true;
					UIManager.log(this+" \"Removed\"");
					deaths++;
					dailyDeaths++;
				}
				if (tempDI.virus.hasCure) {
					if (tempDI.virus.cureDificulty == Dificulty.FA) {
						if (Math.random()*100 < tempDI.virus.cureEffectiveness) {
							cure(tempDI);
							i--;
						}
					} else {
						if (tempDI.virus.cureDificulty == Dificulty.Surgery) {
							for (TreatmentRoom tempTR: TreatmentRoom.TRHandler) {
								if (tempTR.seakTreatment(this)) {
									UIManager.log(this+" Sent Treatment Surgery");
									break;
								}
							}

							break;
						}
						if (tempDI.virus.cureDificulty == Dificulty.Pharma) {
							prescribed = true;
						}
					}
				}
			}
		}
	}

	int healthycycles = 0;
	public void BLS() {
		UIManager.log(this+" Treated BLS for "+respirations+"/"+lungCapacity);
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

	public void revive() {
		UIManager.log(this+" Revived for "+respirations+"/"+lungCapacity);
		if (lungCapacity < 6) {
			lungCapacity = 6;
		}
		respirations = lungCapacity;
	}

	public void ALS() {
		UIManager.log(this+" Treated ALS for "+respirations+"/"+lungCapacity);
		if (lungCapacity  < 18) {
			lungCapacity++;
		} else {
			ALS.patient = null;
			ALS = null;
		}
		BLS();
	}

	public boolean health(int c) {
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
		if (respirations <= 0) {
			boolean careGiven = false;
			if (BLS != null || ALS != null) {
				for (RevivalRoom tempRR: RevivalRoom.CCHandler) {
					if (tempRR.seakTreatment(this)) {
						if (BLS != null) {
							BLS.patient = null;
							BLS = null;
						}
						if (ALS != null) {
							ALS.patient = null;
							ALS = null;
						}
						careGiven = true;
						UIManager.log(this+" Admitted to ED");
						break;
					}
				}
			} 
			if (!careGiven){	
				for (Ambulance tempAmbu: Ambulance.dispatch) {
					if (tempAmbu.seakTreatment(this)) {
						UIManager.log(this+" Stabalized");
						careGiven = true;
						for (RevivalRoom tempRR: RevivalRoom.CCHandler) {
							if (tempRR.seakTreatment(this)) {
								if (BLS != null) {
									BLS.patient = null;
									BLS = null;
								}
								if (ALS != null) {
									ALS.patient = null;
									ALS = null;
								}
								UIManager.log(this+" Admitted to ED");
								break;
							}
						}
						break;
					}
				}
			}
			if (!careGiven) {
				for (FireTruck tempAmbu: FireTruck.dispatch) {
					if (tempAmbu.seakTreatment(this)) {
						UIManager.log(this+" Stabalized");
						careGiven = true;
						break;
					}
				}	
			}
			if (!careGiven) {
				remove = true;
				UIManager.log(this+" Died");
				deaths++;
				dailyDeaths++;
			}
			return false;
		}
		return true;
	}

	public boolean die() {
		if (remove) {
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
			UIManager.drawlist.remove(this);
			return true;
		}
		return false;
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
		if (Math.random() < 0.01) {
			traits.add(trait.antiestablishment);
		}
		if (Math.random() < 0.1) {
			traits.add(trait.tough);
		}
		if (Math.random() < 0.05) {
			traits.add(trait.stubbern);
		}
		if (Math.random() < 0.02) {
			traits.add(trait.social);
		}
		for (Disease d: Disease.DiseaseHandler) {
			if (d.hasVaccine && d.vaccinateNew.isSelected() && Job.bankBalance > 5) {
				Job.changeBalance(-5);
				if (d.canBeImmune && Math.random() < d.vaccineEffectiveness) {
					Antibodies.add(d);
				}
			}
		}
	}

	@Override
	public void clickedOn(int x, int y) {
		if (Math.sqrt(Math.pow(this.xPos-x,2)+Math.pow(this.yPos-y,2)) < 2) {
			UIManager.log(this+": "+Diseases);
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
		//		for (DiseaseInstance t: Diseases) {
		//			if (t.diagnosed) {
		//				g.drawOval(this.xPos-1, this.yPos-1, 2, 2);
		//			}
		//		}
		if (UIManager.debug && Diseases.size() > 0) {
			g.setColor(Color.red);
			g.drawOval(this.xPos-4, this.yPos-4, 8, 8);
		}
		if (hasPPE) {
			g.setColor(Color.CYAN);
			g.drawOval(xPos-3, yPos-3, 6, 6);
		}

		for (DiseaseInstance di: Diseases) {
			if (di.diagnosed && di.virus.trace.isSelected()) {
				for(int[] c: contactTrace) {
					UIManager.ui.g.setColor(Color.red);
					UIManager.ui.g.drawOval(c[0]-5, c[1]-5, 10, 10);
				}
			}
		}
	}



}
