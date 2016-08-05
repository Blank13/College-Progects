package eg.edu.alexu.csd.oop.modes;

import eg.edu.alexu.csd.oop.game.Circus;

public class Custom extends Circus{
	
	public Custom(int screenWidth, int screenHeight,String path) {
		setHeight(screenHeight);
		setWidth(screenWidth);
		setCreator(new CustomMode(path));
	}

}
