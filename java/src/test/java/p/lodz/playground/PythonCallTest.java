package p.lodz.playground;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;

import p.lodz.ms.GeneticsModule;

public class PythonCallTest {

    private final static Logger logger = Logger.getLogger(GeneticsModule.class);
    String pyReturn = null;

    @Test
    public void simpleCall() throws ExecuteException, IOException,
	    InterruptedException {
	CommandLine cmdLine = new CommandLine("/usr/local/bin/python");
	cmdLine.addArgument("../python/java/test.py");
	cmdLine.addArgument("2");
	cmdLine.addArgument("2");

	// HashMap map = new HashMap();
	// map.put("file", new File("invoice.pdf"));
	// cmdLine.setSubstitutionMap(map);

	DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

	ExecuteWatchdog watchdog = new ExecuteWatchdog(
		ExecuteWatchdog.INFINITE_TIMEOUT);
	Executor executor = new DefaultExecutor();
	PumpStreamHandler psh = new PumpStreamHandler(new LogOutputStream() {
	    @Override
	    protected void processLine(String line, int logLevel) {
		if (line.contains("return")) {
		    pyReturn = line;
		} else {
		    logger.log(Level.toLevel(logLevel), line);
		}
	    }
	});
	executor.setStreamHandler(psh);
	executor.setWatchdog(watchdog);
	executor.execute(cmdLine, resultHandler);

	// some time later the result handler callback was invoked so we
	// can safely request the exit value
	resultHandler.waitFor();
	System.out.println(pyReturn);
    }

}
