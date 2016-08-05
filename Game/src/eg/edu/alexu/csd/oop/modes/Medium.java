package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Circus;

public class Medium extends Circus{

	public Medium(int screenWidth, int screenHeight) {
		setHeight(screenHeight);
		setWidth(screenWidth);
		setCreator(new MediumMode());
	}
	
}
