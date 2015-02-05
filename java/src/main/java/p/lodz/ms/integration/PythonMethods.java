package p.lodz.ms.integration;

import java.io.File;

import org.apache.commons.math3.genetics.Chromosome;

import p.lodz.ms.Context;
import p.lodz.ms.genetics.LinksChromosome;

public class PythonMethods extends PythonAdapter {

    private Context c = Context.getI();

    @Deprecated
    public void analyseNetwork(File source, File dest) {
	String command = c.getProp("python.analyse");

	String[] parameters = new String[] { command, source.getAbsolutePath(),
		dest.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void binToXml(LinksChromosome chromosome, File dest) {
	String command = c.getProp("python.bin.to.xml");
	String defaultNetwork = c.getConfig().getScenarioNetwork();

	String[] parameters = new String[] { command, chromosome.toString(),
		defaultNetwork, dest.getAbsolutePath() };
	defaultCall(parameters);
    }

    public boolean checkChromosome(Chromosome chromosome) {
	String command = c.getProp("python.chromosome.check");
	String defaultNetwork = c.getConfig().getScenarioNetwork();

	String[] parameters = new String[] { command, chromosome.toString(),
		defaultNetwork };
	String answer = this.defaultCall(parameters);
	return Boolean.parseBoolean(answer);
    }

    public LinksChromosome createRandomChromosome() {
	String command = c.getProp("python.chromosome.random");
	String defaultNetwork = c.getConfig().getScenarioNetwork();

	String[] parameters = new String[] { command, defaultNetwork };
	String answer = this.defaultCall(parameters);
	return LinksChromosome.parseString(answer);
    }

    public void customizeConfig(File config, File facilities, File network,
	    File population, File output, Integer iterations) {
	String command = c.getProp("python.organise.config");

	String[] parameters = new String[] { command, config.getAbsolutePath(),
		facilities.getAbsolutePath(), network.getAbsolutePath(),
		population.getAbsolutePath(), output.getAbsolutePath(),
		iterations.toString() };
	this.defaultCall(parameters);
    }

    public void eventsGraph(File network, File events, File output) {
	String command = c.getProp("python.draw.events");

	String[] parameters = new String[] { command,
		network.getAbsolutePath(), events.getAbsolutePath(),
		output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void facilitiesGraph(File network, File facilities, File output) {
	String command = c.getProp("python.draw.facilities");

	String[] parameters = new String[] { command,
		network.getAbsolutePath(), facilities.getAbsolutePath(),
		output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void networkGraph(File network, File output) {
	String command = c.getProp("python.draw.network");

	String[] parameters = new String[] { command,
		network.getAbsolutePath(), output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public Boolean organiseBest(File dir) {
	String command = c.getProp("python.organise.best");

	String[] parameters = new String[] { command, dir.getAbsolutePath() };
	String s = this.defaultCall(parameters);
	return Boolean.valueOf(s);
    }

    public void organiseOutput(File output) {
	String command = c.getProp("python.organise.output");

	String[] parameters = new String[] { command, output.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public void removeOutputEvents(File dir) {
	String command = c.getProp("python.organise.events");

	String[] parameters = new String[] { command, dir.getAbsolutePath() };
	this.defaultCall(parameters);
    }

    public LinksChromosome xmlToBin(File xml) {
	String command = c.getProp("python.xml.to.bin");
	String[] parameters = new String[] { command, xml.getAbsolutePath() };

	String bin = this.defaultCall(parameters);
	return LinksChromosome.parseString(bin);
    }

}
