package p.lodz.ms;

import java.io.File;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class Configuration {

    private final String DEFAULTPYTHONMAIN = "../python/ms/call_center.py";
    private final String DEFAULTPYTHONPATH = "/usr/local/bin/python";
    private final String DEFAULTJAVAPATH = "/usr/bin/java";
    private final String DEFAULTPNAME = "default";
    private final String DEFAULTOUTPUT = "../output/";
    private final Integer DEFAULTTHREADS = Runtime.getRuntime()
	    .availableProcessors();
    private final String DEFAULTLOG = "INFO";
    private final String DEFAULTLOGFILE = "logfile.log";
    private final String DEFAULTMATSIMXMX = "2g";

    private final Integer DEFAULTSCENARIOITER = 50;

    private final Integer DEFAULTGAPOPULATION = 35;
    private final Integer DEFAULTGAMAXGEN = 150;
    private final Double DEFAULTGAELITISM = 2.0;
    private final Double DEFAULTGACROSSOVER = 1.0;
    private final Double DEFAULTGAMUTATION = 0.8;
    private final Integer DEFAULTGAARITY = 2;
    private final Integer DEFAULTGAMAXMUTATION = 10;
    private final Integer DEFAULTGAMAXCROSSOVER = 10;

    private String projectName;
    private String projectOutputDir;
    private Integer projectThreads;
    private String projectLogLevel;
    private String projectLogFile;
    private String projectPythonPath;
    private String projectJavaPath;
    private String projectPythonMain;
    private String projectMatsimJar;
    private String projectMatsimXmx;

    private String scenarioConfig;
    private String scenarioNetwork;
    private String scenarioPopulation;
    private String scenarioFacilities;
    private Integer scenarioIterations;

    private Integer gaPopulationSize;
    private Integer gaMaxGenerations;
    private Double gaElitismRate;
    private Double gaCrossoverRate;
    private Double gaMutationRate;
    private Integer gaTournamentArity;
    private Integer gaMaxMutationAttempts;
    private Integer gaMaxCrossoverAttempts;

    public Configuration(File configFile) throws ConfigurationException {
	this(new XMLConfiguration(configFile));
    }

    public Configuration(String configFile) throws ConfigurationException {
	this(new XMLConfiguration(configFile));
    }

    public Configuration(XMLConfiguration configFile)
	    throws ConfigurationException {
	readConfig(configFile);
	Context.getI().setConfig(this);
    }

    private void readConfig(XMLConfiguration config) {
	this.projectName = config.getString("project.name", DEFAULTPNAME);
	this.projectOutputDir = config.getString("project.output-dir",
		DEFAULTOUTPUT);
	this.projectThreads = config.getInteger("project.threads",
		DEFAULTTHREADS);
	this.projectLogLevel = config
		.getString("project.log-level", DEFAULTLOG);
	this.projectLogFile = config.getString("project.log-filename",
		DEFAULTLOGFILE);
	this.projectPythonPath = config.getString("project.python-path",
		DEFAULTPYTHONPATH);
	this.projectPythonMain = config.getString("project.python-main",
		DEFAULTPYTHONMAIN);
	this.projectMatsimJar = config.getString("project.matsim-jar");
	this.projectJavaPath = config.getString("project.java-path",
		DEFAULTJAVAPATH);
	this.projectMatsimXmx = config.getString("project.matsim-xmx",
		DEFAULTMATSIMXMX);

	this.scenarioConfig = config.getString("scenario.config");
	this.scenarioNetwork = config.getString("scenario.network");
	this.scenarioPopulation = config.getString("scenario.population");
	this.scenarioFacilities = config.getString("scenario.facilities");
	this.scenarioIterations = config.getInteger("scenario.iterations",
		DEFAULTSCENARIOITER);

	this.gaPopulationSize = config.getInteger("genetics.population-size",
		DEFAULTGAPOPULATION);
	this.gaMaxGenerations = config.getInteger("genetics.max-generations",
		DEFAULTGAMAXGEN);
	this.gaElitismRate = config.getDouble("genetics.elitism-rate",
		DEFAULTGAELITISM);
	this.gaCrossoverRate = config.getDouble("genetics.crossover-rate",
		DEFAULTGACROSSOVER);
	this.gaMutationRate = config.getDouble("genetics.mutation-rate",
		DEFAULTGAMUTATION);
	this.gaTournamentArity = config.getInteger("genetics.tournament-arity",
		DEFAULTGAARITY);
	this.gaMaxMutationAttempts = config.getInteger(
		"genetics.max-mutation-attempts", DEFAULTGAMAXMUTATION);
	this.gaMaxCrossoverAttempts = config.getInteger(
		"genetics.max-crossover-attempts", DEFAULTGAMAXCROSSOVER);
    }

    public String getProjectName() {
	return projectName;
    }

    public String getProjectOutputDir() {
	return projectOutputDir;
    }

    public Integer getProjectThreads() {
	return projectThreads;
    }

    public String getProjectLogLevel() {
	return projectLogLevel;
    }

    public String getProjectLogFile() {
	return projectLogFile;
    }

    public String getScenarioConfig() {
	return scenarioConfig;
    }

    public String getScenarioNetwork() {
	return scenarioNetwork;
    }

    public String getScenarioPopulation() {
	return scenarioPopulation;
    }

    public String getScenarioFacilities() {
	return scenarioFacilities;
    }

    public Integer getScenarioIterations() {
	return scenarioIterations;
    }

    public Integer getGaPopulationSize() {
	return gaPopulationSize;
    }

    public Integer getGaMaxGenerations() {
	return gaMaxGenerations;
    }

    public Double getGaElitismRate() {
	return gaElitismRate;
    }

    public Double getGaCrossoverRate() {
	return gaCrossoverRate;
    }

    public Double getGaMutationRate() {
	return gaMutationRate;
    }

    public Integer getGaTournamentArity() {
	return gaTournamentArity;
    }

    public String getProjectPythonPath() {
	return projectPythonPath;
    }

    public String getProjectPythonMain() {
	return projectPythonMain;
    }

    public String getProjectDir() {
	return getProjectOutputDir() + "/" + getProjectName() + "/";
    }

    public Integer getGaMaxMutationAttempts() {
	return gaMaxMutationAttempts;
    }

    public Integer getGaMaxCrossoverAttempts() {
	return gaMaxCrossoverAttempts;
    }

    public String getProjectMatsimJar() {
	return projectMatsimJar;
    }

    public String getProjectMatsimXmx() {
	return projectMatsimXmx;
    }

    public String getProjectMatsimXmxArg() {
	return "-Xmx" + getProjectMatsimXmx();
    }

    public String getProjectJavaPath() {
	return projectJavaPath;
    }

}
