package eg.edu.alexu.csd.oop.draw;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.*;

public class Paint {
	
	private JPanel panel1 , panel2 , panel3 ,panel4;
	private JLabel colorL , fillColorL , messageL;
	private JButton removeB,resizeB,undoB,redoB,colorB,fillColorB,resetB,changeColorB,addPluginB;
	private JToggleButton selectB,moveB;
	private JMenuBar menuBar;
	private JMenu file;
	private List<Class<? extends Shape>> shapesClasses = new LinkedList<Class<? extends Shape>>();
	private List<JButton> buttonsList = new LinkedList<JButton>();
	private Point p;
	private Color color = Color.BLACK , fillColor = null;
	private Shape selectedShape;
	
	PaintingEngine engine = new PaintingEngine();

	public Paint(){
		JFrame frame = new JFrame("Paint");
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setExtendedState(javax.swing.JFrame.MAXIMIZED_BOTH);
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		shapesClasses = engine.getSupportedShapes();
		
		menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		file = new JMenu("File");
		menuBar.add(file);
		JMenuItem addPluginMI = new JMenuItem("Add plugin");
		file.add(addPluginMI);
		JMenuItem saveMI = new JMenuItem("Save");
		file.add(saveMI);
		JMenuItem loadMI = new JMenuItem("Load");
		file.add(loadMI);
		JMenuItem exitMI = new JMenuItem("Exit");
		file.add(exitMI);
		
		
		panel1 = new JPanel();
		panel2 = new JPanel();
		panel3 = new JPanel();
		panel4 = new JPanel();
		
		selectB = new JToggleButton("select");
		moveB = new JToggleButton("move");
		resizeB = new JButton("resize");
		removeB = new JButton("remove");
		undoB = new JButton("undo");
		redoB = new JButton("redo");
		colorB = new JButton("");
		colorB.setBackground(color);
		fillColorB = new JButton("null");
		resetB = new JButton("reset");
		changeColorB = new JButton("Chang Color");
		addPluginB = new JButton("add plugin");
		
		colorL = new JLabel("color");
		fillColorL = new JLabel("Fill color");
		messageL = new JLabel(" ");
		
		panel1.setBackground(Color.WHITE);
		content.add(panel1,BorderLayout.CENTER);
		panel1.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				p = e.getPoint();
				System.out.println(p);
				if(selectB.isSelected()){
					selectedShape = getNearShape(engine.getShapes(),p);
					if(selectedShape == null){
						JOptionPane.showMessageDialog(null,"there is no shape to select","Warning",JOptionPane.WARNING_MESSAGE);
						selectB.setSelected(false);
						messageL.setText(" ");
					}
					else{
						messageL.setText(selectedShape.getClass().getSimpleName());
						selectB.setSelected(false);
						color = selectedShape.getColor();
						fillColor = selectedShape.getFillColor();
						colorB.setBackground(color);
						if(color == null){
							colorB.setText("null");
						}
						else{
							colorB.setText("");
						}
						fillColorB.setBackground(fillColor);
						if(fillColor == null){
							fillColorB.setText("null");
						}
						else{
							fillColorB.setText("");
						}
						panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
						engine.refresh(panel1.getGraphics());
					}
				}
				else if(moveB.isSelected()){
					if(selectedShape == null){
						JOptionPane.showMessageDialog(null,"there is no selected shape","Warning",JOptionPane.WARNING_MESSAGE);
					}
					else{
						Shape s = null;
						try {
							s = (Shape) selectedShape.clone();
						} catch (CloneNotSupportedException e1) {
						}
						s.setPosition(p);
						engine.updateShape(selectedShape, s);
						selectedShape = null;
						messageL.setText(" ");
						moveB.setSelected(false);
						panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
						engine.refresh(panel1.getGraphics());
					}
				}
				panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
				engine.refresh(panel1.getGraphics());
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {	
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		});
		
		panel2.setLayout(new GridLayout(shapesClasses.size()-4, 1));
		content.add(panel2, BorderLayout.WEST);
		AddShape addShape = new AddShape();
		int j=0;
		for(int i = 0; i<shapesClasses.size();i++){
			String name = shapesClasses.get(i).getSimpleName();
			if(name.equals("MyShape") || name.equals("Ellipticals")|| name.equals("Polygons") || name.equals("RegularPolygons")){
				continue;
			}
			else{
				JButton b = new JButton(name);
				b.addActionListener(addShape);
				buttonsList.add(b);
				panel2.add(buttonsList.get(j));
				j++;
			}
		}
		
		content.add(panel3, BorderLayout.EAST);
		panel3.setLayout(new GridLayout(13, 1));
		panel3.add(selectB);
		panel3.add(moveB);
		panel3.add(resizeB);
		panel3.add(removeB);
		panel3.add(undoB);
		panel3.add(redoB);
		panel3.add(changeColorB);
		panel3.add(colorL);
		panel3.add(colorB);
		panel3.add(fillColorL);
		panel3.add(fillColorB);
		panel3.add(resetB);
		panel3.add(addPluginB);
		
		
		content.add(panel4, BorderLayout.SOUTH);
		panel4.add(messageL);
		
