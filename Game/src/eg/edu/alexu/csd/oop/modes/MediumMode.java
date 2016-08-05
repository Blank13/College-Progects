package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Factory;
import eg.edu.alexu.csd.oop.game.GameCreator;
import eg.edu.alexu.csd.oop.game.ObjectList;

public class MediumMode implements GameCreator{

	private Factory factory = Factory.getInstance();
	private int x = 3;      // we will change it
	private int y = 5;      // we will change it
	
	@Override
	public ObjectList[] createObjects(ObjectList control, ObjectList moving, ObjectList constant, int screenWidth, int screenHeight) {
		factory.setInfo(3, 4, 6, "/clown.png");
		return factory.createObjects(control, moving, constant, screenWidth, screenHeight);
	}

	@Override
	public int gameSpeed() {
		return 45;
	}

	@Override
	public int getControlSpeed() {
		return 15;
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
		return 6;
	}

}
