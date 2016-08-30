package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Square extends RegularPolygons{
	
	private Double sideLength;
	
	@Override
	public void draw(Graphics canvas) {
		try{
			sideLength = map.get("side length");
		}catch(Exception e){
			throw new RuntimeException("Error while definig to draw");
		}
		try{
			int x = (int)((double)sideLength);
			if(getFillColor() == null && getColor() != null){
				canvas.setColor(getColor());
				canvas.drawRect(this.getPosition().x-x/2, this.getPosition().y-x/2, x, x);
			}
			else if(getFillColor() != null && getColor() == null){
				canvas.setColor(getFillColor());
				canvas.fillRect(this.getPosition().x-x/2, this.getPosition().y-x/2, x, x);
			}
			else if(getFillColor() != null && getColor() != null){
				canvas.setColor(getFillColor());
				canvas.fillRect(this.getPosition().x-x/2, this.getPosition().y-x/2, x, x);
				canvas.setColor(getColor());
				canvas.drawRect(this.getPosition().x-x/2, this.getPosition().y-x/2, x, x);
			}
			else if(getFillColor() == null && getColor() == null){
				throw new RuntimeException("no color setted");
			}
		}catch(Exception e){
			throw new RuntimeException("point is not setted");
		}
	}
	
}
