package entities;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;

public class Disease implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5238678409006557154L;

	public static ArrayList<Disease> DiseaseHandler = new ArrayList<Disease>();

	public String name;
	public int InfRange;
	public int InfChance;
	public int Incubation;
	
	public boolean selfCure = true;
	public int cyclesToCure;

	public int knownEffects = 0;
	public boolean hasTest = false;
	public double testEffectiveness = 0;
	public boolean alwaysDiagnose = false;

	public boolean hasCure = false;
	public int cureEffectiveness = 0;
	public Dificulty cureDificulty;
	public boolean canResearchCure = true;
	public boolean canBeImmune;

	public int researchDificulty = 10;
	public boolean researchingEffects = false;
	public boolean researchingTest = false;
	public boolean researchingCure = false;
	public boolean showResearchPanel;

	public JCheckBox researchEffects;
	public JCheckBox researchTest;
	public JCheckBox researchCure;
	public JCheckBox eliminate;
	public JLabel ETC;
	public ITrace2D ConfirmedCases;
	public boolean canHaveCure = true;
	public JPanel generateResearchPanel(JPanel p) {
		JPanel rc = new JPanel(new BorderLayout());
		p.setLayout(new BorderLayout());
		p.add(rc,BorderLayout.SOUTH);
		researchEffects = new JCheckBox("Research Symptoms");
		rc.add(researchEffects,BorderLayout.WEST);
		researchTest = new JCheckBox("Research Test");
		rc.add(researchTest,BorderLayout.CENTER);
		researchCure = new JCheckBox("Research Cure");
		rc.add(researchCure,BorderLayout.SOUTH);
		rc.add(new JLabel("Research (Costs $100/hr)"),BorderLayout.NORTH);
		eliminate = new JCheckBox("Eliminate Confirmed");
		rc.add(eliminate,BorderLayout.EAST);
		ETC = new JLabel();
		updateETC();
		p.add(ETC,BorderLayout.NORTH);
		Chart2D CasesChart = new Chart2D();
		ConfirmedCases = new Trace2DLtd(720);
		ConfirmedCases.setColor(Color.orange);
		ConfirmedCases.setName("Confirmed Cases");
		CasesChart.addTrace(ConfirmedCases);
		p.add(CasesChart,BorderLayout.CENTER);
		ConfirmedCases.addPoint(0,0);
		return p;
	}

	public void research() {
		if (researchEffects.isSelected()) {
			if (Job.bankBalance > 100) {
				Job.changeBalance(-100);
				if (researchingEffects) {
					if (knownEffects < Symptoms.size()) {
						if ((Math.random()*(researchDificulty+knownEffects)) < 1) {
							knownEffects++;
						}
					}
				} else {
					researchingEffects = true; //this creates a one cycle delay before research begins to discourage starting and stopping research
				}
			} else {
				researchingEffects = false;
			}
		} else {
			researchingEffects = false;
		}
		if (researchTest.isSelected()) {
			if (Job.bankBalance > 100) {
				Job.changeBalance(-100);
				if (researchingTest) {
					if (testEffectiveness < 1.0) {
						if ((Math.random()*(researchDificulty*((Symptoms.size()-knownEffects)+1))) < 1) {
							hasTest = true;
							double t = (Math.random()/10.0);
							t -= (t%0.0001);
							testEffectiveness += t;
						}
					}
				} else {
					researchingTest = true; //this creates a one cycle delay before research begins to discourage starting and stopping research
				}
			} else {
				researchingTest = false;
			}
		} else {
			researchingTest = false;
		}
		if (researchCure.isSelected()) {
			if (Job.bankBalance > 100) {
				Job.changeBalance(-100);
				if (researchingCure) {
					if (canHaveCure) {
						if (cureEffectiveness < 100 || cureDificulty == Dificulty.Surgery) {
							if ((Math.random()*(researchDificulty*((Symptoms.size()-knownEffects)+1))) < 1) {
								Dificulty tempDificulty = (Math.random() < 0.5) ? Dificulty.Pharma:Dificulty.Surgery;
								int tempeffectiveness = (int)(Math.random()*100);
									if (cureEffectiveness < 75 || cureDificulty == Dificulty.Surgery) {
										if (tempeffectiveness > cureEffectiveness) {
											cureDificulty = tempDificulty;
											cureEffectiveness = tempeffectiveness;
										}
									} else if (tempDificulty == Dificulty.Pharma && tempeffectiveness > cureEffectiveness) {
										cureDificulty = tempDificulty;
										cureEffectiveness = tempeffectiveness;
									}
								hasCure = true;
							}
						}
					}
				} else {
					researchingCure = canResearchCure; //this creates a one cycle delay before research begins to discourage starting and stopping research
				}
			} else {
				researchingCure = false;
			}
		} else {
			researchingCure = false;
		}
		updateETC();
	}

	public void updateETC() {
		String s = "Known Symptoms: "+knownEffects;
		if (hasTest) {
			s += " | Test Effectiveness: "+(testEffectiveness*100)+"%";
		} else {
			s += " | No test";
		}
		if (hasCure) {
			if (cureDificulty == Dificulty.FA) {
				s += " | First Aid Cure ("+cureEffectiveness+"%)";
			} else if (cureDificulty == Dificulty.Pharma) {
				s += " | Drug Cure ("+cureEffectiveness+"%)";
			} else if (cureDificulty == Dificulty.Surgery) {
				s += " | Surgery Cure ("+cureEffectiveness+"%)";
			}
		} else {
			s += " | No Cure";
		}
		ETC.setText(s);
	}

	public static enum Dificulty {
		FA,
		Pharma,
		Surgery
	}

	public ArrayList<Effect> Symptoms = new ArrayList<Effect>();


	public Disease(String n, int r, int r0, int i, int c, boolean immunable, boolean panel) {
		name = n;
		InfRange = r;
		InfChance = r0;
		Incubation = i;
		canBeImmune = immunable;
		showResearchPanel = panel;
		cyclesToCure = c;
		Disease.DiseaseHandler.add(this);
	}

	public void spread(agent A, DiseaseInstance d) {
		if (d.cycle < Incubation) {
			d.cycle++;
		} else {
			//symptoms here
		}
		for (agent tempAgent: agent.AgentHandler) {
			if (tempAgent != A) {
				if (Math.sqrt(Math.pow(A.xPos-tempAgent.xPos,2)+Math.pow(A.yPos-tempAgent.yPos,2)) < InfRange) {
					if (!tempAgent.Antibodies.contains(this) && !tempAgent.hasPPE) {
						boolean has = false;
						for (DiseaseInstance td: tempAgent.Diseases) {
							if (td.virus == this) has = true;
						}
						if ((!has) && (Math.random()*(InfChance*tempAgent.immunity)) < 1) {
							tempAgent.Diseases.add(new DiseaseInstance(this));
						}
					}
				}
			}
		}
	}

	public boolean equals(Object temp) {
		Disease t = (Disease) temp;
		if (name.equals(t.name)) {
			return true;
		}
		return false;
	}

}
