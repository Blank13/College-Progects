package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Factory;
import eg.edu.alexu.csd.oop.game.GameCreator;
import eg.edu.alexu.csd.oop.game.ObjectList;

public class EasyMode implements GameCreator{

	private Factory factory = Factory.getInstance();
	private int x = 2;      // we will change it
	private int y = 3;      // we will change it
	
	@Override
	public ObjectList[] createObjects(ObjectList control, ObjectList moving, ObjectList constant, int screenWidth, int screenHeight) {
		factory.setInfo(2, 2, 4, "/clown.png");
		return factory.createObjects(control, moving, constant, screenWidth, screenHeight);
	}

	@Override
	public int gameSpeed() {
		return 50;
	}

	@Override
	public int getControlSpeed() {
		return 10;
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
		return 4;
	}
}
