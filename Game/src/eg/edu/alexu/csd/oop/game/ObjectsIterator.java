package eg.edu.alexu.csd.oop.game;

import java.util.Iterator;
import java.util.List;

public class ObjectsIterator implements Iterator<Object>{
	
	private List<Object> objects;
	private int pointer = -1;
	
	public ObjectsIterator(List<Object> list) {
		objects = list;
	}

	@Override
	public boolean hasNext() {
		if(pointer < objects.size()-1)
			return true;
		return false;
	}

	@Override
	public Object next() {
		return objects.get(++pointer);
	}
}
