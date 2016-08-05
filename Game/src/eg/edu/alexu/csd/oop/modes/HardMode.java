package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Factory;
import eg.edu.alexu.csd.oop.game.GameCreator;
import eg.edu.alexu.csd.oop.game.ObjectList;

public class HardMode implements GameCreator{

	private Factory factory = Factory.getInstance();
	private int x = 5;      // we will change it
	private int y = 7;      // we will change it
	
	@Override
	public ObjectList[] createObjects(ObjectList control, ObjectList moving, ObjectList constant, int screenWidth, int screenHeight) {
		factory.setInfo(4, 6, 8, "/clown.png");
		return factory.createObjects(control, moving, constant, screenWidth, screenHeight);
	}

	@Override
	public int gameSpeed() {
		return 40;
	}

	@Override
	public int getControlSpeed() {
		return 20;
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
		return 8;
	}
}
