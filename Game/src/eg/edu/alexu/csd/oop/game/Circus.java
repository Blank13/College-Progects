package eg.edu.alexu.csd.oop.game;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import eg.edu.alexu.csd.oop.objects.ClownObject;

public class Circus extends Observable implements World, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private GameCreator creator;
	private static int MAX_TIME = 2 * 60 * 1000;	// 1 minute
	private int score = 0;
	private long startTime = System.currentTimeMillis(), endTime;
	private int width;
	private int height;
	private ObjectList constant = new ObjectListImpl("");
	private ObjectList moving = new ObjectListImpl("remove");
	private ObjectList control = new ObjectListImpl("add");
	private ObjectList free = new ObjectListImpl("remove");
	private Stack<GameObject>[][] clownsSticks;
    private Intersection[][] states;
	private FlyWeight flyWeight;
	private ObjectList[] racksLists;
	private List<Observer> observers;
	private int reused = 0;
	private RandomElement random;
	private boolean timeout = false;
	
	public Circus() {
		random = RandomElement.getInstance();
		flyWeight = FlyWeight.getInstance();
		observers = new LinkedList<Observer>();
		observers.add((Observer) moving);
		observers.add((Observer) control);
		observers.add((Observer) free);
	}
	
	public void addObserver(Observer o){
		observers.add(o);
	}

	
	private boolean intersect(GameObject go1, int stick, GameObject go2){
		int index = control.getList().indexOf(go1);
		Stack<GameObject> list = clownsSticks[index][stick];
		boolean intersect = states[index][stick].getCurrentState().intersect(go1, list, stick, go2, this);
		if(intersect){
			((Shape)go2).setState("control");
		}
		return intersect;
	}
	
	@Override
	public List<GameObject> getConstantObjects() {
		return createList(constant.getIterator());
	}

	@Override
	public List<GameObject> getMovableObjects() {
		// return moving.getList();
		return createList(moving.getIterator());
	}

	@Override
	public List<GameObject> getControlableObjects() {
		return createList(control.getIterator());
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public boolean refresh() {
		timeout = System.currentTimeMillis() - startTime > MAX_TIME; // time end and game over
		int num = creator.getNoOfColors();
		if(reused == 10){
			score = Math.max(score -1, 0);
			reused = 0;
		}
		if(!timeout){
			endTime = System.currentTimeMillis();
			
			// move free plates
			Iterator<Object> iterator = free.getIterator();
			Shape o =null;
			while(iterator.hasNext()){
				o = (Shape) iterator.next();
				o.setY(o.getY() + creator.getY());
				if(o.getY() >= height){
					reused ++ ;
					free.removeObject(o);
					moving.removeObject(o);
					flyWeight.addObject(o);
				}
			}
			
			// move plates on racks
			for(int i=0; i<racksLists.length; i++){
				iterator = racksLists[i].getIterator();
				while(iterator.hasNext()){
					o = (Shape) iterator.next();     // this is plate we will change the cast type
					if(i%2 == 0){
						o.setX(o.getX() + creator.getX());
						if(o.getX() >= ( (GameObject) constant.getList().get(i)).getWidth() ){
							racksLists[i].removeObject(o);
							free.addObject(o);
						}
					}else{
						o.setX(o.getX() - creator.getX());
						if(o.getX() + o.getWidth() <= (width + 15) - ( (GameObject) constant.getList().get(i)).getWidth() ){
							racksLists[i].removeObject(o);
							free.addObject(o);
						}
					}
				}
				try {
					if(i % 2 == 0){
						if(o.getX() > o.getWidth()+15){
							// create new plate
							Shape a = flyWeight.getMovingObject(random.getRandomName("moving"), 5, ((i + 2) / 2 ) * 40 - 20, random.getRandomColor(num, o.getColor()));
							racksLists[i].addObject(a);
							moving.addObject(a);
						}
					}else{
						if(o.getX() < width -  (2 * o.getWidth()+10)){
							// create new plate
							Shape a = flyWeight.getMovingObject(random.getRandomName("moving"), width-(o.getWidth()), ((i + 2) / 2 ) * 40 - 20, random.getRandomColor(num, o.getColor()));
							racksLists[i].addObject(a);
							moving.addObject(a);
						}
					}
				} catch (InstantiationException | IllegalAccessException
						| NoSuchMethodException | SecurityException
						| IllegalArgumentException | InvocationTargetException e) {
					e.printStackTrace();
				}
			}
			// check intersection between clowns and free plates
			iterator = free.getIterator();
			while(iterator.hasNext()){
				o = (Shape) iterator.next();
				List<Object> clowns = control.getList();
				int x = -1;
				for(int i=0; i<clowns.size() && clowns.get(i) instanceof ClownObject; i++){
					GameObject clown = (GameObject) clowns.get(i);
					if(clown.getX() == 0){
						loop("0", clownsSticks[i][0]);
						loop("0", clownsSticks[i][1]);
					}else if(clown.getX()+clown.getWidth() >= width + 15){
						loop("width", clownsSticks[i][0]);
						loop("width", clownsSticks[i][1]);
					}else{
						loop("", clownsSticks[i][0]);
						loop("", clownsSticks[i][1]);
					}
					if(clown.getX() != x){
						x = clown.getX();
						if(intersect(clown, 0, o))
							break;
						if(intersect(clown, 1, o))
							break;
					}
				}
			}
		}
		return ! timeout;
	}

	@Override
	public String getStatus() {
		return "Please Use Arrows To Move   |   Score=" + score + "   |   Time=" + Math.max(0, (MAX_TIME - (endTime-startTime))/1000);	// update status
	}

	@Override
	public int getSpeed() {
		return creator.gameSpeed();
	}

	@Override
	public int getControlSpeed() {
		return creator.getControlSpeed();
	}
	
	public void setWidth(int screenWidth){
		width = screenWidth;
	}
	
	public void setHeight(int screenHeight){
		height = screenHeight;
	}
	
	@SuppressWarnings("unchecked")
	public void setCreator(GameCreator creator){
		this.creator = creator;
		racksLists = this.creator.createObjects(control, moving, constant, width, height);
		clownsSticks = new Stack[control.getList().size()][2];
		states = new Intersection[control.getList().size()][2];
		for(int i =0; i<control.getList().size(); i++){
			for(int j=0; j<2; j++){
				states[i][j] = new Intersection();
				clownsSticks[i][j] = new Stack<GameObject>();
			}
		}
	}
	
	@Override
	public void notifyObservers(Object arg) {
		for(Observer o : observers){
			o.update(this, arg);
		}
	}
	
	private List<GameObject> createList(Iterator<Object> iterator){
		List<GameObject> list = new LinkedList<GameObject>();
		while(iterator.hasNext()){
			list.add((GameObject) iterator.next());
		}
		return list;
	}
	
	private void loop(String s, Stack<GameObject> list){
		for(GameObject o : list){
			((Shape)o).setXState(s);
		}
	}
	
	public void increaseScore(Stack<GameObject> list){
		score ++;
		for(int i=0; i<3; i++){
			control.removeObject(list.pop());
		}
	}
	
	public int getMaxHeight(){
		return ((constant.getList().size() -1 + 2) / 2 ) * 40;
	}
	
	public void setTimeOut(boolean b){
		timeout = b;
	}
}
