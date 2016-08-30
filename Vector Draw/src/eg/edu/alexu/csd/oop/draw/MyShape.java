package eg.edu.alexu.csd.oop.draw;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


public abstract class MyShape implements Shape{
	
	private Point coordinate = null;
	private Color corner = null , fill = null;
	protected Boolean isClone = false , setted = false;
	
	@Override
	public void setPosition(Point position) {
		if(position == null){
			coordinate = null;
		}
		else{
			coordinate = new Point();
			coordinate.x = position.x;
			coordinate.y = position.y;
		}
	}

	@Override
	public Point getPosition() {
		if(coordinate == null){
			return null;
		}
		return coordinate;
	}

	@Override
	public void setProperties(Map<String, Double> properties) {
		// TODO Auto-generated method stub
	}

	@Override
	public Map<String, Double> getProperties() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColor(Color color) {
		corner = color;
		
	}

	@Override
	public Color getColor() {
		return corner;
	}

	@Override
	public void setFillColor(Color color) {
		fill = color;
		
	}

	@Override
	public Color getFillColor() {
		return fill;
	}

	@Override
	public void draw(Graphics canvas) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		isClone = true;
		 try {
             Class<?> cl = this.getClass();
             Constructor<?> cons = cl.getConstructor();
             Shape s = (Shape) cons.newInstance();
             s.setProperties(this.getProperties());
             try{
                 s.setPosition(this.getPosition());
             }catch(Exception e){
             }
             s.setFillColor(getFillColor());
             s.setColor(getColor());
             isClone = false;
             return s;
 		} catch (NoSuchMethodException | SecurityException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            isClone = false;
 		}
        isClone = false;
		return null;
	}

}
