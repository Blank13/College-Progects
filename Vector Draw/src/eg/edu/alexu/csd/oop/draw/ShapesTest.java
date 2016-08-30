package eg.edu.alexu.csd.oop.draw;

import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class ShapesTest {
	
	@Test
	public void testNullMapSetted() {
		Shape circle = new Square();
		Map<String, Double> m1 = new HashMap<String,Double>();
		circle.setProperties(m1);
		assertEquals(m1, circle.getProperties());
		circle.setProperties(null);
		assertEquals(null, circle.getProperties());
	}
	
	@Test
	public void testColor() {
		Shape circle = new Circle();
		Color c ;
		c = Color.RED;
		circle.setColor(c);
		c = Color .BLUE;
		assertEquals(Color.RED, circle.getColor());
		circle.setFillColor(c);
		c= Color.CYAN;
		assertEquals(Color.BLUE, circle.getFillColor());
		c= null;
		circle.setColor(c);
		assertEquals(null, circle.getColor());
		
	}
	
	@Test
	public void testposition() {
		Shape circle = new Circle();
		Point p = new Point(50,50);
		Point c = new Point(50,50);
		circle.setPosition(p);
		p.x=20;
		p.y=30;
		assertEquals(c, circle.getPosition());
		
	}
	
	@Test
	public void testclone() {
		Shape circle = new Circle();
		Shape clone = null;
		Color c ;
		c = Color.RED;
		circle.setColor(c);
		c = Color .BLUE;
		circle.setFillColor(c);
		try {
			clone = (Shape) circle.clone();
		} catch (CloneNotSupportedException e) {
			fail();
		}
		clone.setColor(Color.BLACK);
		assertEquals(Color.RED, circle.getColor());
		assertEquals(Color.BLACK, clone.getColor());
	}
	
	@Test
	public void testclone2() {
		Shape circle = new Circle();
		Shape clone = null;
		Color c ;
		c = Color.RED;
		circle.setColor(c);
		c = Color .BLUE;
		circle.setFillColor(c);
		Map<String, Double> m1 = circle.getProperties();
		m1.put("radius", (double) 100);
		circle.setProperties(m1);
		try {
			clone = (Shape) circle.clone();
		} catch (CloneNotSupportedException e) {
			fail();
		}
		Map<String, Double> m2 = clone.getProperties();
		m2.put("radius", (double) 200);
		clone.setProperties(m2);
		clone.setColor(Color.BLACK);
		assertEquals(Color.RED, circle.getColor());
		assertEquals(Color.BLACK, clone.getColor());
		assertEquals(m1, circle.getProperties());
		assertEquals(m2, clone.getProperties());
	}
	
	@Test
	public void testRegularPolygons() {
		Shape square1 = new Square();
		Shape square2 = new Square();
		Map<String, Double> m1 = square1.getProperties();
		Map<String, Double> m2 = square2.getProperties();
		m1.put("side length", (double) 200);
		m2.put("side length", (double) 100);
		square1.setPosition(new Point(0,0));
		square1.setColor(Color.RED);
		square1.setProperties(m1);
		square2.setPosition(new Point(10,10));
		square2.setColor(Color.RED);
		square2.setProperties(m2);
		assertEquals(m1, square1.getProperties());
		assertEquals(m2, square2.getProperties());

	}
	
	@Test
	public void testdraw() {
		Shape circle = new Square();
		Map<String, Double> m1 = new HashMap<String,Double>();
		circle.setProperties(m1);
		Graphics g = null;
		try{
			circle.draw(g);
			fail();
		}catch(Exception e){
		}
	}
}
