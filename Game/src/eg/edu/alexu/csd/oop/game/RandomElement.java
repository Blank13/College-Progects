package eg.edu.alexu.csd.oop.game;

import java.awt.Color;
import java.io.Serializable;
import java.util.List;

public class RandomElement implements Serializable{
   
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static RandomElement instance;
	private String[] moving;
	private String[] constant;
	private Color[] colors = {Color.BLUE, Color.RED, Color.GRAY, Color.GREEN, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.YELLOW, Color.lightGray, Color.PINK};
	
	private RandomElement(){

	}
	
	public static RandomElement getInstance(){
		if(instance == null){
			instance = new RandomElement();
		}
		return instance;
	}
	
	public Color getRandomColor(int i, Color c){
		int index = (int) (Math.random() * i);
		Color temp = colors[index];
		while(temp.equals(c)){
			index = (int) (Math.random() * i);
			temp = colors[index];
		}
		return temp;
	}
	
	public String getRandomName(String s){
		int index;
		String name = null;
		if(s.equals("moving")){
			index = (int) (Math.random() * moving.length);
			name = moving[index];
		}else if(s.equals("constant")){
			index = (int) (Math.random() * constant.length);
			name = constant[index];
		}
		return name;
	}
	
	public void updateMovingNames(List<Class<? extends Shape>> list){
		String[] names = new String[list.size()];
		for(int i=0; i<list.size(); i++){
			names[i] = list.get(i).getSimpleName();
		}
		moving = names;
	}
	
	public void updateConstantNames(List<Class<? extends GameObject>> list){
		String[] names = new String[list.size()];
		for(int i=0; i<list.size(); i++){
			names[i] = list.get(i).getSimpleName();
		}
		constant = names;
	}
	
}
