
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;

public class SFGSolution {
		
	public SFGSolution(List<Path> paths, List<Loop> iLoops, List<Loop> twoLoops,
			List<Loop> threeLoops, double tf, List<Double> deltas, Double delta){
		
		JFrame frame = new JFrame();
		frame.setTitle("SFG Solution");
		frame.setSize(1000, 640);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setBackground(Color.WHITE);
		frame.setVisible(true);
		
		String[][] spaths = makePaths(paths);
		String[][] spathsGains = makePathGains(paths);
		String[][] sILoops = makeLoops(iLoops);
		String[][] sILoopsGains = makeLoopsGains(iLoops);
		String[][] sNonTouchingloops = makeLoops2(twoLoops,threeLoops);
		String[][] sNonTouchingloopsGains = makeLoops2Gains(twoLoops,threeLoops);
		String[][] sDeltas = makeDeltas(deltas,paths);
		String[] nPaths = {"Forward paths"};
		String[] nILoops = {"All Loops"};
		String[] nNontouchingLoops = {"Non touching loops"};
		String[] nGains = {"Gains"};
		String[] nDeltas = {"Deltas"};
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JPanel panel3 = new JPanel();
		
		content.add(panel1, BorderLayout.NORTH);
		content.add(panel2, BorderLayout.CENTER);
		content.add(panel3, BorderLayout.SOUTH);
		
		JTable tForwardPath = new JTable(spaths,nPaths);
		JTable tILoops = new JTable(sILoops,nILoops);
		JTable tNonTouchingLoops = new JTable(sNonTouchingloops,nNontouchingLoops);
		JTable tForwardPathGain = new JTable(spathsGains,nGains);
		JTable tILoopsGain = new JTable(sILoopsGains,nGains);
		JTable tNonTouchingLoopsGain = new JTable(sNonTouchingloopsGains,nGains);
		JTable tDeltas = new JTable(sDeltas,nDeltas);
		
		JLabel lFP = new JLabel("Forward paths");
		JLabel lIL = new JLabel("All loops");
		JLabel lNL = new JLabel("Non touching loops");
		JLabel Gain1 = new JLabel("Gains");
		JLabel Gain2 = new JLabel("Gains");
		JLabel Gain3 = new JLabel("Gains");
		JLabel lDelta = new JLabel("Deltas value");
		
		panel1.setLayout(new GridLayout(1, 7));
		panel2.setLayout(new GridLayout(1, 7));
		panel3.setLayout(new GridLayout(2, 1));
		
		tForwardPath.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*2/3,panel2.getHeight()));
		tForwardPath.setEnabled(false);
		tILoops.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*2/3,panel2.getHeight()));
		tILoops.setEnabled(false);
		tNonTouchingLoops.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*2/3,panel2.getHeight()));
		tNonTouchingLoops.setEnabled(false);
		tDeltas.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*2/3,panel2.getHeight()));
		tDeltas.setEnabled(false);
		tForwardPathGain.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*1/3,panel2.getHeight()));
		tForwardPathGain.setBackground(Color.PINK);
		tForwardPathGain.setEnabled(false);
		tILoopsGain.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*1/3,panel2.getHeight()));
		tILoopsGain.setBackground(Color.PINK);
		tILoopsGain.setEnabled(false);
		tNonTouchingLoopsGain.setPreferredScrollableViewportSize(new Dimension((panel2.getWidth()/3)*1/3,panel2.getHeight()));
		tNonTouchingLoopsGain.setBackground(Color.PINK);
		tNonTouchingLoopsGain.setEnabled(false);

		Font font = new Font("Serif", Font.BOLD, 16);
		tForwardPath.setFont(font);
		tForwardPathGain.setFont(font);
		tILoops.setFont(font);
		tILoopsGain.setFont(font);
		tNonTouchingLoops.setFont(font);
		tNonTouchingLoopsGain.setFont(font);
		tDeltas.setFont(font);
		
		panel1.add(lFP);
		panel1.add(Gain1);
		panel1.add(lIL);
		panel1.add(Gain2);
		panel1.add(lNL);
		panel1.add(Gain3);
		panel1.add(lDelta);
		panel2.add(tForwardPath);
		panel2.add(tForwardPathGain);
		panel2.add(tILoops);
		panel2.add(tILoopsGain);
		panel2.add(tNonTouchingLoops);
		panel2.add(tNonTouchingLoopsGain);
		panel2.add(tDeltas);
		
		JLabel LDelta = new JLabel("Delta = "+delta);
		JLabel LTF = new JLabel("Over all transfer function = "+tf);
		font = new Font("Serif", Font.PLAIN, 32);
		LDelta.setFont(font);
		LTF.setFont(font);
		panel3.add(LDelta);
		panel3.add(LTF);
	}

	private String[][] makeDeltas(List<Double> deltas, List<Path> paths) {
		String[][] s = new String[paths.size()][1];
		int counter =0;
		for(int i=0;i<paths.size();i++){
			if(!paths.get(i).getPath().startsWith("From")){
				s[i][0] = deltas.get(counter).toString();
				counter++;
			}
		}
		return s;
	}

	private String[][] makeLoops2Gains(List<Loop> twoLoops, List<Loop> threeLoops) {
		String[][] l = new String[twoLoops.size()+threeLoops.size()][1];
		int i =0;
		for(Loop l2 : twoLoops){
			Double d =  l2.getGain();
			l[i++][0] = d.toString();
		}
		for(Loop l2 : threeLoops){
			Double d =  l2.getGain();
			l[i++][0] = d.toString();
		}
		return l;
	}

	private String[][] makeLoopsGains(List<Loop> iLoops) {
		String[][] l = new String[iLoops.size()][1];
		int i =0;
		for(Loop l2 : iLoops){
			Double d =  l2.getGain();
			l[i++][0] = d.toString();
		}
		return l;
	}

	private String[][] makePathGains(List<Path> paths) {
		String[][] p = new String[paths.size()][1];
		int i =0;
		for(Path p2 : paths){
			if(p2.getPath().startsWith("From")){
				p[i++][0] = "";
			}
			else{
				Double d = p2.getGain();
				p[i++][0] = d.toString();
			}
		}
		return p;
	}

	private String[][] makeLoops2(List<Loop> twoLoops, List<Loop> threeLoops) {
		String[][] l = new String[twoLoops.size()+threeLoops.size()][1];
		int i =0;
		for(Loop l2 : twoLoops){
			String s = l2.getPath();
			String s2 = new String("");
			char c = s.charAt(0);
			s2 += c;
			int j = 1;
			while(j < s.length()-1){
				s2 += s.charAt(j);
				if(s.charAt(j) == c){
					s2 += ",";
				}
				j++;
			}
			s2 += s.charAt(j);
			l[i++][0] = s2;
		}
		for(Loop l2 : threeLoops){
			String s = l2.getPath();
			String s2 = new String("");
			char c = s.charAt(0);
			s2 += c;
			int j = 1;
			while(j < s.length()-1){
				if(s.charAt(j) == c){
					s2 += s.charAt(j);
					if(j+1 < s.length()){
						s2 += ",";
						c = s.charAt(j+1);
						j++;
					}
				}
				if(j+1 < s.length()){
					s2 += s.charAt(j);
					j++;
				}
			}
			s2 += s.charAt(j);
			l[i++][0] = s2;
		}
		return l;
	}

	private String[][] makeLoops(List<Loop> iLoops) {
		String[][] l = new String[iLoops.size()][1];
		int i =0;
		for(Loop l2 : iLoops){
			l[i++][0] = l2.getPath();
		}
		return l;
	}

	private String[][] makePaths(List<Path> paths) {
		String[][] p = new String[paths.size()][1];
		int i =0;
		for(Path p2 : paths){
			p[i++][0] = p2.getPath();
		}
		return p;
	}
}
