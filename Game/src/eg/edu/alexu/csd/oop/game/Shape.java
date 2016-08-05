package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public abstract class Shape implements GameObject, Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int SPRITE_WIDTH = 50;
	private final int SPRITE_HEIGHT = 20;
	private final int MAX_MSTATE = 1;
	// an array of sprite images that are drawn sequentially
	private transient BufferedImage[] spriteImages = new BufferedImage[MAX_MSTATE];
	private int x;
	private int y;
	private boolean visible;
	private Color color;
	private String state, xState;
	
	public Shape() {
		this.visible = true;
		state = "moving";
		xState = "";
	}
	
	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		if(xState.equals("0")){
			if(! (getX() > x) ){
				this.x = x;
			}
		}else if(xState.equals("width")){
			if(!(x > getX())){
				this.x = x;
			}
		}else{
			this.x = x;
		}
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		if(state.equals("moving")){
			this.y = y;
		}
	}

	@Override
	public int getWidth() {
		return SPRITE_WIDTH;
	}

	@Override
	public int getHeight() {
		return SPRITE_HEIGHT;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public BufferedImage[] getSpriteImages() {
		return spriteImages;
	}
	
	public abstract void draw();

	public void setSpriteImages(BufferedImage[] spirits) {
		this.spriteImages = spirits;
	}
	
	public void setColor(Color color){
		this.color = color;
		draw();
	}
	
	public Color getColor(){
		return color;
	}
	
	public void setState(String s){
		state = s;
	}
	
	public String getState(){
		return state;
	}
	
	public void setXState(String s){
		this.xState = s;
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

