package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Circus;

public class Hard extends Circus{

	public Hard(int screenWidth, int screenHeight) {
		setHeight(screenHeight);
		setWidth(screenWidth);
		setCreator(new HardMode());
	}
}
