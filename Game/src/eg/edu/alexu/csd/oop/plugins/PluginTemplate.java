package eg.edu.alexu.csd.oop.plugins;

public abstract class PluginTemplate {

	private EnvironmentIF environment;
	
	public void setEnvironment (EnvironmentIF environment) {
		this.environment = environment;
	}
	
	protected EnvironmentIF getEnvironment() {
		return environment;
	}
	
	public abstract void start();
	
}
