package eg.edu.alexu.csd.oop.game;

import java.io.Serializable;
import java.util.Stack;

public interface IntersectionState extends Serializable{
	
	public boolean intersect(GameObject go1, Stack<GameObject> list, int stick, GameObject go2, Circus circus);

}
