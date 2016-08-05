package eg.edu.alexu.csd.oop.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.game.GameObject;

public class RackObject implements GameObject, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sprite_width ;
	private final int SPRITE_HEIGHT = 10;
	private final int MAX_MSTATE = 1;
	// an array of sprite images that are drawn sequentially
	private transient BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
	private int x;
	private int y;
	
	public RackObject(int posX, int posY, Color color, int width) {
		this.x = posX;
		this.y = posY;
		sprite_width = width;
		// create a bunch of buffered images and place into an array, to be displayed sequentially
		spriteImages[0] = new BufferedImage(sprite_width, SPRITE_HEIGHT,	BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = spriteImages[0].createGraphics();
		g2.setColor(color);
		g2.setBackground(color);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(20));
		g2.drawLine(0, 0, getWidth(), 0);
		g2.dispose();
	}

	@Override
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getWidth() {
		return sprite_width;
	}

	@Override
	public int getHeight() {
		return SPRITE_HEIGHT;
	}

	@Override
	public boolean isVisible() {
		return true;
	}

	@Override
	public BufferedImage[] getSpriteImages() {
		return spriteImages;
	}
	
	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(spriteImages.length); // how many images are serialized?
        for (BufferedImage eachImage : spriteImages) {
        	 ByteArrayOutputStream buffer = new ByteArrayOutputStream();
             ImageIO.write(eachImage, "jpg", buffer);
             out.writeInt(buffer.size()); // Prepend image with byte count
             buffer.writeTo(out);
        }
    }
	
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        
        int imageCount = in.readInt();
        ArrayList<BufferedImage> imageSelection = new ArrayList<BufferedImage>(imageCount);
        for (int i = 0; i < imageCount; i++) {
            int size = in.readInt(); // Read byte count
            byte[] buffer = new byte[size];
            in.readFully(buffer); // Make sure you read all bytes of the image
            imageSelection.add(ImageIO.read(new ByteArrayInputStream(buffer)));
        }
        spriteImages = new BufferedImage[imageSelection.size()];
        imageSelection.toArray(spriteImages);
    }
	
	/*
	private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeInt(spriteImages.length); // how many images are serialized?
        for (BufferedImage eachImage : spriteImages) {
            ImageIO.write(eachImage, "png", out); // png is lossless
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        final int imageCount = in.readInt();
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>(imageCount);
        for (int i=0; i<imageCount; i++) {
            images.add(ImageIO.read(in));
        }
        images.toArray(spriteImages);
    }
    */

}
