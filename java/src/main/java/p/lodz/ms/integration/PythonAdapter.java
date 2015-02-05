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

import p.lodz.ms.Context;

public class PythonAdapter {

    private final static Logger logger = Logger.getLogger(PythonAdapter.class);

    private String pyReturn = null;

    protected String defaultCall(String... commands) {

	CommandLine cmdLine = new CommandLine(Context.getI().getConfig()
		.getProjectPythonPath());
	cmdLine.addArgument(Context.getI().getConfig().getProjectPythonMain());
	cmdLine.addArguments(commands);

	logger.debug(cmdLine.toString());

	DefaultExecuteResultHandler resultHandler = new DefaultExecuteResultHandler();
	Executor executor = new DefaultExecutor();
	executor.setStreamHandler(new PumpStreamHandler(new LogOutputStream() {
	    @Override
	    protected void processLine(String line, int logLevel) {
		if (line.contains("return:")) {
		    pyReturn = line;
		} else {
		    logger.warn(line);
		}
	    }
	}));
	executor.setWatchdog(new ExecuteWatchdog(
		ExecuteWatchdog.INFINITE_TIMEOUT));
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
