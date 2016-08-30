package eg.edu.alexu.csd.oop.draw;


import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JsonOperator {

	public void jsonSave(File file, List<Shape> list) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(file);
			writer.write("{");
			writer.flush();
			writer.write(System.getProperty( "line.separator" ));
			writer.flush();
			writer.write("\"picture\" : [");
			writer.flush();
			writer.write(System.getProperty( "line.separator" ));
			writer.flush();
			for(int i=0;i<list.size();i++){
				Shape shape = (Shape) list.get(i);
				writer.write("{\""+shape.getClass().getName()+"\" : [\n");
				writer.flush();
				writer.write(System.getProperty( "line.separator" ));
				writer.flush();
				Color cOut = shape.getColor();
				Color cIn = shape.getFillColor();
				Point p = shape.getPosition();
				Map<String,Double> map = shape.getProperties();
				if(cOut != null){
					writer.write("{\"color\" : ["
							+cOut.getRed()+","+cOut.getGreen()+","+
							cOut.getBlue()+"]},");
					writer.flush();
					writer.write(System.getProperty( "line.separator" ));
					writer.flush();
				}
				if(cIn != null){
					writer.write("{\"fill color\" : ["
							+cIn.getRed()+","+cIn.getGreen()+","
							+cIn.getBlue()+"]},");
					writer.flush();
					writer.write(System.getProperty( "line.separator" ));
					writer.flush();
				}
				
				if(p != null){
					writer.write("{\"position\" : ["+ p.x + ","+ p.y+"]},");
					writer.flush();
					writer.write(System.getProperty( "line.separator" ));
					writer.flush();
				}
				if(map != null && !map.isEmpty()){
					Set<String> set = map.keySet();
					if(checkSet(set,map)){
						writer.write("{\"map\" : [");
						writer.flush();
						writer.write(System.getProperty( "line.separator" ));
						writer.flush();
						Iterator<String> iterator =  set.iterator();
						while(iterator.hasNext()){
							String key = iterator.next();
							Double value = map.get(key);
							boolean isNull = true;
							if(value != null){
								isNull = false;
								writer.write("{\""+key+"\" : "+value+"}");
								writer.flush();
							}
							if(iterator.hasNext() && !isNull){
								writer.write(",");
								writer.flush();
								writer.write(System.getProperty( "line.separator" ));
								writer.flush();
							}
							else if(!iterator.hasNext() && !isNull){
								writer.write(System.getProperty( "line.separator" ));
								writer.flush();
							}
						}
						writer.write("]}");
						writer.flush();
						writer.write(System.getProperty( "line.separator" ));
						writer.flush();
					}
				}
				writer.write("]}");
				writer.flush();
				writer.write(System.getProperty( "line.separator" ));
				writer.flush();
			}
			writer.write("]}");
			writer.flush();
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("some thing wrong in the file");
		} catch(NullPointerException e2){
			throw new RuntimeException();
		}
		finally{
			try {
				writer.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public List<Shape> loadJSON(File file) {
		List<Shape> list2 = new LinkedList<Shape>();
		try {
			@SuppressWarnings("resource")
			Scanner fscan = new Scanner(file);
			String s = "";
			while(fscan.hasNextLine()){
				s += fscan.nextLine();	
			}
			Pattern p = Pattern.compile("\"([^\"]+)\"|(\\d+\\.\\d+)|(\\d+)");
			Matcher m = p.matcher(s);
			List<String> tokens = new LinkedList<String>();
			boolean checked = check(s);
			if(checked){
				while(m.find()){
				  String token = m.group();
				  token = token.replace("\"", "");
				  tokens.add(token);
				}
				Iterator<String> it = tokens.iterator();
				String str = it.next();
				for(int i=0;it.hasNext();i++){
					if(i==0 ){
						if(str.equals("picture")){
							str=it.next();
						}
						else{
							throw new RuntimeException("picture object is not writen");
						}
					}
					else{
						try {
							Shape shape = ShapesFactory.newInstance().getNewShape(str);
							str = it.next();
							for(int j=0;j<4;j++){
								if(str.equals("color")||str.equals("fill color")||str.equals("position")||str.equals("map")){
									if(str.equals("color")){
										Color c = new Color((int)((double)Double.parseDouble(it.next())),(int)((double)Double.parseDouble(it.next())),(int)((double)Double.parseDouble(it.next())));
										shape.setColor(c);
										if(it.hasNext()){
											str = it.next();
										}
										else{
											break;
										}
									}
									else if(str.equals("fill color")){
										Color c = new Color((int)((double)Double.parseDouble(it.next())),(int)((double)Double.parseDouble(it.next())),(int)((double)Double.parseDouble(it.next())));
										shape.setFillColor(c);
										if(it.hasNext()){
											str = it.next();
										}
										else{
											break;
										}
									}
									else if(str.equals("position")){
										Point point = new Point((int)((double)Double.parseDouble(it.next())),(int)((double)Double.parseDouble(it.next())));
										shape.setPosition(point);
										if(it.hasNext()){
											str = it.next();
										}
										else{
											break;
										}
									}
									else if(str.equals("map")){
										Map<String,Double> map = shape.getProperties();
										Set<String> set = map.keySet();
										Iterator<String> pIt = set.iterator();
										while(pIt.hasNext()){
											str = it.next();
											map.put(str, Double.parseDouble(it.next()));
											pIt.next();
										}
										shape.setProperties(map);
										if(it.hasNext()){
											str = it.next();
										}
										else{
											break;
										}
									}
								}
								else{
									break;
								}
							}
							list2.add(shape);
						} catch (Exception e) {
							throw new RuntimeException();
						}
					}
				}
			}
			else{
				fscan.close();
				throw new RuntimeException("JSON syntax is wrong");
			}
			fscan.close();
		} catch (FileNotFoundException e) {
			throw new RuntimeException("FileNotFoundException");
		}
		return list2;
	}
	
	//private methods
	
	private boolean checkSet(Set<String> set, Map<String, Double> map) {
		Iterator<String> iterator =  set.iterator();
		while(iterator.hasNext()){
			String key = iterator.next();
			Double value = map.get(key);
			if(value != null){
				return true;
			}
		}
		return false;
	}
	
	private boolean check(String s) {
		int curlyBrackets =0 , squareBrackets=0;
		for(int i=0;i<s.length();i++){
			if(s.charAt(i) == '{'){
				curlyBrackets++;
			}
			else if(s.charAt(i) == '}'){
				curlyBrackets--;
				if(curlyBrackets<0){
					return false;
				}
			}
			else if(s.charAt(i) == '['){
				squareBrackets++;
			}
			else if(s.charAt(i) == ']'){
				squareBrackets--;
				if(squareBrackets <0){
					return false;
				}
			}
		}
		return true;
	}
}
