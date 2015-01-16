package p.lodz.playground;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.junit.Test;

public class PythonCallTest {

    @Test
    public void simpleCall() throws ExecuteException, IOException, InterruptedException {
	CommandLine cmdLine = new CommandLine("/usr/local/bin/python");
	cmdLine.addArgument("../python/onetime/tests.py");
	cmdLine.addArgument("2");
	cmdLine.addArgument("2");
	
//	HashMap map = new HashMap();
//	map.put("file", new File("invoice.pdf"));
//	cmdLine.setSubstitutionMap(map);

	DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

	ExecuteWatchdog watchdog = new ExecuteWatchdog(60 * 1000);
	Executor executor = new DefaultExecutor();
	executor.setExitValue(1);
	executor.setWatchdog(watchdog);
	executor.execute(cmdLine, resultHandler);

	// some time later the result handler callback was invoked so we
	// can safely request the exit value
	resultHandler.waitFor();
    }
    
//    @Test
    public void mostSimpleCall() throws ExecuteException, IOException{
	CommandLine cmdLine = new CommandLine("/usr/local/bin/python");
	cmdLine.addArgument("../python/onetime/tests.py");
	cmdLine.addArgument("2");
	cmdLine.addArgument("2");
	
	DefaultExecutor executor = new DefaultExecutor();
	executor.setExitValue(0);
	ExecuteWatchdog watchdog = new ExecuteWatchdog(60000);
	executor.setWatchdog(watchdog);
	
	executor.execute(cmdLine);
    }

}
