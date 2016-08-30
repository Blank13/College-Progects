package eg.edu.alexu.csd.oop.draw;


import java.awt.Color;
import java.awt.Point;
import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XmlOperator {

	public void save(String path , List<Shape> shapes){
		try{
			DocumentBuilderFactory fac = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = fac.newDocumentBuilder();
			Document doc = builder.newDocument();
			Element root = doc.createElement("Shapes");
			doc.appendChild(root);
			Iterator<Shape> itr = shapes.iterator();
			while(itr.hasNext()){
				Shape now = itr.next();
				String name = now.getClass().getName();
				Element ele = doc.createElement(name);
				root.appendChild(ele);
				Point p = now.getPosition();
				String xc , yc;
				if(p != null){
					xc = Double.toString(p.getX());
					yc = Double.toString(p.getY());
				}
				else{
					xc = "null";
					yc = "null";
				}
				Element ele2 = doc.createElement("Position");
				ele.appendChild(ele2);
				Element ele3 = doc.createElement("Xcor");
				ele2.appendChild(ele3);
				ele3.appendChild(doc.createTextNode(xc));
				Element ele4 = doc.createElement("Ycor");
				ele2.appendChild(ele4);
				ele4.appendChild(doc.createTextNode(yc));
				Color fillc = now.getFillColor();
				Color col = now.getColor();
				String redCol , blueCol , greenCol , redFcol , greenFcol , blueFcol;
				if(col != null){
					redCol = Double.toString((double)col.getRed());
					blueCol = Double.toString((double)col.getBlue());
					greenCol = Double.toString((double)col.getGreen());
				}
				else{
					redCol = "null";
					blueCol = "null";
					greenCol = "null";
				}
				if(fillc != null){
					redFcol = Double.toString((double)fillc.getRed());
					blueFcol = Double.toString((double)fillc.getBlue());
					greenFcol = Double.toString((double)fillc.getGreen());
				}
				else{
					redFcol = "null";
					blueFcol = "null";
					greenFcol = "null";
				}
				Element ele5 = doc.createElement("Colors");
				ele.appendChild(ele5);
				Element ele6 = doc.createElement("externalColor");
				ele5.appendChild(ele6);
				Element ele7 = doc.createElement("Red");
				ele6.appendChild(ele7);
				ele7.appendChild(doc.createTextNode(redCol));
				Element ele8 = doc.createElement("Blue");
				ele6.appendChild(ele8);
				ele8.appendChild(doc.createTextNode(blueCol));
				Element ele9 = doc.createElement("Green");
				ele6.appendChild(ele9);
				ele9.appendChild(doc.createTextNode(greenCol));
				Element ele10 = doc.createElement("fillColor");
				ele5.appendChild(ele10);
				Element ele11 = doc.createElement("Red");
				ele10.appendChild(ele11);
				ele11.appendChild(doc.createTextNode(redFcol));
				Element ele12 = doc.createElement("Blue");
				ele10.appendChild(ele12);
				ele12.appendChild(doc.createTextNode(blueFcol));
				Element ele13 = doc.createElement("Green");
				ele10.appendChild(ele13);
				ele13.appendChild(doc.createTextNode(greenFcol));
				Element ele14 = doc.createElement("Properties");
				ele.appendChild(ele14);
				if(now.getProperties() != null){
					Set<String> keys = now.getProperties().keySet();
					Iterator<String> it = keys.iterator();
					while(it.hasNext()){
						String key = it.next();
						Double val = now.getProperties().get(key);
						String str;
						if(val != null){
							str = Double.toString((double) val);
						}else{
							str = "null";
						}
						Element ele15 = doc.createElement(key);
						ele14.appendChild(ele15);
						ele15.appendChild(doc.createTextNode(str));
					}
				}
			}
			TransformerFactory fact = TransformerFactory.newInstance();
			Transformer trans = fact.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(path));
			trans.transform(source, result);
		}catch(Exception e){
			throw new RuntimeException("XML save failed!");
		}
	}
	
	public List<Shape> load(String path){
		List<Shape> shapes = new LinkedList<Shape>();
		Document doc;
		try{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.parse(new File(path));
		}catch(Exception e){
			throw new RuntimeException("XML load failed!");
		}
		Element root = doc.getDocumentElement();
		NodeList savedShapes =  root.getChildNodes();
		for(int counter = 0; counter < savedShapes.getLength(); counter ++){
			if(savedShapes.item(counter).getNodeType() == Node.ELEMENT_NODE){
				Node nod = savedShapes.item(counter);
				ShapesFactory fact = ShapesFactory.newInstance();
				Shape shp = fact.getNewShape(nod.getNodeName());
				Element ele = (Element) nod;
				Element ele2 = (Element) ele.getElementsByTagName("Position").item(0);
				String xc , yc;
				xc = ele2.getElementsByTagName("Xcor").item(0).getTextContent();
				yc = ele2.getElementsByTagName("Ycor").item(0).getTextContent();
				if(getArg(yc) != null){
					Point p = new Point();
					p.setLocation(getArg(xc), getArg(yc));
					shp.setPosition(p);
				}
				Element ele3 = (Element) ele.getElementsByTagName("Colors").item(0);
				Element ele4 = (Element) ele3.getElementsByTagName("externalColor").item(0);
				Double extRed = getArg(ele4.getElementsByTagName("Red").item(0).getTextContent());
				Double extBlue = getArg(ele4.getElementsByTagName("Blue").item(0).getTextContent());
				Double extGreen = getArg(ele4.getElementsByTagName("Green").item(0).getTextContent());
				if(extRed != null){
					shp.setColor(new Color((int)((double)extRed), (int)((double)extGreen), (int)((double)extBlue)));
				}
				Element ele5 = (Element) ele3.getElementsByTagName("fillColor").item(0);
				Double filRed = getArg(ele5.getElementsByTagName("Red").item(0).getTextContent());
				Double filBlue = getArg(ele5.getElementsByTagName("Blue").item(0).getTextContent());
				Double filGreen = getArg(ele5.getElementsByTagName("Green").item(0).getTextContent());
				if(filRed != null){
					shp.setFillColor(new Color((int)((double)filRed), (int)((double)filGreen), (int)((double)filBlue)));
				}
				Element ele6 = (Element) ele.getElementsByTagName("Properties").item(0);
				NodeList properties = ele6.getChildNodes();
				Map<String , Double> map = shp.getProperties();
				for(int counter2 = 0; counter2 < properties.getLength(); counter2 ++){
					Node now = properties.item(counter2);
					String property = now.getNodeName();
					Double value = getArg(now.getTextContent());
					map.put(property, value);
				}
				shapes.add(shp);
			}
		}
		return shapes;
	}
	
	
	private Double getArg(String representation){
		if(!representation.equals("null")){
			return Double.parseDouble(representation);
		}
		return null;
	}
}
