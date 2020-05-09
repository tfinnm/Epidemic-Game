package entities;

import java.io.Serializable;

public class DiseaseInstance implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7736050064905674909L;
	public Disease virus;
	public int cycle = 0;
	public boolean diagnosed = false;
	public boolean cured = false;
	int cyclestoremove;
	
	public DiseaseInstance(Disease d) {
		virus = d;
		cyclestoremove = d.cyclesToCure;
	}
	
	public void advance(int c,agent A) {
		if (cycle < virus.Incubation) {
			cycle++;
		} else if (cycle == virus.Incubation) {
			for (Effect e: virus.Symptoms) {
				e.initial(A);
				e.cycle(c,A);
			}
			cyclestoremove--;
			cycle++;
		} else if (cycle > virus.Incubation) {
			for (Effect e: virus.Symptoms) {
				e.cycle(c,A);
			}
			cyclestoremove--;
		}
		if (cyclestoremove < 1) {
			cured = true;
		}
	}

}
