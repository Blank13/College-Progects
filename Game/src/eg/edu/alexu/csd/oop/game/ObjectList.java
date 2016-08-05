package eg.edu.alexu.csd.oop.game;

import java.util.Iterator;
import java.util.List;

public interface ObjectList {
	
	public void addObject(Object go);
	
	public void removeObject(Object go);
	
	public Iterator<Object> getIterator();
	
	public List<Object> getList();

}
