import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.commons.collections15.Factory;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.samples.AddNodeDemo;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

/**
 * 
 * @author Ahmed Hafez & Mohamed Elsayed 
 *
 */
public class GraphGUI {
    private Graph<MyVertex, MyEdge> g;
    private static VisualizationViewer<MyVertex, MyEdge> vv;
    private int edgeCount , start=1 , end=0 ,inputNode=1;
    private boolean isEndSetted = false;
    private Factory<MyVertex> vertexFactory;
    private Factory<MyEdge> edgeFactory;
    private List<MyVertex> vertices = new ArrayList<MyVertex>();
	private List<MyEdge> edges = new ArrayList<MyEdge>();
	private static JMenuBar menuBar;
  
    public GraphGUI() {
        g = new DirectedSparseGraph<MyVertex, MyEdge>();
        edgeCount = 0;
        vertexFactory = new Factory<MyVertex>() {
            public MyVertex create() {
                return new MyVertex(vertices.size()+1);
            }
        };
        edgeFactory = new Factory<MyEdge>() {
            public MyEdge create() {
            	while(true){
	            	try{
	            		double weight = Double.parseDouble(JOptionPane.showInputDialog("Enter weight of edge"));
	                	MyEdge e = new MyEdge(weight);
	                    return e;
	            	}catch(Exception e1){
	            		JOptionPane.showMessageDialog(null, "enter a correct number to the weight"
	    						,"Warning",JOptionPane.WARNING_MESSAGE);
	            	}
            	}
            }
        };
        menuBar = new JMenuBar();
        JMenu sfgMenu = new JMenu("SFG");
        JMenu vertixMenu = new JMenu("Node editing");
        JMenuItem solver = new JMenuItem("SFG Solve");
        JMenuItem start = new JMenuItem("Set start");
        JMenuItem end = new JMenuItem("Set end");
        JMenuItem input = new JMenuItem("Set input node");
        JMenuItem deleteVertex = new JMenuItem("Delete Node");
   
        sfgMenu.add(solver);
        sfgMenu.add(start);
        sfgMenu.add(end);
        sfgMenu.add(input);
        vertixMenu.add(deleteVertex);
   
        menuBar.add(sfgMenu);
        menuBar.add(vertixMenu);
        Solver solve = new Solver();
		solver.addActionListener(solve);
		SetStart ss = new SetStart();
		start.addActionListener(ss);
		SetEnd se = new SetEnd();
		end.addActionListener(se);
		SetInput si = new SetInput();
		input.addActionListener(si);
		DeleteNode dn = new DeleteNode();
		deleteVertex.addActionListener(dn);
    }
    
    public static void main(String[] args) {
    	GraphGUI sfg = new GraphGUI();
        Layout<MyVertex, MyEdge> layout = new StaticLayout<MyVertex, MyEdge>(sfg.g);
        layout.setSize(new Dimension(300,300));
        vv = new VisualizationViewer<MyVertex, MyEdge>(layout);
        vv.setPreferredSize(new Dimension(1000,600));
        vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<MyVertex>());
        vv.getRenderContext().setEdgeLabelTransformer(new ToStringLabeller<MyEdge>());
        vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
        EditingModalGraphMouse<MyVertex, MyEdge> gm = new EditingModalGraphMouse<MyVertex, MyEdge>(vv.getRenderContext(), 
                 sfg.vertexFactory, sfg.edgeFactory); 
        vv.setGraphMouse(gm);
        
        JFrame frame = new JFrame("Signal Flow Graph");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(vv);
        
        JMenu modeMenu = gm.getModeMenu();
        modeMenu.setText("Graph making");
        modeMenu.setIcon(null); 
        modeMenu.setPreferredSize(new Dimension(100,20));
        
        menuBar.add(modeMenu);
       
