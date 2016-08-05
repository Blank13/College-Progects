package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Circus;

public class Easy extends Circus{

	public Easy(int screenWidth, int screenHeight) {
		setHeight(screenHeight);
		setWidth(screenWidth);
		setCreator(new EasyMode());
	}
}
