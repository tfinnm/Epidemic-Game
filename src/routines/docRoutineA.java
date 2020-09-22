package routines;

import entities.agent;

public class docRoutineA extends routine {

	private static final long serialVersionUID = -8091078403973640156L;

	@Override
	public void doCycle(agent A, int cycle) {
		if (cycle % 24 > 9 && cycle % 24 < 18 && !A.stayHome()) { 
				work(A,cycle);
		} else {
			if (cycle%24 == 18 && !A.stayHome()) {
				shop(A,cycle); 
			} else {
				home(A, cycle, (cycle % 24 > 21 || cycle % 24 < 8));
			}
		}
		
	}

}
