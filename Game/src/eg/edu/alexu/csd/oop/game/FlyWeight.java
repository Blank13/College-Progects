package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class FlyWeight implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static FlyWeight instance;
	private Factory factory;
	private Map<String, Shape> recycleObjects = new HashMap<String, Shape>();
	
	private FlyWeight() {
		factory = Factory.getInstance();
	}
	
	public static FlyWeight getInstance(){
		if(instance == null){
			instance = new FlyWeight();
		}
		return instance;
	}
	
	public Shape getMovingObject(String name, int posX, int posY, Color color) throws InstantiationException
	, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException{
		Shape object = recycleObjects.get(name);
		if(object == null){
			object = factory.getMovingObject(name, posX, posY, color);
		}
		else{
			recycleObjects.remove(name);
			object.setX(posX);
			object.setY(posY);
			object.setColor(color);
		}
		return object;
	}

	public void addObject(Shape arg) {
		String name = arg.getClass().getSimpleName();
		if(!recycleObjects.containsKey(name)){
			recycleObjects.put(name, arg);
		}
	}
}
