package scenarios;

import entities.*;
import epidemic.UIManager;
import prefabs.*;

public class City implements Scenario{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6743163858059666577L;

	public void loadScenario() {
		
		Disease[] illnesses = {
				new Disease("SARS",10,10,336,168,true,true),
				new Disease("Common cold",10,1,12,72,false,true),
				new Disease("Influenza",10,5,24,120,false,true)
		};
		illnesses[0].Symptoms.add(new Effect() {

			@Override
			public void initial(agent A) {	
			}

			@Override
			public void cycle(int cycle, agent A) {
				if (cycle % 3 == 0) {
					A.respirations--;
				}
				if (cycle % 5 == 0) {
					A.lungCapacity--;
				}
			}
			
		});
		illnesses[1].Symptoms.add(new Effect() {

			@Override
			public void initial(agent A) {	
				if (A.respirations > 11) {
					A.respirations = 11;
				}
			}
			
			@Override
			public void cycle(int cycle, agent A) {
				
			}
			
		});
		illnesses[2].Symptoms.add(new Effect() {

			@Override
			public void initial(agent A) {	
			}
			
			@Override
			public void cycle(int cycle, agent A) {
				A.respirations--;
			}
			
		});
		
		for (int i = 0; i < 1000; i++) {
			if (Math.random() < 0.01) {
				int j = (int) (Math.random()*illnesses.length);
				new agent(0,0).Diseases.add(new DiseaseInstance(illnesses[j]));
			} else {
				new agent(0,0);
			}
		}
		new RoadGridGenerator(100,100);
		for (int i = 32; i < UIManager.WIDTH; i+=132) {
			for (int j = 16; j <= UIManager.HEIGHT; j+=132) {
				new standardBlockGenerator(i,j);
			}
		}
	}
	
	public String toString() {
		return "Procedural City";
	}

}
