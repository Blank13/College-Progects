package eg.edu.alexu.csd.oop.draw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class Circle extends Ellipse{
	private Map<String,Double> map = new HashMap<String, Double>();
	private Double radius;
	
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
		if(map != null && map.containsKey("radius")){
			radius = map.get("radius");
			if(radius!= null){
				Map<String,Double> clone = new HashMap<String, Double>();
				clone.put("width", radius*2);
				clone.put("hight", radius*2);
				super.setProperties(clone);
			}
		}
	}
	
	@Override
	public Map<String, Double> getProperties() {
		if(map != null && map.isEmpty() && !isClone && !setted){
			map.put("radius", null);
		}
		return map;
	}
}
