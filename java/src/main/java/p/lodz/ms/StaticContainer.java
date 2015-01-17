package p.lodz.ms;

public class StaticContainer {

    private static StaticContainer instance;

    public static final String tripDurationsFileName = "parsed_tripdurations.txt";
    private static final String gaIterationPrefix = "ga.";

    // Always starts at 0 iteration
    private Integer gaCurrentIteration = 0;

    public static StaticContainer getInstance() {
	if (instance == null) {
	    synchronized (Configuration.class) {
		if (instance == null) {
		    instance = new StaticContainer();
		}
	    }
	}
	return instance;
    }

    private StaticContainer() {
    }

    public Integer getGaCurrentIteration() {
	return gaCurrentIteration;
    }

    public void setGaCurrentIteration(Integer gaCurrentIteration) {
	this.gaCurrentIteration = gaCurrentIteration;
    }

    public String getGaCurrentIterationPath() {
	return new String(gaIterationPrefix + getGaCurrentIteration()+"/");
    }
}
