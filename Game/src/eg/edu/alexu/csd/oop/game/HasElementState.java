package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.util.Stack;

public class HasElementState implements IntersectionState{

	private Intersection intersection;
	
	public HasElementState(Intersection i) {
		intersection = i;
	}
	
	@Override
	public boolean intersect(GameObject go1, Stack<GameObject> list, int stick, GameObject go2, Circus circus) {
		boolean intersect = false;
		GameObject o = list.peek();
		// check intersection with moving object go2 and stack with clown
		intersect = (Math.abs((o.getX()+o.getWidth()/2) - (go2.getX()+go2.getWidth()/2)) <= 2*o.getWidth()/3 && Math.abs((go2.getY()+go2.getHeight()) - (o.getY())) < 10 );
		if(intersect){
			intersection.setCurrentState(intersection.getHasElementState());
			list.push(go2);
			circus.notifyObservers(go2);
			go2.setX(o.getX());
			go2.setY(o.getY() - (go2.getHeight() + 5));
			if(list.size() > 2){
				Color color1 = ((Shape)go2).getColor();
				list.pop();
				Shape temp1 = (Shape) list.pop();
				Color color2 = temp1.getColor();
				Shape temp2 = (Shape) list.peek();
				Color color3 = temp2.getColor();
				list.push(temp1);
				list.push(go2);
				if(color1.equals(color2) && color1.equals(color3)){
					if(list.size() == 3){
						intersection.setCurrentState(intersection.getEmptyState());
					}
					circus.increaseScore(list);
				}else{
					if(go2.getY() <= circus.getMaxHeight() + 10)
						circus.setTimeOut(true);
				}
			}
		}
		return intersect;
	}

}
