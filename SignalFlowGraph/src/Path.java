
public class Path {
	
	private String path;
	private double gain;
	
	public Path(String path, double gain) {
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
