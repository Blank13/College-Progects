
public class Loop {
	
	private String path;
	private double gain;
	
	public Loop(String path, double gain) {
		this.path = path;
		this.gain = gain;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public double getGain() {
		return gain;
	}
	public void setGain(double gain) {
		this.gain = gain;
	}
}