package p.lodz.ms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class Context {

    private static Context instance;

    public static final String facilitiesGraphName = "facilities.png";
    public static final String outputEventsFileName = "output_events.xml.gz";
    public static final String eventsFolderName = "events";
    public static final String networkGraphName = "network.png";
    public static final String networkFileName = "network.xml";
    public static final String configFileName = "config.xml";
    public static final String chromosomeFileName = "chromosome.txt";
    public static final String fitnessFilename = "fitness.txt";
    public static final String fitnessInitialFilename = "fitnessInitial.txt";

    // properties
    private Properties properties;

    // main configuration
    private Configuration config;

    // starting value does not matter
    private String gaIterationPrefix;

    // Should start at 0 iteration
    private Integer gaCurrentIteration = 0;

    private Thread closeChildThread;
    private List<Process> childProcess = Collections
	    .synchronizedList(new ArrayList<Process>());

    public static Context getI() {
	if (instance == null) {
	    synchronized (Configuration.class) {
		if (instance == null) {
		    instance = new Context();
		}
	    }
	}
	return instance;
    }

    private Context() {
	// just a thread that kills the child processes
	closeChildThread = new Thread() {
	    public void run() {
		for (Process p : childProcess) {
		    p.destroy();
		}
	    }
	};
    }

    // Current iteration stuff
    public Integer getGaCurrentIteration() {
	return gaCurrentIteration;
    }

    public void setGaCurrentIteration(Integer gaCurrentIteration) {
	this.gaCurrentIteration = gaCurrentIteration;
    }

    public void increaseGaCurrentIteration() {
	this.gaCurrentIteration++;
    }

    // currentIterationPath
    public String getGaCurrentIterationPath() {
	return new String(gaIterationPrefix + getGaCurrentIteration() + "/");
    }

    public String getGaIterationPrefix() {
	return gaIterationPrefix;
    }

    public void setGaIterationPrefix(String gaIterationPrefix) {
	this.gaIterationPrefix = gaIterationPrefix;
    }

    // to kill the matsim threads (in case)
    public Thread getCloseChildThread() {
	return closeChildThread;
    }

    public void addChildProcess(Process p) {
	this.childProcess.add(p);
    }

    public void removeChildProcess(Process p) {
	this.childProcess.remove(p);
    }

    // main configuration
    public Configuration getConfig() {
	return config;
    }

    public void setConfig(Configuration mainConfig) {
	this.config = mainConfig;
    }

    // properties
    public String getProperty(String input) {
	return properties.getProperty(input);
    }

}
