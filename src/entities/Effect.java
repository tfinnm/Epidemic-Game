package entities;

public abstract class Effect {
	
	public abstract void initial(agent A);
	
	public abstract void cycle(int cycle, agent A);

}
