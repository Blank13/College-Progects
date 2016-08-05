package eg.edu.alexu.csd.oop.plugins;

public interface EnvironmentIF {
	
	/**
	* Sets game speed as specified by the plug-in information
	* @param speed game speed
	*/
	public void setGameSpeed (int speed);
	
	/**
	* Sets control speed as specified by the plug-in information
	* @param speed control speed
	*/
	public void setControlSpeed (int speed);
	
	/**
	* Sets horizontal offset for every move
	* as specified by the plug-in information
	* @param x offset distance
	*/
	public void setX (int x);
	
	/**
	* Sets vertical offset for every move
	* as specified by the plug-in information
	* @param y offset distance
	*/
	public void setY (int y);
	
	/**
	* Sets number of colors used for moving objects
	* as specified by the plug-in information
	* @param num number of colors
	*/
	public void setNoOfColors (int num);
	
	/**
	* Sets path to an image to use as controller
	* as specified by the plug-in information
	* @param path path to the control image
	*/
	public void setCustomControlImage ();
	
	/**
	* Call this method to start the environment
	*/
	public void run();
	
	/**
	* Sets number of clowns
	* as specified by the plug-in information
	* @param num number of clowns wanted
	*/
	public void setNumOfClowns (int num);
	
	/**
	* Sets number of racks
	* as specified by the plug-in information
	* @param num number of racks wanted
	*/
	public void setNumOfRacks (int num);
}
