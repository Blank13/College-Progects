package eg.edu.alexu.csd.oop.game;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import eg.edu.alexu.csd.oop.game.GameEngine.GameController;
import eg.edu.alexu.csd.oop.objects.PlateObject;
import eg.edu.alexu.csd.oop.objects.RackObject;
import eg.edu.alexu.csd.oop.objects.RectangleObject;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class StartFrame extends JFrame {

	private static final long serialVersionUID = -520405843332976909L;
	
	private JPanel contentPane;
	private static StartFrame instance;
	private World world;
	private GameController gameController = null;
	private Factory factory;
	private List<Class<? extends Shape>> movingShapes;
	private List<Class<? extends GameObject>> constantShapes;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StartFrame frame = StartFrame.getInstance();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public static StartFrame getInstance(){
		if(instance == null){
			instance = new StartFrame();
		}
		return instance;
	}

	/**
	 * Create the frame.
	 */
	private StartFrame() {
		
		JMenuBar  menuBar = new JMenuBar();;
		
		JMenu menu = new JMenu("File");
		JMenuItem pauseMenuItem = new JMenuItem("Pause");
		JMenuItem resumeMenuItem = new JMenuItem("Resume");
		JMenuItem saveItem = new JMenuItem("Save");
		JMenuItem loadItem = new JMenuItem("Load");
		menu.add(pauseMenuItem);
		menu.addSeparator();
		menu.add(resumeMenuItem);
		menu.addSeparator();
		menu.add(saveItem);
		menu.addSeparator();
		menu.add(loadItem);
		menuBar.add(menu);
		
		JMenu menu1 = new JMenu("New Levels");
		JMenuItem newEasy = new JMenuItem("Easy");
		JMenuItem newMedium = new JMenuItem("Medium");
		JMenuItem newHard = new JMenuItem("Hard");
		JMenuItem newCustom = new JMenuItem("Custom");
		menu1.add(newEasy);
		menu1.addSeparator();
		menu1.add(newMedium);
		menu1.addSeparator();
		menu1.add(newHard);
		menu1.addSeparator();
		menu1.add(newCustom);
		menuBar.add(menu1);
		
		factory = Factory.getInstance();
		movingShapes = new LinkedList<Class<? extends Shape>>();
		constantShapes = new LinkedList<Class<? extends GameObject>>();
		movingShapes.add(RectangleObject.class);
		movingShapes.add(PlateObject.class);
		constantShapes.add(RackObject.class);
		factory.setMovingClasses(movingShapes);
		factory.setConstantClasses(constantShapes);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 627, 439);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JButton bStart = new JButton("Start Game");
		bStart.setBounds(257, 268, 115, 29);
		bStart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(world != null){
					gameController = GameEngine.start("Hello to my Game", world, menuBar);
				}else{
					JOptionPane.showMessageDialog(null,"No world to begin!!", "Warning", JOptionPane.WARNING_MESSAGE);
				}
				
				newEasy.addActionListener(new SetActionListener(newEasy));
				newMedium.addActionListener(new SetActionListener(newMedium));
				newHard.addActionListener(new SetActionListener(newHard));
				newCustom.addActionListener(new SetActionListener(newCustom));
				pauseMenuItem.addActionListener(new ActionListener() {
					@Override public void actionPerformed(ActionEvent e) {
						gameController.pause();
					}
				});
				resumeMenuItem.addActionListener(new ActionListener() {
					@Override public void actionPerformed(ActionEvent e) {
						gameController.resume();
					}
				});
				saveItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String path = getPath();
						if(path != null){
							SnapShot s = new SnapShot();
							s.save(world, path+".txt");
						}else{
							JOptionPane.showMessageDialog(null,"wrong path to save game", "Warning", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				loadItem.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						String path = getPath();
						if(path != null){
							SnapShot s = new SnapShot();
							world = s.load(path);
							gameController.changeWorld(world);
						}else{
							JOptionPane.showMessageDialog(null,"wrong path for loaded game", "Warning", JOptionPane.WARNING_MESSAGE);
						}
					}
				});
				instance.setVisible(false);
			}
		});
		contentPane.add(bStart);
		
		JLabel lblNewLabel = new JLabel("Choose Difficulty");
		lblNewLabel.setBounds(15, 45, 153, 20);
		contentPane.add(lblNewLabel);
		
		JButton bEasy = new JButton("Easy");
		bEasy.setBounds(15, 80, 115, 29);
		bEasy.addActionListener(new SetActionListener(bEasy));
		contentPane.add(bEasy);
		
		JButton bMedium = new JButton("Medium");
		bMedium.setBounds(15, 112, 115, 29);
		bMedium.addActionListener(new SetActionListener(bMedium));
		contentPane.add(bMedium);
		
		JButton bHard = new JButton("Hard");
		bHard.setBounds(15, 143, 115, 29);
		bHard.addActionListener(new SetActionListener(bHard));
		contentPane.add(bHard);
		
		JButton bCustom = new JButton("Custom");
		bCustom.setBounds(15, 173, 115, 29);
		bCustom.addActionListener(new SetActionListener(bCustom));
		contentPane.add(bCustom);
		
		JButton bPlugin = new JButton("Add Shapes");
		bPlugin.setBounds(500, 173, 115, 29);
		contentPane.add(bPlugin);
		bPlugin.addActionListener(new addPlugin());
		
		JButton bLoad = new JButton("Load Game");
		bLoad.setBounds(500, 140, 115, 29);
		contentPane.add(bLoad);
		bLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String path = getPath();
				if(path != null){
					SnapShot s = new SnapShot();
					world = s.load(path);
				}else{
					JOptionPane.showMessageDialog(null,"wrong path for loaded game", "Warning", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
	}
	
	private class addPlugin implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String path = getPath();
			if(path == null){
				JOptionPane.showMessageDialog(null,"wrong path for jar", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			else{
				JarFile jarFile = null;
				try {
					jarFile = new JarFile(path);
					Enumeration<JarEntry> entry = jarFile.entries();

					URL[] urls = { new URL("jar:file:" + path+"!/") };
					URLClassLoader classLoader = URLClassLoader.newInstance(urls);
							
					movingShapes = new LinkedList<Class<? extends Shape>>();
					while (entry.hasMoreElements()) {
				        JarEntry jarEntry = (JarEntry) entry.nextElement();
				        if (jarEntry.getName().endsWith(".png")) {
				        	loadImage(jarEntry.getName(), classLoader, path.substring(0, path.lastIndexOf('\\')));
				        }
					}
					
					entry = jarFile.entries();
					while (entry.hasMoreElements()) {
						JarEntry jarEntry = (JarEntry) entry.nextElement();
						if (jarEntry.getName().endsWith(".class")){
						    String className = jarEntry.getName().substring(0,jarEntry.getName().length()-".class".length());
						    className = className.replace('/', '.');
						    Class<?> cl = classLoader.loadClass(className);
						    if (Shape.class.isAssignableFrom(cl)) {
						    	movingShapes.add((Class<? extends Shape>) cl);
						    } 
				        }
					}
					factory.setMovingClasses(movingShapes);
				} catch (ClassNotFoundException | IOException ex) {
					throw new RuntimeException(ex);
				} finally {
					try {
						jarFile.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}	
	}
	
	private class SetActionListener implements ActionListener{
		
		private JComponent com;
		
		public SetActionListener(JComponent c) {
			com = c;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String name = null,path = "";
			if(com instanceof JButton){
				JButton button = (JButton) e.getSource();
				 name = button.getText().toString();
			}else{
				JMenuItem button = (JMenuItem) e.getSource();
				name = button.getText().toString();
			}
			if(name.equals("Custom")){
				 path = getPath();
			}
			if(path == null){
				 JOptionPane.showMessageDialog(null,"wrong path for jar", "Warning", JOptionPane.WARNING_MESSAGE);
			}
			else{
				try {
					world = factory.createWorld(name, 1200, 650,path);
					if(com instanceof JMenuItem){
						gameController.changeWorld(world);
					}
				} catch (ClassNotFoundException | NoSuchMethodException
						| SecurityException | InstantiationException
						| IllegalAccessException | IllegalArgumentException
						| InvocationTargetException e1) {
					e1.printStackTrace();
				}
			}
			
		}		
	}
	
	private String getPath() {
		JFileChooser fc = new JFileChooser();
		fc.setDialogTitle("Load a mode Jar");
		fc.setFileFilter(new FileTypeFilter(".jar", "Jar File"));
		fc.setFileFilter(new FileTypeFilter(".txt", "Text File")); 
		int result = fc.showOpenDialog(null);
		if(result == JFileChooser.APPROVE_OPTION){
			String path = fc.getSelectedFile().getPath();
			return path;
		}
		return null;
	}
	
	public void loadImage(String fileName, URLClassLoader cl, String path){

	    BufferedImage buffer = null;
	    try {
	        buffer = ImageIO.read(cl.getResourceAsStream(fileName));
	        ImageIO.write(buffer, "png",new File(path+"/"+fileName));
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
}
