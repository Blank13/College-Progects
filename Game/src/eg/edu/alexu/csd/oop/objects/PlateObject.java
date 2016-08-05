package eg.edu.alexu.csd.oop.objects;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import eg.edu.alexu.csd.oop.game.Shape;

public class PlateObject extends Shape{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void draw(){
		BufferedImage[] spriteImages = new BufferedImage[1];
		spriteImages[0] = new BufferedImage(getWidth(), getHeight(),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = spriteImages[0].createGraphics();
		g2.setColor(getColor());
		g2.setBackground(getColor());
		int[] xCoordinates = new int[4];
		int[] yCoordinates = new int[4];
		xCoordinates[0] = 0;
		xCoordinates[1] = getWidth();
		xCoordinates[2] = getWidth() - 5;
		xCoordinates[3] = 5;
		yCoordinates[0] = 0;
		yCoordinates[1] = 0;
		yCoordinates[2] = getHeight();
		yCoordinates[3] = getHeight();
		g2.fillPolygon(xCoordinates, yCoordinates, 4);
		g2.dispose();
		setSpriteImages(spriteImages);
	}
}

