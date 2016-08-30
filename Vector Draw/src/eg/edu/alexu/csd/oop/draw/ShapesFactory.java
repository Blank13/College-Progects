package eg.edu.alexu.csd.oop.draw;

public class ShapesFactory {
	private static ShapesFactory factory;
	private ShapesFactory(){
		
	}
	public static ShapesFactory newInstance(){
		if(factory == null){
			factory = new ShapesFactory();
		}
		return factory;
	}
	
	public Shape getNewShape(String shapeType){
		try{
			Class<?> shp = Class.forName(shapeType);
			if(Shape.class.isAssignableFrom(shp)){
				return (Shape) shp.newInstance();
			}
			throw new RuntimeException("a not found shape is saved!");
		}catch(Exception e){
			throw new RuntimeException("shapes factoy fails!");
		}
	}
}
