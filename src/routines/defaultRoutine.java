package routines;

import entities.agent;

public class defaultRoutine extends routine {

	private static final long serialVersionUID = -8091078403973640156L;

	@Override
	public void doCycle(agent A, int cycle) {
		if (cycle % 24 > 9 && cycle % 24 < 17 && !A.stayHome()) { 
			if (cycle/24 < 6) {
				work(A,cycle);
			} else {
				event(A,cycle);
			}
		} else {
			if (cycle%24 == 17 && !A.stayHome()) {
				shop(A,cycle); 
			} else {
				home(A, cycle, (cycle % 24 > 21 || cycle % 24 < 8));
			}
		}
		
	}

}