		ColorChange c = new ColorChange();
		colorB.addActionListener(c);
		fillColorB.addActionListener(c);
		Reset reset = new Reset();
		resetB.addActionListener(reset);
		Remove remove = new Remove();
		removeB.addActionListener(remove);
		Undo undo = new Undo();
		undoB.addActionListener(undo);
		Redo redo = new Redo();
		redoB.addActionListener(redo);
		Select select = new Select();
		selectB.addActionListener(select);
		Move move = new Move();
		moveB.addActionListener(move);
		Resize resize = new Resize();
		resizeB.addActionListener(resize);
		ShapeChangeColor shapeColor = new ShapeChangeColor();
		changeColorB.addActionListener(shapeColor);
		AddPlugin adpl = new AddPlugin(); 
		addPluginB.addActionListener(adpl);
		
		addPluginMI.addActionListener(adpl);
		Save save = new Save();
		saveMI.addActionListener(save);
		Load load = new Load();
		loadMI.addActionListener(load);
		Exit exit = new Exit();
		exitMI.addActionListener(exit);
	}
	
	
	public class AddShape implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent add) {	
			if(p==null){
				JOptionPane.showMessageDialog(null, "Click on the drawing area first to"
						+ " set the position","Warning",JOptionPane.WARNING_MESSAGE);
				return;
			}
			if(color != null || fillColor != null){
				messageL.setText(" ");
				String className = new String();
				for(int i = 0; i<buttonsList.size();i++){
					if(add.getSource() == buttonsList.get(i)){
						className = buttonsList.get(i).getText();
						break;
					}
				}
				try {
					Shape s = null;
					for(int i = 0; i<shapesClasses.size();i++){
						if(className.equals(shapesClasses.get(i).getSimpleName())){
							s = shapesClasses.get(i).newInstance();
							break;
						}
					}
					Map<String, Double> propertiesMap = s.getProperties();
					Set<String> set = propertiesMap.keySet();
					Iterator<String> i = set.iterator();
					try{
						while(i.hasNext()){
							String key = i.next();
							Double x = Double.parseDouble(JOptionPane.showInputDialog("Enter "+key,""));
							propertiesMap.put(key, x);
						}
						s.setPosition(p);
						s.setColor(color);
						s.setFillColor(fillColor);
						s.setProperties(propertiesMap);
						engine.addShape(s);
						engine.refresh(panel1.getGraphics());
					}catch(Exception e){
						throw new RuntimeException();
					}
				}catch (RuntimeException e) {
					JOptionPane.showMessageDialog(null,"something is not setted","Warning",JOptionPane.WARNING_MESSAGE);
				}catch (Exception e) {
					JOptionPane.showMessageDialog(null,"Unexpected error in class constructor","Warning",JOptionPane.WARNING_MESSAGE);
				}
			}
			else{
				JOptionPane.showMessageDialog(null,"color is not setted","Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
	
	}
	
	public class Undo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				engine.undo();
				panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
				engine.refresh(panel1.getGraphics());
			}catch(RuntimeException e1){
				JOptionPane.showMessageDialog(null,"can't undo any more","Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public class Redo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				engine.redo();
				panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
				engine.refresh(panel1.getGraphics());
			}catch(RuntimeException e1){
				JOptionPane.showMessageDialog(null,"can't redo any more","Warning",JOptionPane.WARNING_MESSAGE);
			}
			
		}
	}
	
	public class Remove implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			try{
				engine.removeShape(selectedShape);
				panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
				selectedShape=null;
				messageL.setText(" ");
				engine.refresh(panel1.getGraphics());
				selectB.setSelected(false);
			}catch(Exception e1){
				JOptionPane.showMessageDialog(null,"there is no shape to remove","Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
	}
	
	public class Select implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectB.isSelected() && selectedShape != null){
				selectedShape = null;
				messageL.setText(" ");
				selectB.setSelected(false);
			}
		}
	}
	
	public class Move implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(selectedShape == null){
				JOptionPane.showMessageDialog(null,"there is no selected shape","Warning",JOptionPane.WARNING_MESSAGE);
				moveB.setSelected(false);
			}
			panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
			engine.refresh(panel1.getGraphics());
		}
	
	}
	
	public class Resize implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(selectedShape == null){
				JOptionPane.showMessageDialog(null,"there is no shape selected","Warning",JOptionPane.WARNING_MESSAGE);
				return;
			}
			Map<String, Double> propertiesMap = selectedShape.getProperties();
			Shape s = null;
			try {
				s = (Shape) selectedShape.clone();
			} catch (CloneNotSupportedException e1){
			}
			Set<String> set = propertiesMap.keySet();
			Iterator<String> i = set.iterator();
			try{
				while(i.hasNext()){
					String key = i.next();
					Double x = Double.parseDouble(JOptionPane.showInputDialog("Enter "+key,propertiesMap.get(key)));
					propertiesMap.put(key, x);
				}
				s.setProperties(propertiesMap);
				engine.updateShape(selectedShape, s);
				selectedShape = null;
				messageL.setText(" ");
				panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
				engine.refresh(panel1.getGraphics());
			}catch(Exception e){
				throw new RuntimeException();
			}
		}
	
	}
	
	public class ColorChange implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == colorB){
				color = JColorChooser.showDialog(null, "pick your color", color);
				colorB.setText("");
				colorB.setBackground(color);
			}
			else{
				fillColor = JColorChooser.showDialog(null, "pick your color", fillColor);
				fillColorB.setText("");
				fillColorB.setBackground(fillColor);
			}
		}
	}
	
	public class Reset implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			color = null;
			colorB.setText("null");
			colorB.setBackground(color);
			fillColor = null;
			fillColorB.setText("null");
			fillColorB.setBackground(fillColor);
			p=null;
		}
	}
	
	public class ShapeChangeColor implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(selectedShape != null){
				Shape s = null;
				try {
					s = (Shape) selectedShape.clone();
				} catch (CloneNotSupportedException e1) {
				}
				if(colorB.getText().equals("null")){
					color = null;
				}
				else{
					color = colorB.getBackground();
				}
				if(fillColorB.getText().equals("null")){
					fillColor = null;
				}
				else{
					fillColor = fillColorB.getBackground();
				}
				s.setColor(color);
				s.setFillColor(fillColor);
				engine.updateShape(selectedShape, s);
				selectedShape = null;
				messageL.setText(" ");
				panel1.getGraphics().clearRect(0, 0, panel1.getWidth(), panel1.getHeight());
				engine.refresh(panel1.getGraphics());
			}
		}
	}
	
	public class AddPlugin implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent args0) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Add plugin");
			fc.setFileFilter(new FileTypeFilter(".jar","JAR File"));
			int result = fc.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				try{
					File dest = new File("plugin");
					copyToDirectory(file, dest);
					JOptionPane.showMessageDialog(null,"Please reopen the app to add your plugin","Warning",JOptionPane.WARNING_MESSAGE);
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"Wrong jar format","Warning",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
		
	}
	
	public class Save implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Save a file");
			fc.setFileFilter(new FileTypeFilter(".XML","XML File"));
			fc.setFileFilter(new FileTypeFilter(".JSON","JSON File"));
			int result = fc.showSaveDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				try{
					engine.save(file.getPath());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"can't save wrong extension","Warning",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	
	}
	
	public class Load implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			JFileChooser fc = new JFileChooser();
			fc.setDialogTitle("Open a file");
			fc.setFileFilter(new FileTypeFilter(".XML","XML File"));
			fc.setFileFilter(new FileTypeFilter(".JSON","JSON File"));
			int result = fc.showOpenDialog(null);
			if(result == JFileChooser.APPROVE_OPTION){
				File file = fc.getSelectedFile();
				try{
					engine.load(file.getPath());
					engine.refresh(panel1.getGraphics());
				}catch(Exception e){
					JOptionPane.showMessageDialog(null,"can't load wrong extension","Warning",JOptionPane.WARNING_MESSAGE);
				}
			}
		}
	
	}
	
	public class Exit implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent arg0) {
			System.exit(0);
		}
	
	}
	
	private Shape getNearShape(Shape[] shapes, Point p2) {
		double Distance , minDistance = Integer.MAX_VALUE;
		Shape s = null;
		for(int i=0;i<shapes.length;i++){
			if(i==0){
				Distance = Math.sqrt(Math.pow(Math.abs(p2.x-shapes[i].getPosition().x), 2)+Math.pow(Math.abs(p2.y-shapes[i].getPosition().y), 2));
				minDistance = Distance;
				s = shapes[i];
			}
			else{
				Distance = Math.sqrt(Math.pow(Math.abs(p2.x-shapes[i].getPosition().x), 2)+Math.pow(Math.abs(p2.y-shapes[i].getPosition().y), 2));
				if(Distance <= minDistance){
					minDistance = Distance;
					s = shapes[i];
				}
			}
		}
		return s;
	}
	
	private void copyToDirectory(File sourceLocation, File targetLocation) throws IOException {
		if (sourceLocation.isDirectory()) {
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}
			String[] children = sourceLocation.list();
			for (int i = 0; i < children.length; i++) {
				copyToDirectory(new File(sourceLocation, children[i]), new File(targetLocation, children[i]));
			}
		} 
		else if(targetLocation.isDirectory()){
			if (!targetLocation.exists()) {
				targetLocation.mkdir();
			}
			InputStream in = new FileInputStream(sourceLocation);
			OutputStream out = new FileOutputStream(targetLocation+File.separator+sourceLocation.getName());
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.close();
		}
	}

	
	public static void main(String[] args){
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				Paint frame = new Paint();
			}
		});
	}
}
