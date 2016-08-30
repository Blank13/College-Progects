package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class PaintingEngine implements DrawingEngine{
	
	private List<Shape> list = new LinkedList<Shape>();
	private Stack<HistoryContainer> mainHis = new Stack<HistoryContainer>();
	private Stack<HistoryContainer> assHis = new Stack<HistoryContainer>();
	
	@Override
	public void refresh(Graphics canvas) {
		Shape[] shapes = this.getShapes();
		for(int counter = 0; counter < shapes.length; counter ++){
			shapes[counter].draw(canvas);
		}		
	}

	@Override
	public void addShape(Shape shape) {
		list.add(shape);
		limitHis();
		HistoryContainer his = new HistoryContainer();
		his.setAsAdd(shape);
		his.setOrder(list.lastIndexOf(shape));
		mainHis.push(his);
	}

	@Override
	public void removeShape(Shape shape) {
		limitHis();
		HistoryContainer his = new HistoryContainer();
		his.setAsDel(shape);
		his.setOrder(list.lastIndexOf(shape));
		mainHis.push(his);
		list.remove(list.lastIndexOf(shape));
	}

	@Override
	public void updateShape(Shape oldShape, Shape newShape) {
		limitHis();
		HistoryContainer his = new HistoryContainer();
		his.setAsUpd(newShape, oldShape);
		his.setOrder(list.lastIndexOf(oldShape));
		mainHis.push(his);
		list.set(list.lastIndexOf(oldShape), newShape);
	}

	@Override
	public Shape[] getShapes() {
		Shape[] shapes = new Shape[list.size()];
		Iterator<Shape> it = list.iterator();
		for(int i=0;i<list.size();i++){
			shapes[i] = it.next();
		}
		return shapes;
	}

	@Override
	public List<Class<? extends Shape>> getSupportedShapes() {
		List<Class<? extends Shape>> classes = new LinkedList<Class<? extends Shape>>();
		Class<?> myInterface = null;
		try {
			myInterface = Class.forName("eg.edu.alexu.csd.oop.draw.Shape");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException("interface wasn't found");
		}
		ClassFinder searcher  = new ClassFinder(myInterface);
		classes = searcher.getClasses();
		for(int i=0;i<classes.size();i++){
			if(myInterface == classes.get(i)){
				classes.remove(i);
				break;
			}
		}
		return classes;
	}

	@Override
	public void undo() {
		if(assHis.size() >= 20 || mainHis.empty()){
			throw new RuntimeException();
		}
		HistoryContainer his = mainHis.pop();
		if(his.getType() == 1){
			list.remove(list.lastIndexOf(his.getNewObj()));
		}
		else if(his.getType() == 2){
			list.add(his.getOrder(), (Shape) his.getOldObj());
		}
		else{
			list.set(list.lastIndexOf(his.getNewObj()), (Shape) his.getOldObj());
		}
		assHis.push(his);
	}

	@Override
	public void redo() {
		if(assHis.empty()){
			throw new RuntimeException();
		}
		HistoryContainer his = assHis.pop();
		if(his.getType() == 1){
			list.add(his.getOrder(), (Shape) his.getNewObj());
		}
		else if(his.getType() == 2){
			list.remove(list.lastIndexOf(his.getOldObj()));
		}
		else{
			list.set(list.lastIndexOf(his.getOldObj()), (Shape) his.getNewObj());
		}
		mainHis.push(his);
	}

	@Override
	public void save(String path) {
		File file = new File(path);
		try{
			if(!file.exists()){
				file.createNewFile();
			}
		}catch(Exception e){
			throw new RuntimeException("some thing wrong in the file");
		}
		
		if(pathCheckForJSON(path)){
			JsonOperator oper = new JsonOperator();
			oper.jsonSave(file, list);
		}
		else if(pathCheckForXML(path)){
			XmlOperator oper = new XmlOperator();
			oper.save(path, list);
		}
		else{
			throw new RuntimeException("not JSON or XML extention");
		}
	}


	@Override
	public void load(String path) {
		File file = new File(path);
		if(file.exists()){
			if(pathCheckForJSON(path)){
				JsonOperator oper = new JsonOperator();
				list = oper.loadJSON(file);
			}
			else if(pathCheckForXML(path)){
				XmlOperator oper = new XmlOperator();
				list = oper.load(path);
			}
			else{
				throw new RuntimeException("not JSON or XML extention");
			}
			mainHis.clear();
			assHis.clear();
		}
		else{
			throw new RuntimeException("File not found");
		}
	}

	//private methods
	
	private boolean pathCheckForXML(String path) {
		String str = new String();
		for(int i=path.length()-4;i<path.length();i++){
			str += path.charAt(i);
		}
		str = str.toLowerCase();
		if(str.equals(".xml")){
			return true;
		}
		return false;
	}
	
	
	private boolean pathCheckForJSON(String path) {
		String str = new String();
		for(int i=path.length()-5;i<path.length();i++){
			str += path.charAt(i);
		}
		str = str.toLowerCase();
		if(str.equals(".json")){
			return true;
		}
		else{
			return false;
		}
	}
	
	private void limitHis(){
		int siz = assHis.size();
		assHis = new Stack<HistoryContainer>();
		for(int counter = 0; counter < 20 - siz && !mainHis.empty(); counter ++){
			assHis.push(mainHis.pop());
		}
		mainHis = new Stack<HistoryContainer>();
		while(!assHis.empty()){
			mainHis.push(assHis.pop());
		}
	}
}
