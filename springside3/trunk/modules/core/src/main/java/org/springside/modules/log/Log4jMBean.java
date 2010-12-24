/**
 * Copyright (c) 2005-2011 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 基于JMX动态配置Logger日志等级, 获取Logger Appender的MBean.
 * 
 * @author calvin
 */
@ManagedResource(objectName = Log4jMBean.LOG4J_MBEAN_NAME, description = "Log4j Management Bean")
public class Log4jMBean {

	/**
	 * Log4jMbean的注册名称.
	 */
	public static final String LOG4J_MBEAN_NAME = "Log4j:name=log4j";

	private static org.slf4j.Logger mbeanLogger = LoggerFactory.getLogger(Log4jMBean.class);

	/**
	 * 项目的默认Logger名称, 如"org.springside.examples.miniweb".
	 */
	private String defaultLoggerName;

	/**
	 * 获取Logger的日志级别.
	 */
	@ManagedOperation(description = "Get logging level of the logger")
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "loggerName", description = "Logger name") })
	public String getLoggerLevel(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getEffectiveLevel().toString();
	}

	/**
	 * 设置Logger的日志级别.
	 * 如果日志级别名称错误, 设为DEBUG.
	 */
	@ManagedOperation(description = "Set new logging level to the logger")
	@ManagedOperationParameters({ @ManagedOperationParameter(name = "loggerName", description = "Logger name"),
			@ManagedOperationParameter(name = "newlevel", description = "New level") })
	public void setLoggerLevel(String loggerName, String newLevel) {
		Logger logger = Logger.getLogger(loggerName);
		Level level = Level.toLevel(newLevel);
		logger.setLevel(level);
		mbeanLogger.info("设置{}级别为{}", loggerName, newLevel);
	}

	@ManagedAttribute(description = "Project default logging level of the logger")
	public String getDefaultLoggerLevel() {
		return getLoggerLevel(defaultLoggerName);
	}

	@ManagedAttribute(description = "Project default logging level of the logger")
	public void setDefaultLoggerLevel(String newLevel) {
		setLoggerLevel(defaultLoggerName, newLevel);
	}

	public void setDefaultLoggerName(String defaultLoggerName) {
		this.defaultLoggerName = defaultLoggerName;
	}
}