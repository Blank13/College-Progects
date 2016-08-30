import java.util.ArrayList;
import java.util.List;


public class Operations {
	
	private int nodes_num, loopStart, start = 1, end;
	private List<Path> paths = new ArrayList<Path>();
	private List<Loop> iLoops = new ArrayList<Loop>();
	private List<Loop> twoLoops = new ArrayList<Loop>();
	private List<Loop> threeLoops = new ArrayList<Loop>();
	private boolean[][] links;
	private double[][] gains;
	private double delta, tf;
	private List<Double> deltas = new ArrayList<Double>();
	
	public void start(boolean[][] links, double[][] gains, int start, int end, int inputNode){
		this.end = end;
		this.links = links;
		this.gains = gains;
		this.start = inputNode;
		nodes_num = links.length;
		findPaths(this.start, "", 1, 1);
		for(int i=1; i<=nodes_num; i++){
			loopStart = i;
			findILoops(i, "", 1, 1);
		}
		twoNonTouchin();
		threeNonTouchin();
		calculateDelta();
		calculateDeltas();
		calculateTF();
		if(inputNode != start){
			double tf1 = this.tf;
			List<Path> paths1 = new ArrayList<Path>();
			this.paths.add(0,new Path("From "+this.start+" to "+this.end, 0));
			paths1 = addPaths(paths1,this.paths);
			this.paths.clear();
			this.iLoops.clear();
			this.end = start;
			findPaths(this.start, "", 1, 1);
			for(int i=1; i<=nodes_num; i++){
				loopStart = i;
				findILoops(i, "", 1, 1);
			}
			calculateDeltas();
			calculateTF();
			this.tf = tf1 / this.tf;
			this.paths.add(0,new Path("From "+this.start+" to "+this.end, 0));
			this.paths = addPaths(this.paths, paths1);
		}
	}

	private List<Path> addPaths(List<Path> paths1, List<Path> paths2) {
		for(Path p2 : paths2){
			paths1.add(p2);
		}
		return paths1;
	}
	
	public List<Path> getPaths(){
		return this.paths;
	}
	
	public List<Loop> getILoops(){
		return this.iLoops;
	}
	
	public List<Loop> get2Loops(){
		return this.twoLoops;
	}
	
	public List<Loop> get3Loops(){
		return this.threeLoops;
	}
	public List<Double> getDeltas(){
		return this.deltas;
	}
	
	public Double getDelta(){
		return this.delta;
	}
	
	public double getTF(){
		return this.tf;
	}
	
	private void calculateTF() {
		int i = 0;
		tf = 0;
		for(Path path : paths){
			tf += path.getGain() * deltas.get(i);
			i ++;
		}
		tf /= delta;
	}

	private void calculateDeltas() {
		for(Path path : paths){
			double delta = 1;
			for(Loop loop : iLoops){
				if(! intersect(path.getPath(), loop.getPath()))
					delta -= loop.getGain();
			}
			for(Loop loop : twoLoops){
				if(! intersect(path.getPath(), loop.getPath()))
					delta += loop.getGain();
			}
			for(Loop loop : threeLoops){
				if(! intersect(path.getPath(), loop.getPath()))
					delta -= loop.getGain();
			}
			deltas.add(delta);
		}
	}

	private void calculateDelta() {
		delta = 1;
		for(Loop loop : iLoops){
			delta -= loop.getGain();
		}
		for(Loop loop : twoLoops){
			delta += loop.getGain();
		}
		for(Loop loop : threeLoops){
			delta -= loop.getGain();
		}
	}

	private void findPaths(int start, String path, double total, double gain){
		path += start;
		total *= gain;
		if(start == end){
			Path new_path = new Path(path, total);
			paths.add(new_path);
		}else{
			for(int j=1; j<=nodes_num; j++){
				String temp = "";
				temp += j;
				if(start != j && !path.contains(temp) && links[start-1][j-1]){
					findPaths(j, path, total, gains[start-1][j-1]);
				}
			}
		}
	}
	
	private void findILoops(int start, String loop, double total, double gain){
		loop += start;
		total *= gain;
		if(start == loopStart && loop.length() > Integer.toString(loopStart).length()){
			if(! found(loop,iLoops)){
				Loop new_loop = new Loop(loop, total);
				iLoops.add(new_loop);
			}
		}else{
			for(int j=1; j<=nodes_num; j++){
				String temp = "";
				temp += j;
				if( (start != j || j == loopStart) && (!loop.contains(temp) || j == loopStart) && links[start-1][j-1]){
					findILoops(j, loop, total, gains[start-1][j-1]);
				}
			}
		}
	}
	
	private void twoNonTouchin(){
		for(int i=0; i<iLoops.size(); i++){
			Loop one = iLoops.get(i);
			String x = one.getPath();
			for(int j=i+1; j<iLoops.size(); j++){
				Loop two = iLoops.get(j);
				String y = two.getPath();
				if(!intersect(x,y)){
					Loop new_loop = new Loop(x+y, one.getGain()*two.getGain());
					twoLoops.add(new_loop);
				}
			}
		}
	}
	
	private void threeNonTouchin(){
		for(int i=0; i<iLoops.size(); i++){
			Loop one = iLoops.get(i);
			String x = one.getPath();
			for(int j=0; j<twoLoops.size(); j++){
				Loop two = twoLoops.get(j);
				String y = two.getPath();
				if(!intersect(x,y)){
					if(!found(x+y,threeLoops)){
						Loop new_loop = new Loop(x+y, one.getGain()*two.getGain());
						threeLoops.add(new_loop);
					}
				}
			}
		}
	}

	private boolean intersect(String x, String y) {
		for(int i=0; i<x.length(); i++){
			if(y.contains(x.substring(i, i+1)))
				return true;
		}
		return false;
	}
	
	private boolean found(String x, List<Loop> iLoops2){
		boolean found = false;
		for(Loop loop : iLoops2){
			String temp = loop.getPath();
			if(x.length() == temp.length()){
				found = true;
				for(int i=0; i<temp.length(); i++){
					if(!x.contains(temp.substring(i, i+1))){
						found = false;
						break;
					}
				}
			}
			if(found)
				return true;
		}
		return false;
	}
}
