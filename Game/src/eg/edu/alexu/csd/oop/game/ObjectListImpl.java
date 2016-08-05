package eg.edu.alexu.csd.oop.game;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class ObjectListImpl implements ObjectList, Observer, Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Object> list = new LinkedList<Object>();
	private String state;
	
	public ObjectListImpl(String s) {
		state = s;
	}
	
	public ObjectListImpl() {
		state = "";
	}
	
	@Override
	public void addObject(Object go) {
		list.add(go);
	}

	@Override
	public void removeObject(Object go) {
		list.remove(go);
	}

	@Override
	public Iterator<Object> getIterator() {
		return new ObjectsIterator(list);
	}

	@Override
	public List< Object> getList() {
		return list;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(state.equals("add")){
			list.add((GameObject) arg);
		}else if(state.equals("remove")){
			if(list.contains(arg)){
				list.remove(arg);
			}
		}
	}
}
