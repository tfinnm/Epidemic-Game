package entities;

import java.util.ArrayList;

public class Disease {

	String name;
	int InfRange;
	int InfChance;
	int Incubation;
	
	int knownEffects = 0;
	boolean hasTest = false;
	int testEffectiveness = 0;
	
	boolean hasCure = false;
	int cureEffectiveness = 0;
	
	public ArrayList<Effect> Symptoms = new ArrayList<Effect>();
	

	public Disease(String n, int r, int r0, int i) {
		name = n;
		InfRange = r;
		InfChance = r0;
		Incubation = i;
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
					if (!tempAgent.Antibodies.contains(this)) {
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
