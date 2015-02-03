package p.lodz.ms;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaticContainer {

    private static StaticContainer instance;

    public static final String facilitiesGraphName = "facilities.png";
    public static final String outputEventsFileName = "output_events.xml.gz";
    public static final String eventsFolderName = "events";
    public static final String networkGraphName = "network.png";
    public static final String networkFileName = "network.xml";
    public static final String configFileName = "config.xml";
    public static final String chromosomeFileName = "chromosome.txt";
    public static final String fitnessFilename = "fitness.txt";
    public static final String fitnessInitialFilename = "fitnessInitial.txt";

    // starting value does not matter
    private String gaIterationPrefix = "ga.";
    // Always starts at 0 iteration
    private Integer gaCurrentIteration = 0;

    private Thread closeChildThread;
    private List<Process> childProcess = Collections
	    .synchronizedList(new ArrayList<Process>());

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
	closeChildThread = new Thread() {
	    public void run() {
		for (Process p : childProcess) {
		    p.destroy();
		}
	    }
	};
    }

    public Integer getGaCurrentIteration() {
	return gaCurrentIteration;
    }

    public void setGaCurrentIteration(Integer gaCurrentIteration) {
	this.gaCurrentIteration = gaCurrentIteration;
    }

    public String getGaCurrentIterationPath() {
	return new String(gaIterationPrefix + getGaCurrentIteration() + "/");
    }

    public String getGaIterationPrefix() {
	return gaIterationPrefix;
    }

    public void setGaIterationPrefix(String gaIterationPrefix) {
	this.gaIterationPrefix = gaIterationPrefix;
    }

    public void increaseCurrentGeneration() {
	setGaCurrentIteration(getGaCurrentIteration() + 1);
    }

    public Thread getCloseChildThread() {
	return closeChildThread;
    }

    public void addChildProcess(Process p) {
	this.childProcess.add(p);
    }

    public void removeChildProcess(Process p) {
	this.childProcess.remove(p);
    }

}
