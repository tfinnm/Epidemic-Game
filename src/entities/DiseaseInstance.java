package entities;

public class DiseaseInstance {

	public Disease virus;
	public int cycle = 0;
	
	public DiseaseInstance(Disease d) {
		virus = d;
	}
	
	public void advance(int c,agent A) {
		if (cycle < virus.Incubation) {
			cycle++;
		} else if (cycle == virus.Incubation) {
			for (Effect e: virus.Symptoms) {
				e.initial(A);
				e.cycle(c,A);
			}
			cycle++;
		} else if (cycle > virus.Incubation) {
			for (Effect e: virus.Symptoms) {
				e.cycle(c,A);
			}
		}
	}

}
