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
    private static final String customizeConfig = "customize_config";
    private static final String checkChromosome = "array_strongly_connected";
    private static final String randomChromosome = "create_randomized_sc_graph";

    @Deprecated
    public void analyseNetwork(File source, File dest) {
	String[] parameters = new String[] { source.getAbsolutePath(),
		dest.getAbsolutePath() };
	this.defaultCall(analyseGraph, parameters);
    }

    public LinksChromosome convertNetworkToBinary(File xml) {
	String[] parameters = new String[] { xml.getAbsolutePath() };
	String bin = this.defaultCall(fromXmltoBinary, parameters);
	return LinksChromosome.parseString(bin);
    }

    public LinksChromosome createRandomChromosome() {
	String defaultNetwork = Configuration.getInstance()
		.getScenarioNetwork();
	String[] parameters = new String[] { defaultNetwork };
	String answer = this.defaultCall(randomChromosome, parameters);
	return LinksChromosome.parseString(answer);
    }

    public boolean checkChromosome(Chromosome chromosome) {
	String defaultNetwork = Configuration.getInstance()
		.getScenarioNetwork();
	String[] parameters = new String[] { chromosome.toString(),
		defaultNetwork };
	String answer = this.defaultCall(checkChromosome, parameters);
	return Boolean.parseBoolean(answer);
    }

    public void convertBinaryToNetwork(LinksChromosome chromosome, File dest) {
	String defaultNetwork = Configuration.getInstance()
		.getScenarioNetwork();
	String[] parameters = new String[] { defaultNetwork,
		chromosome.toString(), dest.getAbsolutePath() };
	defaultCall(fromBinaryToXml, parameters);
    }

    public void facilitiesGraph(File network, File facilities, File output) {
	String[] parameters = new String[] { network.getAbsolutePath(),
		facilities.getAbsolutePath(), output.getAbsolutePath() };
	this.defaultCall(facilitiesGraph, parameters);
    }

    public void networkGraph(File network, File output) {
	String[] parameters = new String[] { network.getAbsolutePath(),
		output.getAbsolutePath() };
	this.defaultCall(networkGraph, parameters);
    }

    public void eventsGraph(File network, File events, File output) {
	String[] parameters = new String[] { network.getAbsolutePath(),
		events.getAbsolutePath(), output.getAbsolutePath() };
	this.defaultCall(eventsGraph, parameters);
    }

    public void organiseOutput(File output) {
	String[] parameters = new String[] { output.getAbsolutePath() };
	this.defaultCall(organiseOutput, parameters);
    }

    public void customizeConfig(File config, File facilities, File network,
	    File population, File output, Integer iterations) {
	String[] parameters = new String[] { config.getAbsolutePath(),
		facilities.getAbsolutePath(), network.getAbsolutePath(),
		population.getAbsolutePath(), output.getAbsolutePath(),
		iterations.toString() };
	this.defaultCall(customizeConfig, parameters);
    }

    public void organiseBest(File dir) {
	String[] parameters = new String[] { dir.getAbsolutePath() };
	this.defaultCall(organiseBest, parameters);
    }

}
