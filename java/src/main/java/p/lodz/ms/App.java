package p.lodz.ms;

import java.io.IOException;

public class App {

    private static ConfigurationModule configModule;
    private static GeneticsModule geneticsModule;

    public static void main(String[] args) throws IOException {
	configModule = new ConfigurationModule(args[0]);
	geneticsModule = new GeneticsModule();
	geneticsModule.runGeneticAlgorithm();
	System.exit(0);
    }

}
