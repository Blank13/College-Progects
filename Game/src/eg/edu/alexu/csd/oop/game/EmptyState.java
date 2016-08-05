package eg.edu.alexu.csd.oop.game;

import java.util.Stack;

public class EmptyState implements IntersectionState{
	
	private Intersection intersection;
	
	public EmptyState(Intersection i) {
		intersection = i;
	}

	@Override
	public boolean intersect(GameObject go1, Stack<GameObject> list, int stick, GameObject go2, Circus circus) {
		boolean intersect = false;
		// check intersection with moving object go2 and clown stick
		if(stick == 0){
			intersect = (Math.abs((go1.getX()+36/2) - (go2.getX()+go2.getWidth()/2)) <= 2*36/3 && Math.abs((go2.getY()+go2.getHeight()) - (go1.getY())) < 3 );
		}else{
			intersect = (Math.abs((go1.getX()+ go1.getWidth() - 36/2) - (go2.getX()+go2.getWidth()/2)) <= 36/2 && Math.abs((go2.getY()+go2.getHeight()) - (go1.getY())) < 3);
		}
		if(intersect){
			intersection.setCurrentState(intersection.getHasElementState());
			list.push(go2);
			circus.notifyObservers(go2);
			if(stick == 0){
				go2.setX(go1.getX() - 8);
			}else{
				go2.setX(go1.getX() + go1.getWidth() - (go2.getWidth() - 8));
			}
		}
		return intersect;
	}

}
