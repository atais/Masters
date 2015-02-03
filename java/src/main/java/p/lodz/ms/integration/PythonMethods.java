package p.lodz.ms.integration;

import java.io.File;

import org.apache.commons.math3.genetics.Chromosome;

import p.lodz.ms.Configuration;
import p.lodz.ms.genetics.LinksChromosome;

public class PythonMethods extends PythonAdapter {

    private static final String analyseGraph = "analyse_and_save";
    private static final String fromXmltoBinary = "fromXMLtoBinary";
    private static final String fromBinaryToXml = "fromBinarytoXML";
    private static final String facilitiesGraph = "draw_facilities_graph";
    private static final String networkGraph = "draw_network_graph";
    private static final String eventsGraph = "draw_events_graph";
    private static final String organiseOutput = "organise_output";
    private static final String organiseBest = "organise_best";
    private static final String removeOutputEvents = "remove_output_events";
    private static final String customizeConfig = "customize_config";
    private static final String checkChromosome = "array_strongly_connected";
    private static final String randomChromosome = "create_randomized_sc_graph";

    @Deprecated
    public void analyseNetwork(File source, File dest) {
	String[] parameters = new String[] { analyseGraph,
		source.getAbsolutePath(), dest.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public LinksChromosome convertNetworkToBinary(File xml) {
	String[] parameters = new String[] { fromXmltoBinary,
		xml.getAbsolutePath() };
	String bin = this.defaultCall(parameters);
	return LinksChromosome.parseString(bin);
    }

    public LinksChromosome createRandomChromosome() {
	String defaultNetwork = Configuration.getInstance()
		.getScenarioNetwork();
	String[] parameters = new String[] { randomChromosome, defaultNetwork };
	String answer = this.defaultCall(parameters);
	return LinksChromosome.parseString(answer);
    }

    public boolean checkChromosome(Chromosome chromosome) {
	String defaultNetwork = Configuration.getInstance()
		.getScenarioNetwork();
	String[] parameters = new String[] { checkChromosome,
		chromosome.toString(), defaultNetwork };
	String answer = this.defaultCall(parameters);
	return Boolean.parseBoolean(answer);
    }

    public void convertBinaryToNetwork(LinksChromosome chromosome, File dest) {
	String defaultNetwork = Configuration.getInstance()
		.getScenarioNetwork();
	String[] parameters = new String[] { fromBinaryToXml, defaultNetwork,
		chromosome.toString(), dest.getAbsolutePath() };
	defaultCall(parameters);
    }

    public void facilitiesGraph(File network, File facilities, File output) {
	String[] parameters = new String[] { facilitiesGraph,
		network.getAbsolutePath(), facilities.getAbsolutePath(),
		output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void networkGraph(File network, File output) {
	String[] parameters = new String[] { networkGraph,
		network.getAbsolutePath(), output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void eventsGraph(File network, File events, File output) {
	String[] parameters = new String[] { eventsGraph,
		network.getAbsolutePath(), events.getAbsolutePath(),
		output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void organiseOutput(File output) {
	String[] parameters = new String[] { organiseOutput,
		output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void customizeConfig(File config, File facilities, File network,
	    File population, File output, Integer iterations) {
	String[] parameters = new String[] { customizeConfig,
		config.getAbsolutePath(), facilities.getAbsolutePath(),
		network.getAbsolutePath(), population.getAbsolutePath(),
		output.getAbsolutePath(), iterations.toString() };
	this.defaultCall(parameters);
    }

    public Boolean organiseBest(File dir) {
	String[] parameters = new String[] { organiseBest,
		dir.getAbsolutePath() };
	String s = this.defaultCall(parameters);
	return Boolean.valueOf(s);
    }

    public void removeOutputEvents(File dir) {
	String[] parameters = new String[] { removeOutputEvents,
		dir.getAbsolutePath() };
	this.defaultCall(parameters);
    }

}
