package eg.edu.alexu.csd.oop.game;

import java.io.Serializable;

public class Intersection implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IntersectionState empty;
	private IntersectionState hasElement;
	private IntersectionState current;
	
	public Intersection() {
		empty = new EmptyState(this);
		hasElement = new HasElementState(this);
		current = empty;
	}
	
	public void setCurrentState(IntersectionState state){
		current = state;
	}
	
	public IntersectionState getEmptyState(){
		return empty;
	}
	
	public IntersectionState getHasElementState(){
		return hasElement;
	}
	
	public IntersectionState getCurrentState(){
		return current;
	}

}
