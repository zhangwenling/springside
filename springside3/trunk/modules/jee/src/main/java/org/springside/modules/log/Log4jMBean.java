/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JdbcAppenderTask.java 353 2009-08-22 09:33:28Z calvinxiu
 */
package org.springside.modules.log;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * JMX动态配置日志等级的MBean.
 * 
 * @author calvin
 */
@ManagedResource(objectName = "Custom:type=Log4jManagement,name=log4jManagement", description = "Log4j Managed Bean")
public class Log4jMBean {

	@ManagedAttribute(description = "get the logging level of the root logger")
	public String getRootLogLevel() {
		Logger root = Logger.getRootLogger();
		return root.getLevel().toString();
	}

	@ManagedAttribute(description = "set the new logging level to the root logger")
	public void setRootLogLevel(String newLevel) {
		Logger root = Logger.getRootLogger();
		Level level = Level.toLevel(newLevel);
		root.setLevel(level);
	}

	@ManagedOperation(description = "get the logging level of the logger")
	public String getLogLevel(
			@ManagedOperationParameter(name = "loggerName", description = "logger name") String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getLevel().toString();
	}

	@ManagedOperation(description = "set the new logging level to the logger")
	public void setLogLevel(
			@ManagedOperationParameter(name = "loggerName", description = "logger name") String loggerName,
			@ManagedOperationParameter(name = "newlevel", description = "new level") String newLevel) {
		Logger logger = Logger.getLogger(loggerName);
		Level level = Level.toLevel(newLevel);
		logger.setLevel(level);
	}
}
