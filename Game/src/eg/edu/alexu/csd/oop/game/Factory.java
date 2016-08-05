package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import eg.edu.alexu.csd.oop.objects.ClownObject;

public class Factory implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int clownsNo, racksNo, colorsNo;
	private String clownPic;
	private static Factory instance;
	private RandomElement random;
	private List<Class<? extends Shape>> movingShapes;
	private List<Class<? extends GameObject>> constantShapes;
	
	private Factory() {
		random = RandomElement.getInstance();
	}
	
	public void setMovingClasses(List<Class<? extends Shape>> list){
		movingShapes=list;
		random.updateMovingNames(movingShapes);
	}
	
	public void setConstantClasses(List<Class<? extends GameObject>> list){
		constantShapes=list;
		random.updateConstantNames(constantShapes);
	}
	
	public static Factory getInstance(){
		if(instance == null){
			instance = new Factory();
		}
		return instance;
	}
	
	public World createWorld(String name, int width, int height, String path) throws ClassNotFoundException, NoSuchMethodException
	, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> c = Class.forName("eg.edu.alexu.csd.oop.modes."+name);
		Class<?>[] types = null;
		Constructor<?> cons = null;
		World world = null;
		if(name.equals("Custom")){
			types = new Class<?>[]{int.class, int.class,String.class};
			cons = c.getConstructor(types);
			world = (World) cons.newInstance(width, height, path);
		}
		else{
			types = new Class<?>[]{int.class, int.class};
			cons = c.getConstructor(types);
			world = (World) cons.newInstance(width, height);
		}
		return world;
	}
	
	private int getIndexOfMoving(String name){
		for (int i = 0; i < movingShapes.size(); i++) {
			if (movingShapes.get(i).getSimpleName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	public Shape getMovingObject(String name, int posX, int posY, Color color) throws InstantiationException, IllegalAccessException {
		Class<?> c = movingShapes.get(getIndexOfMoving(name));
		Shape shape = (Shape) c.newInstance();
		shape.setX(posX);
		shape.setY(posY);
		shape.setColor(color);
		return shape;
	}
	
	public GameObject getControlObject(int posX, int posY, String path){
		return new ClownObject(posX, posY, path);
	}

	public GameObject getConstObject(String name, int posX, int posY, Color color, int width) throws NoSuchMethodException
	, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Class<?> c = constantShapes.get(getIndexOfConstant(name));
		Class<?>[] types = {int.class, int.class, Color.class, int.class};
		Constructor<?> cons = c.getConstructor(types);
		return (GameObject) cons.newInstance(posX, posY, color, width);
	}
	
	private int getIndexOfConstant(String name){
		for (int i = 0; i < constantShapes.size(); i++) {
			if (constantShapes.get(i).getSimpleName().equals(name)) {
				return i;
			}
		}
		return -1;
	}
	
	public List<Class<? extends Shape>> getMovingShapes(){
		return movingShapes;
	}
	
	public List<Class<? extends GameObject>> getConstantShapes(){
		return constantShapes;
	}
	
	public ObjectList[] createObjects(ObjectList control, ObjectList moving, ObjectList constant, int screenWidth, int screenHeight) {
		
		// create clowns
		for(int i = 0; i < clownsNo; i++){
			control.addObject(getControlObject( ((i+1) * screenWidth / (clownsNo+1)) - 90, screenHeight - 185, clownPic));
		}
		
		// create racks lists 
		ObjectList[] lists = new ObjectListImpl[racksNo];
		
		// create Racks and its lists
		for(int i = 0; i < racksNo; i++){
			lists[i] = new ObjectListImpl();
			try {
				int width = 350 - ((i / 2) * 90);
				int height = ((i + 2) / 2 ) * 40;
				Shape o1 = null;
				if(i % 2 == 0){
					constant.addObject(getConstObject("RackObject", 0,height , Color.BLACK, width));
					o1 = getMovingObject(random.getRandomName("moving"), 20, height - 20, random.getRandomColor(colorsNo, Color.BLACK));
				}
				else{
					constant.addObject(getConstObject("RackObject", screenWidth - width + 15, height, Color.BLACK, width));
					o1 = getMovingObject(random.getRandomName("moving"), screenWidth - 50, height - 20, random.getRandomColor(colorsNo, Color.BLACK));
				}
				lists[i].addObject(o1);
				moving.addObject(o1);
			} catch (NoSuchMethodException | SecurityException
					| InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException e) {
				e.printStackTrace();
			}
		}
		return lists;
	}
	
	public void setInfo(int clownsNo, int racksNo, int colorsNo, String clownPic){
		this.clownPic = clownPic;
		this.clownsNo = clownsNo;
		this.racksNo = racksNo;
		this.colorsNo = colorsNo;
	}
}
