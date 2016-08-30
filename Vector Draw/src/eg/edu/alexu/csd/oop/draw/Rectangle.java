package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Rectangle extends Polygons{

	private Map<String,Double> map = new HashMap<String, Double>();
	private Double width , length;
	
	@Override
	public void setProperties(Map<String, Double> properties) {
		if(properties == null){
			map = null;
		}
		else{
			Set<String> s = properties.keySet();
			Iterator<String> i = s.iterator();
			while(i.hasNext()){
				String key = i.next();
				map.put(key, properties.get(key));
			}
		}
		setted = true;
	}
	
	@Override
	public Map<String, Double> getProperties() {
		if(map != null && map.isEmpty() && !isClone && !setted){
			map.put("width", null);
			map.put("length", null);
		}
		return map;
	}
	
	@Override
	public void draw(Graphics canvas) {
		try{
			width = map.get("width");
			length = map.get("length");
		}catch(Exception e){
			throw new RuntimeException("Error while definig to draw");
		}
		try{
			int x = (int)((double)width);
			int y = (int)((double)length);
			if(getFillColor() == null && getColor() != null){
				canvas.setColor(getColor());
				canvas.drawRect(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
			}
			else if(getFillColor() != null && getColor() == null){
				canvas.setColor(getFillColor());
				canvas.fillRect(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
			}
			else if(getFillColor() != null && getColor() != null){
				canvas.setColor(getFillColor());
				canvas.fillRect(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
				canvas.setColor(getColor());
				canvas.drawRect(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
			}
			else if(getFillColor() == null && getColor() == null){
				throw new RuntimeException("no color setted");
			}
		}catch(Exception e){
			throw new RuntimeException("point is not setted");
		}		
	}
	
}
