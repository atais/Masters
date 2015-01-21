package p.lodz.ms.integration;

import java.io.IOException;

import org.apache.commons.exec.CommandLine;
import org.apache.commons.exec.DefaultExecuteResultHandler;
import org.apache.commons.exec.DefaultExecutor;
import org.apache.commons.exec.ExecuteException;
import org.apache.commons.exec.ExecuteWatchdog;
import org.apache.commons.exec.Executor;
import org.apache.commons.exec.LogOutputStream;
import org.apache.commons.exec.PumpStreamHandler;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.log4j.Logger;

import p.lodz.ms.Configuration;

public class PythonAdapter {

    private final static Logger logger = Logger.getLogger(PythonAdapter.class);

    private String pyReturn = null;

    protected String defaultCall(String command, String... parameters) {
	pyReturn = null;
	CommandLine cmdLine = new CommandLine(Configuration.getInstance()
		.getProjectPythonPath());
	cmdLine.addArgument(Configuration.getInstance().getProjectPythonMain());
	cmdLine.addArgument(command);
	for (String param : parameters) {
	    cmdLine.addArgument(param);
	}

	DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();

	ExecuteWatchdog watchdog = new ExecuteWatchdog(
		ExecuteWatchdog.INFINITE_TIMEOUT);
	Executor executor = new DefaultExecutor();
	PumpStreamHandler psh = new PumpStreamHandler(new LogOutputStream() {
	    @Override
	    protected void processLine(String line, int logLevel) {
		if (line.contains("return:")) {
		    pyReturn = line;
		} else {
		    logger.info(line);
		}
	    }
	});
	executor.setStreamHandler(psh);
	executor.setWatchdog(watchdog);
	try {
	    executor.execute(cmdLine, resultHandler);
	    resultHandler.waitFor();
	} catch (ExecuteException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (IOException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	} catch (InterruptedException e) {
	    logger.error(ExceptionUtils.getStackTrace(e));
	}

	if (pyReturn != null && !pyReturn.isEmpty()) {
	    try {
		pyReturn = pyReturn.substring(7);
	    } catch (StringIndexOutOfBoundsException e) {
		logger.error(ExceptionUtils.getStackTrace(e));
	    }
	}
	return pyReturn;
    }

}
