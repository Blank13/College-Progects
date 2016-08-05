package eg.edu.alexu.csd.oop.objects;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import eg.edu.alexu.csd.oop.game.Shape;

public class RectangleObject extends Shape{

	@Override
	public void draw() {
		BufferedImage[] spriteImages = new BufferedImage[1];
		// create a bunch of buffered images and place into an array, to be displayed sequentially
		spriteImages[0] = new BufferedImage(getWidth(), getHeight(),	BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = spriteImages[0].createGraphics();
		g2.setColor(getColor());
		g2.setBackground(getColor());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
		setSpriteImages(spriteImages);
	}	
}
