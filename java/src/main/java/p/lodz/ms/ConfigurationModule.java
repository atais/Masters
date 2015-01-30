package p.lodz.ms;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class ConfigurationModule {
    private final static Logger logger = Logger
	    .getLogger(ConfigurationModule.class);

    private Configuration config;

    public ConfigurationModule(String configFile) {
	try {
	    startConfiguration(configFile);
	} catch (ConfigurationException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (ArrayIndexOutOfBoundsException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (DirectoryNotEmptyException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}
    }

    private void startConfiguration(String configFile)
	    throws ConfigurationException, IOException {
	config = Configuration.getInstance().readXMLFile(configFile);
	logger.setLevel(Level.toLevel(config.getProjectLogLevel()));

	if (!config.getProjectLogFile().isEmpty()) {
	    FileAppender fa = new FileAppender();
	    fa.setName("FileLogger");
	    fa.setFile(config.getProjectDir() + "/"
		    + config.getProjectLogFile());
	    fa.setLayout(new PatternLayout("%d{ISO8601} %-5p (%t-%c{1}) %m%n"));
	    fa.setThreshold(Level.toLevel(config.getProjectLogLevel()));
	    fa.setAppend(true);
	    fa.activateOptions();
	    Logger.getRootLogger().addAppender(fa);
	}

	logger.info("Creating output folder with project folder inside");
	File folder = new File(config.getProjectDir());
	if (folder.listFiles().length > 1) {
	    logger.error("WATCH OUT THE PROJECT DIR NOT EMPTY!");
	    logger.error("WATCH OUT THE PROJECT DIR NOT EMPTY!");
	    logger.error("WATCH OUT THE PROJECT DIR NOT EMPTY!");
	}
	FileUtils.forceMkdir(new File(config.getProjectDir()));
    }
}
