package p.lodz.ms;

import java.io.IOException;

public class App {

    private static GeneticsModule geneticsModule;

    public static void main(String[] args) throws IOException {
	new ConfigurationModule(args[0]);
	Runtime.getRuntime().addShutdownHook(
		StaticContainer.getInstance().getCloseChildThread());

	geneticsModule = new GeneticsModule();
	geneticsModule.runGeneticAlgorithm();
    }

}
