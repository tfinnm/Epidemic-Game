package routines;

import entities.agent;

public class docRoutineC extends routine {

	private static final long serialVersionUID = -8091078403973640156L;

	@Override
	public void doCycle(agent A, int cycle) {
		if (cycle % 24 > 1 && cycle % 24 < 10 && !A.stayHome()) { 
				work(A,cycle);
		} else {
			if (cycle%24 == 13 && !A.stayHome()) {
				shop(A,cycle); 
			} else {
				home(A, cycle, (cycle % 24 > 14 && cycle % 24 < 22));
			}
		}
		
	}

}
