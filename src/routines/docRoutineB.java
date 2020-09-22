package routines;

import entities.agent;

public class docRoutineB extends routine {

	private static final long serialVersionUID = -8091078403973640156L;

	@Override
	public void doCycle(agent A, int cycle) {
		if (cycle % 24 > 17 || cycle % 24 < 2 && !A.stayHome()) { 
				work(A,cycle);
		} else {
			if (cycle%24 == 16 && !A.stayHome()) {
				shop(A,cycle); 
			} else {
				home(A, cycle, (cycle % 24 > 5 && cycle % 24 < 15));
			}
		}
		
	}

}
