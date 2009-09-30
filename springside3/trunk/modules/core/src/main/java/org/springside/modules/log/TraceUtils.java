package org.springside.modules.log;

import java.util.UUID;

import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TraceUtils {

	public static final String LOGGER_NAME = "TraceLogger";
	public static final String TRACE_ID_KEY = "traceId";

	private static final String TRACE_NAME_KEY = "traceName";
	private static final String SUBTRACE_NAME_KEY = "subtraceName";
	private static final String INDENT = "  ";

	private static final Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

	public static Logger getLogger() {
		return logger;
	}

	public static void beginTrace(String traceName) {
		UUID traceId = UUID.randomUUID();
		MDC.put(TRACE_ID_KEY, traceId);
		MDC.put(TRACE_NAME_KEY, traceName);

		NDC.clear();
		logger.trace("-> {}", traceName);

		NDC.push(INDENT);
	}

	public static void endTrace() {
		NDC.pop();

		logger.trace("<- {}", MDC.get(TRACE_NAME_KEY));

		MDC.remove(TRACE_ID_KEY);
		MDC.remove(TRACE_NAME_KEY);
	}

	public static void beginSubTrace(String traceName) {
		String traceNameKey = SUBTRACE_NAME_KEY + NDC.getDepth();

		MDC.put(traceNameKey, traceName);

		logger.trace("-> {}", traceName);

		NDC.push(INDENT);
	}

	public static void endSubTrace() {
		NDC.pop();

		String traceNameKey = SUBTRACE_NAME_KEY + NDC.getDepth();

		logger.trace("<- {}", MDC.get(traceNameKey));

		MDC.remove(traceNameKey);
	}
}
