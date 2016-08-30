package eg.edu.alexu.csd.oop.draw;

public class HistoryContainer {
	
	private int type;
	private int order;
	private Object newObj;
	private Object oldObj;
	
	public void setAsAdd(Object newObj){
		type = 1;
		this.newObj = newObj;
	}
	
	public void setAsDel(Object oldObj){
		type = 2;
		this.oldObj = oldObj;
	}
	
	public void setAsUpd(Object newObj , Object oldObj){
		type = 3;
		this.newObj = newObj;
		this.oldObj = oldObj;
	}
	
	public int getType(){
		return type;
	}
	
	public Object getNewObj(){
		return newObj;
	}
	
	public Object getOldObj(){
		return oldObj;
	}
	
	public void setOrder(int order){
		this.order = order;
	}
	
	public int getOrder(){
		return order;
	}
}
