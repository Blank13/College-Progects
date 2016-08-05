package eg.edu.alexu.csd.oop.game;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SnapShot {
	
	public void save(World world, String path){
		try
		{
			FileOutputStream fos = new FileOutputStream(path);
			ObjectOutputStream oos = new ObjectOutputStream (fos);
			oos.writeObject(world);
			System.out.println("Saved successfully");
			oos.flush();
			oos.close();
		}catch(NotSerializableException e){
			System.out.println("Fail in save !!!!!!!\n"+e);
			
		}catch (IOException e) {
			
		}
	}
	
	public World load(String path){
		World world = null;
		try
		{
			FileInputStream fis = new FileInputStream(path);
			ObjectInputStream ois = new ObjectInputStream (fis);
			world = (World) ois.readObject();
			ois.close();
			return world;
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return null;
		}
	}

}
