package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Factory;
import eg.edu.alexu.csd.oop.game.GameCreator;
import eg.edu.alexu.csd.oop.game.ObjectList;
import eg.edu.alexu.csd.oop.plugins.Environment;
import eg.edu.alexu.csd.oop.plugins.EnvironmentIF;

public class CustomMode implements GameCreator{

	private int gameSpeed, controlSpeed, x, y, numOfColors, numOfClowns, numOfRacks;
	private String customControlImage;
	private EnvironmentIF environment;
	private Factory factory = Factory.getInstance();
	
	public CustomMode (String path) {
		environment = new Environment(this, path);
		environment.run();
	}
	
	
	@Override
	public ObjectList[] createObjects(ObjectList control, ObjectList moving,
			ObjectList constant, int screenWidth,
			int screenHeight) {
		factory.setInfo(numOfClowns, numOfRacks, numOfColors, customControlImage);
		return factory.createObjects(control, moving, constant, screenWidth, screenHeight);
	}

	@Override
	public int gameSpeed() {
		return gameSpeed;
	}

	@Override
	public int getControlSpeed() {
		return controlSpeed;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public int getNoOfColors() {
		return numOfColors;
	}
	
	public void setGameSpeed (int speed) {
		gameSpeed = speed;
	}
	
	public void setControlSpeed (int speed) {
		controlSpeed = speed;
	}
	
	public void setX (int x) {
		this.x = x;
	}
	
	public void setY (int y) {
		this.y = y;
	}
	
	public void setNoOfColors (int num) {
		numOfColors = num;
	}
	
	public void setControlImage (String path) {
		this.customControlImage = path;
	}
	
	public void setNumOfClowns (int num) {
		this.numOfClowns = num;
	}
	
	public void setNumOfRacks (int num) {
		this.numOfRacks = num;
	}

}
