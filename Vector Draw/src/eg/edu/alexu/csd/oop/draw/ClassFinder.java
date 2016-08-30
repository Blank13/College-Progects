package eg.edu.alexu.csd.oop.draw;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class ClassFinder {

    private Class<?> superClass = null;
    private List<Class<? extends Shape>> classes = new LinkedList<Class<? extends Shape>>();

    public ClassFinder(Class<?> superClass) {
        this.superClass = superClass;
    }
    
    @SuppressWarnings("unchecked")
	protected void addClass(String className) {
                try {
                    Class<? extends Shape> theClass =
                        (Class<? extends Shape>) Class.forName(
					    className,
					    false,
					    this.getClass().getClassLoader());

                    if (this.superClass.isAssignableFrom(theClass)) {
                        this.classes.add(theClass);
                    }
                } catch (ClassNotFoundException cnfe) {
                	System.out.println(cnfe);
                } catch (Throwable t) {
                }
    }

    public List<Class<? extends Shape>> getClasses() {
        String classpath = System.getProperty("java.class.path");
        String pathSeparator = System.getProperty("path.separator");
        StringTokenizer st = new StringTokenizer(classpath,pathSeparator);
        while (st.hasMoreTokens()) {
            File currentDirectory = new File(st.nextToken());
            processFile(currentDirectory.getAbsolutePath(),"");
        }
        File dest = new File("plugin");
		if(!dest.exists()){
			dest.mkdirs();
		}
        this.getPlugin(dest);
        return this.classes;
    }
    
    private void getPlugin(File dest) {
    	File[] children = dest.listFiles();
        if (children == null || children.length == 0) {
            return;
        }
        Set directories = new HashSet();
        for (int i = 0; i < children.length; i++) {
            File child = children[i];
            if (child.isDirectory()) {
                directories.add(children[i]);
            } 
            else {
                if (child.getName().endsWith(".jar")) {
                   this.addPlugin(child.getPath());
                }
            }
        }
	}

	private void addPlugin(String jarPath) {
		try {
			List<Class<? extends Shape>> ret = new LinkedList<Class<? extends Shape>>();
			URL[] forLoader = new URL[] { (new File(jarPath)).toURI().toURL() };
			ClassLoader loader = URLClassLoader.newInstance(forLoader,
					getClass().getClassLoader());

			String path = (new File(jarPath)).getPath();

			JarInputStream jis = new JarInputStream(new FileInputStream(path));
			JarEntry entry = jis.getNextJarEntry();

			while (entry != null) {
				if (entry.getName().endsWith(".class")) {
					String className = getClassName(entry.getName());

					Class<?> theClass = Class.forName(className, true, loader);
					if (!theClass.isInterface()
							&& !Modifier.isAbstract(theClass.getModifiers())
							&& theClass.newInstance() instanceof Shape) {
						classes.add((Class<? extends Shape>) theClass);
					}

				}
				entry = jis.getNextJarEntry();
			}
			
			jis.close();
		} catch (Exception ex) {
		}
	}
    

    @SuppressWarnings({ "rawtypes", "unchecked" })
	private void processFile(String base, String current) {
        File currentDirectory = new File(base + File.separatorChar + current);

        if (isArchive(currentDirectory.getName())) {
            try {
                processZip(new ZipFile(currentDirectory));
            } catch (Exception e) {
            }
            return;
        }
        else {
            Set directories = new HashSet();
            File[] children = currentDirectory.listFiles();
            if (children == null || children.length == 0) {
                return;
            }
            for (int i = 0; i < children.length; i++) {
                File child = children[i];
                if (child.isDirectory()) {
                    directories.add(children[i]);
                } 
                else {
                    if (child.getName().endsWith(".class")) {
                        String className =
                            getClassName(
                                current +
                                ((current == "") ? "" : File.separator) +
                                child.getName());
                        addClass(className);
                    }
                }
            }
            for (Iterator i = directories.iterator(); i.hasNext(); ) {
                processFile(base, current + ((current=="")?"":File.separator) +
                    ((File)i.next()).getName());
            }
        }
    }

    protected boolean isArchive(String name) {
        if ((name.endsWith(".jar") || (name.endsWith(".zip")))) {
            return true;
        }
        else {
            return false;
        }
    }

    protected String getClassName(String fileName) {
        String newName =  fileName.replace(File.separatorChar,'.');
        newName =  newName.replace('/','.');
        return newName.substring(0, fileName.length() - 6);
    }

    @SuppressWarnings("rawtypes")
	protected void processZip(ZipFile file) {
        Enumeration files = file.entries();
        while (files.hasMoreElements()) {
            Object tfile = files.nextElement();
            ZipEntry child = (ZipEntry) tfile;
            if (child.getName().endsWith(".class")) {
                addClass(getClassName(child.getName()));
            }
        }
    }
}