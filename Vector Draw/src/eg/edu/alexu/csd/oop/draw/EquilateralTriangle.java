package eg.edu.alexu.csd.oop.draw;

import java.awt.Graphics;

public class EquilateralTriangle extends RegularPolygons{
	
	private Double sideLength;
	
	@Override
	public void draw(Graphics canvas){
		try{
			sideLength = map.get("side length");
		}catch(Exception e){
			throw new RuntimeException("Error while definig to draw");
		}
		try{
			int x = (int)((double)sideLength);
			int medianLength = (int) (0.5*Math.sqrt(3)*x);
			int[] xPoints = new int[3];
			int[] yPoints = new int[3];
			yPoints[0] = this.getPosition().y - 2*medianLength/3;
			yPoints[1] = this.getPosition().y + medianLength/3;
			yPoints[2] = this.getPosition().y + medianLength/3;
			xPoints[0] = this.getPosition().x;
			xPoints[1] = this.getPosition().x - x/2;
			xPoints[2] = this.getPosition().x + x/2;
			if(getFillColor() == null && getColor() != null){
				canvas.setColor(getColor());
				canvas.drawPolygon(xPoints, yPoints, 3);
			}
			else if(getFillColor() != null && getColor() == null){
				canvas.setColor(getFillColor());
				canvas.fillPolygon(xPoints, yPoints, 3);
			}
			else if(getFillColor() != null && getColor() != null){
				canvas.setColor(getFillColor());
				canvas.fillPolygon(xPoints, yPoints, 3);
				canvas.setColor(getColor());
				canvas.drawPolygon(xPoints, yPoints, 3);
			}
			else if(getFillColor() == null && getColor() == null){
				throw new RuntimeException("no color setted");
			}
		}catch(Exception e){
			throw new RuntimeException("point is not setted");
		}
		
	}
}
