package eg.edu.alexu.csd.oop.plugins;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import javax.imageio.ImageIO;

import eg.edu.alexu.csd.oop.modes.CustomMode;

public class Environment implements EnvironmentIF{

	private CustomMode mode;
	private String imagePath, jarPath;
	
	public Environment (CustomMode mode, String path) {
		this.mode = mode;
		this.jarPath = path;
	}
	
	@Override
	public void setGameSpeed(int speed) {
		mode.setGameSpeed(speed);
	}

	@Override
	public void setControlSpeed(int speed) {
		mode.setControlSpeed(speed);
	}

	@Override
	public void setX(int x) {
		mode.setX(x);
	}

	@Override
	public void setY(int y) {
		mode.setY(y);
	}

	@Override
	public void setNoOfColors(int num) {
		mode.setNoOfColors(num);
	}
	
	@Override
	public void setCustomControlImage() {
		mode.setControlImage(imagePath);
	}
	
	@Override
	public void setNumOfClowns(int num) {
		mode.setNumOfClowns(num);
	}

	@Override
	public void setNumOfRacks(int num) {
		mode.setNumOfRacks(num);
	}
	
	@Override
	public void run() {
		JarFile jarFile = null;
		try {
			jarFile = new JarFile(jarPath);
			Enumeration<JarEntry> entry = jarFile.entries();

			URL[] urls = { new URL("jar:file:" + jarPath+"!/") };
			URLClassLoader classLoader = URLClassLoader.newInstance(urls);

			while (entry.hasMoreElements()) {
		        JarEntry jarEntry = (JarEntry) entry.nextElement();
		        if (jarEntry.getName().endsWith(".png")) {
		        	String loadedPath = loadImage(jarEntry.getName(), classLoader);
		        	if (loadedPath != null) {
		        		imagePath = loadedPath;
		        	}
		        }
			}
			entry = jarFile.entries();
			while (entry.hasMoreElements()) {
				JarEntry jarEntry = (JarEntry) entry.nextElement();
				if (jarEntry.getName().endsWith(".class")){
				    String className = jarEntry.getName().substring(0,jarEntry.getName().length()-".class".length());
				    className = className.replace('/', '.');
				    Class<?> cl = classLoader.loadClass(className);
				    if (PluginTemplate.class.isAssignableFrom(cl)) {
				    	PluginTemplate loadedClass = (PluginTemplate) cl.newInstance();
				    	loadedClass.setEnvironment(this);
						loadedClass.start();
				    } 
		        }
			}
		} catch (ClassNotFoundException | IOException | InstantiationException | IllegalAccessException ex) {
			throw new RuntimeException(ex);
		} finally {
			try {
				jarFile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public String loadImage(String fileName, URLClassLoader cl){

	    BufferedImage buffer = null;
	    try {
	        buffer = ImageIO.read(cl.getResourceAsStream(fileName));
	        File jarFile = new File(this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI());
	        jarFile = jarFile.getParentFile();
	        ImageIO.write(buffer, "png",new File(jarFile+"/"+fileName));
	        return "/"+fileName;
	    } catch (IOException | URISyntaxException e) {
	        e.printStackTrace();
	        return null;
	    }
	}
}
