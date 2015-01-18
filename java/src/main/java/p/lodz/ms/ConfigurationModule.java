package p.lodz.ms;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class ConfigurationModule {
    private final static Logger logger = Logger
	    .getLogger(ConfigurationModule.class);

    private Configuration config;

    public ConfigurationModule(String configFile) throws Exception {
	try {
	    startConfiguration(configFile);
	} catch (ConfigurationException e) {
	    logger.error("Wrong configuration file as parameter");
	    throw e;
	} catch (ArrayIndexOutOfBoundsException e) {
	    logger.error("Start the app with configuration file as parameter");
	    throw e;
	} catch (DirectoryNotEmptyException e) {
	    logger.error("Project folder already exists, safe-break");
	    throw e;
	} catch (IOException e) {
	    logger.error("Problem with creating the folder structure");
	    throw e;
	}
    }

    private void startConfiguration(String configFile)
	    throws ConfigurationException, IOException {
	config = Configuration.getInstance().readXMLFile(configFile);
	logger.info("Properly loaded " + configFile + " config");
	logger.setLevel(Level.toLevel(config.getProjectLogLevel()));
	logger.info("Setting logger level to " + logger.getLevel().toString());

	logger.info("Creating output folder with project folder inside");
	FileUtils.forceMkdir(new File(config.getProjectOutputDir()));
	File project = new File(config.getProjectOutputDir()
		+ config.getProjectName());
	if (!project.mkdir()) {
	    throw (new DirectoryNotEmptyException(project.toString()));
	}
    }
}
