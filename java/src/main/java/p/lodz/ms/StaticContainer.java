package p.lodz.ms;

public class StaticContainer {

    private static StaticContainer instance;

    public static final String outputEventsFileName = "output_events.xml.gz";
    public static final String eventsFolderName = "events";
    public static final String networkGraphName = "network.png";
    public static final String networkFileName = "network.xml";
    public static final String configFileName = "config.xml";
    public static final String chromosomeFileName = "chromosome.txt";
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
