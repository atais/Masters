package p.lodz.ms;

import java.io.IOException;

public class App {

    private static GeneticsModule geneticsModule;

    public static void main(String[] args) throws IOException {
	new ConfigurationModule(args[0]);
	Runtime.getRuntime().addShutdownHook(
		Context.getI().getCloseChildThread());

	geneticsModule = new GeneticsModule();
	geneticsModule.runGeneticAlgorithm();
    }

}
