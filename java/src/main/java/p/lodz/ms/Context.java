package p.lodz.ms;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

public class Context {

    private final static Logger logger = Logger.getLogger(Context.class);
    private static Context instance;

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

	properties = new Properties();
	try {
	    InputStream is = Context.class
		    .getResourceAsStream("/config.properties");
	    properties.load(is);
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    // Current iteration stuff
    public Integer getGaCurrentIteration() {
	return gaCurrentIteration;
    }

    public void setGaCurrentIteration(Integer gaCurrentIteration) {
	this.gaCurrentIteration = gaCurrentIteration;
    }

    public void increaseGaCurrentIteration() {
	this.gaCurrentIteration += 1;
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
    public String getProp(String input) {
	return properties.getProperty(input);
    }

}
