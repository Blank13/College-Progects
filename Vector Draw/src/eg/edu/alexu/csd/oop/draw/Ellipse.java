package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Ellipse extends Ellipticals{
	
	private Map<String,Double> map = new HashMap<String, Double>();
	private Double width , hight;
	
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
			map.put("hight", null);
		}
		return map;
	}
	
	@Override
	public void draw(Graphics canvas) {
		try{
			width = map.get("width");
			hight = map.get("hight");
		}catch(Exception e){
			throw new RuntimeException("Error while defining to draw");
		}
		try{
			int x = (int)((double)width);
			int y = (int)((double)hight);
			if(this.getFillColor() == null && this.getColor() != null){
				canvas.setColor(this.getColor());
				canvas.drawOval(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
			}
			else if(this.getFillColor() != null && this.getColor() == null){
				canvas.setColor(getFillColor());
				canvas.fillOval(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
			}
			else if(this.getFillColor() != null && this.getColor() != null){
				canvas.setColor(getFillColor());
				canvas.fillOval(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
				canvas.setColor(getColor());
				canvas.drawOval(this.getPosition().x-x/2, this.getPosition().y-y/2, x, y);
			}
			else if(this.getFillColor() == null && this.getColor() == null){
				throw new RuntimeException("no color setted");
			}
		}catch(Exception e){
			throw new RuntimeException("point is not setted");
		}
	}
}
