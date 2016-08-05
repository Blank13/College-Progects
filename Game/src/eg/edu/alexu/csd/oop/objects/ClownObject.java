package eg.edu.alexu.csd.oop.objects;

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

public class ClownObject implements GameObject, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int MAX_MSTATE = 1;
	// an array of sprite images that are drawn sequentially
	private transient BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
	private int x;
	private final int y;
	private boolean visible;
	
	public ClownObject(int posX, int posY, String path) {
		this.x = posX;
		this.y = posY;
		this.visible = true;
		// create a bunch of buffered images and place into an array, to be displayed sequentially
				try {
					spriteImages[0] = ImageIO.read(getClass().getResourceAsStream(path));
				} catch (IOException e) {
					e.printStackTrace();
				}
	}

	@Override
	public int getX() {
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
		
	}

	@Override
	public int getWidth() {
		return spriteImages[0].getWidth();
	}

	@Override
	public int getHeight() {
		return spriteImages[0].getHeight();
	}

	@Override
	public boolean isVisible() {
		return visible;
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
             ImageIO.write(eachImage, "png", buffer);
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

}