        frame.setJMenuBar(menuBar);
        gm.setMode(null);
        frame.pack();
        frame.setVisible(true);  
        
    }
    
    public class Solver implements ActionListener{

    	private boolean[][] links;
    	private double[][] gains;
    	
		@Override
		public void actionPerformed(ActionEvent solve) {
			if(!isEndSetted){
				end = vertices.size();
			}
			links = new boolean[vertices.size()][vertices.size()];
			gains = new double[vertices.size()][vertices.size()];
			getLinks();
			Operations op = new Operations();
			op.start(links, gains, start, end,inputNode);
			
			SFGSolution sfg = new SFGSolution(op.getPaths(),op.getILoops(),
					op.get2Loops(),op.get3Loops(), op.getTF(), op.getDeltas() ,op.getDelta());
		}

		private void getLinks() {
			Collection<MyEdge> edges = g.getEdges();
			for (MyEdge e : edges){
				e.setVertices();
				int from = e.from , to = e.to;
				links[from-1][to-1] = true;
				gains[from-1][to-1] = e.weight;
			}
		}
    	
    }
    
    public class SetStart implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int start2 = 1 ;
			try{
				start2 = Integer.parseInt(JOptionPane.showInputDialog("Enter Start node :"));
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "enter a correct number to the start"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
			if(start2 < vertices.size()){
				start = start2;
			}
			else{
				JOptionPane.showMessageDialog(null, "enter a number to the start in correct range"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
    }
    
    public class SetEnd implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent setEnd) {
			int end2 = 0;
			try{
				end2 = Integer.parseInt(JOptionPane.showInputDialog("Enter End node :"));
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "enter a correct number to the end"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
			if(end2 <= vertices.size()){
				isEndSetted = true;
				end = end2;
			} else{
				JOptionPane.showMessageDialog(null, "enter a number to the end in correct range"
					,"Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
    	
    }
    
    public class SetInput implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int input2 = 1 ;
			try{
				input2 = Integer.parseInt(JOptionPane.showInputDialog("Enter input node :"));
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "enter a correct number to the input node"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
			if(input2 < vertices.size()){
				inputNode = input2;
			}
			else{
				JOptionPane.showMessageDialog(null, "enter a number to the input node in correct range"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
    }
    
    public class DeleteNode implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int number = 0;
			try{
				number = Integer.parseInt(JOptionPane.showInputDialog("Enter input node :"));
			}catch(Exception e){
				JOptionPane.showMessageDialog(null, "enter a correct number to the input node"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
			if(number <= vertices.size()){
				boolean found = false;
				for(MyVertex v1 : vertices){
					if(v1.getValue() == number){
						vertices.remove(v1);
						g.removeVertex(v1);
						found = true;
						break;
					}
				}
				if(found){
					for(int i=0;i<vertices.size();i++){
						MyVertex v1 = vertices.get(i);
						v1.setValue(i+1);
					}
					Collection<MyEdge> edges2 = g.getEdges();
					edges.clear();
					for (MyEdge e : edges2){
						edges.add(e);
					}
				}
				vv.repaint();
			}
			else{
				JOptionPane.showMessageDialog(null, "enter a number to the input node in correct range"
						,"Warning",JOptionPane.WARNING_MESSAGE);
			}
		}
    }
    
    private class MyVertex{
    	private Integer id;
    	
    	private MyVertex(int id) {
    		this.id = id;
    		vertices.add(this);
    	}
    	
    	public int getValue(){
    		return id;
    	}
    	
    	public void setValue(int id){
    		this.id = id;
    	}
    	
    	public String toString() { 
    		return id.toString();
    	}
    }
    
    private class MyEdge {
    	private Double weight; 
    	private int from = 0 , to = 0;
    	
    	private MyEdge(double weight) {
	    	edgeCount++;
	    	this.weight = weight;
	    	edges.add(this);
    	}
    	
    	public void setVertices() {
    		Collection<MyVertex> incidentVertices = g.getIncidentVertices(this);
			for (MyVertex v : incidentVertices) {
				if (from == 0) {
					from = v.getValue();
				} else {
					to = v.getValue();
				}
			}
			edges.remove(this);
			edges.add(this);
		}

		public String toString() {
			return weight.toString();
    		
    	}
    }
}
