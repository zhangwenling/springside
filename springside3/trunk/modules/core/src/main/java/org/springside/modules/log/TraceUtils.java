/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: 
 */
package org.springside.modules.log;

import java.util.UUID;

import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 系统运行时打印方便调试与追踪信息的工具类.
 * 
 * 使用MDC存储traceID, 一次trace中所有日志都自动带有该ID,
 * 可以方便的用grep命令在日志文件中提取该trace的所有日志.
 * 
 * 如果一次Trace较长或层次较多, 还可以使用的SubTrace分类和缩进.
 * 
 * 需要在log4j.properties中进行如下配置:
 * 
 * log4j.logger.TraceLogger=TRACE,trace
 * log4j.additivity.TraceLogger=false
 * 
 * log4j.appender.trace=org.apache.log4j.ConsoleAppender
 * log4j.appender.trace.layout=org.apache.log4j.PatternLayout
 * log4j.appender.trace.layout.ConversionPattern=%d [%t] %X{traceId} -%x %m%n
 * 
 * @author calvin
 */
public class TraceUtils {

	public static final String LOGGER_NAME = "TraceLogger";
	public static final String TRACE_ID_KEY = "traceId";
	public static final String INDENT = "  ";

	private static final String TRACE_NAME_KEY = "traceName";
	private static final String SUBTRACE_NAME_KEY = "subtraceName";

	private static Logger logger = LoggerFactory.getLogger(LOGGER_NAME);

	public static Logger getLogger() {
		return logger;
	}

	/**
	 * 开始Trace.
	 * 生成本次Trace的唯一ID并放入MDC, 打印trace开始信息并增加缩进.
	 */
	public static void beginTrace(String traceName) {
		UUID traceId = UUID.randomUUID();
		MDC.put(TRACE_ID_KEY, traceId);
		MDC.put(TRACE_NAME_KEY, traceName);

		NDC.clear();
		logger.trace("-> {}", traceName);

		NDC.push(INDENT);
	}

	/**
	 * 结束一次Trace.
	 * 打印trace结束信息, 减少缩进, 清除traceId.
	 */
	public static void endTrace() {
		NDC.pop();

		logger.trace("<- {}", MDC.get(TRACE_NAME_KEY));

		MDC.remove(TRACE_ID_KEY);
		MDC.remove(TRACE_NAME_KEY);
	}

	/**
	 * 开始子Trace.
	 * 打印子Trace开始信息, 增加缩进. 
	 */
	public static void beginSubTrace(String traceName) {
		String traceNameKey = SUBTRACE_NAME_KEY + NDC.getDepth();

		MDC.put(traceNameKey, traceName);

		logger.trace("-> {}", traceName);

		NDC.push(INDENT);
	}

	/**
	 * 结束子Trace.
	 * 打印子Trace结束信息, 减少缩进.
	 */
	public static void endSubTrace() {
		NDC.pop();

		String traceNameKey = SUBTRACE_NAME_KEY + NDC.getDepth();

		logger.trace("<- {}", MDC.get(traceNameKey));

		MDC.remove(traceNameKey);
	}
}
