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

	public static final String TRACE_ID_KEY = "traceId";

	/**
	 * 开始Trace.
	 * 生成本次Trace的唯一ID并放入MDC.
	 */
	public static void beginTrace() {
		UUID traceId = UUID.randomUUID();
		MDC.put(TRACE_ID_KEY, traceId);
	}

	/**
	 * 开始Trace.
	 * 将TraceID放入MDC.
	 */
	public static void beginTrace(String traceId) {
		MDC.put(TRACE_ID_KEY, traceId);
	}

	/**
	 * 结束一次Trace.
	 * 清除traceId.
	 */
	public static void endTrace() {
		MDC.remove(TRACE_ID_KEY);
	}
}
