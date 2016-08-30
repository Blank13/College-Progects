package eg.edu.alexu.csd.oop.draw;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RegularPolygons extends Polygons{
	protected Map<String,Double> map = new HashMap<String, Double>();
	private Double sideLength;
	
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
			map.put("side length", null);
		}
		return map;
	}
}
