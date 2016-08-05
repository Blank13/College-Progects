package eg.edu.alexu.csd.oop.game;

import java.io.Serializable;


public interface GameCreator extends Serializable{
	
	/**
	 * create constant , moving and control objects according to level
	 */
	public ObjectList[] createObjects(ObjectList control, ObjectList moving, ObjectList constant, int screenWidth, int screenHeight);
	
	/**
	 * 
	 * @return moving object speed
	 */
	public int gameSpeed();
	
	/**
	 * 
	 * @return control object speed
	 */
	public int getControlSpeed();
	
	public int getX();
	
	public int getY();
	
	public int getNoOfColors();

}
