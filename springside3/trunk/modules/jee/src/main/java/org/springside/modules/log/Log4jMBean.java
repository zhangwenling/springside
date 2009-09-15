/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: JdbcAppenderTask.java 353 2009-08-22 09:33:28Z calvinxiu
 */
package org.springside.modules.log;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.apache.log4j.Appender;
import org.apache.log4j.Category;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * JMX动态配置日志等级的MBean.
 * 
 * @author calvin
 */
@ManagedResource(objectName = "Custom:type=Log4jManagement,name=log4jManagement", description = "Log4j Managed Bean")
public class Log4jMBean {

	private org.slf4j.Logger mbeanLogger = LoggerFactory.getLogger(Log4jMBean.class);

	@ManagedAttribute(description = "Logging level of the root logger")
	public String getRootLogLevel() {
		Logger root = Logger.getRootLogger();
		return root.getLevel().toString();
	}

	@ManagedAttribute(description = "Logging level of the root logger")
	public void setRootLogLevel(String newLevel) {
		Logger root = Logger.getRootLogger();
		Level level = Level.toLevel(newLevel);
		root.setLevel(level);
		mbeanLogger.info("设置rootLogger级别到{}", newLevel);
	}

	@ManagedOperation(description = "Get logging level of the logger")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "loggerName", description = "Logger name") })
	public String getLogLevel(String loggerName) {
		Logger logger = Logger.getLogger(loggerName);
		return logger.getLevel().toString();
	}

	@ManagedOperation(description = "set the new logging level to the logger")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "loggerName", description = "Logger name"),
			@ManagedOperationParameter(name = "newlevel", description = "New level") })
	public void setLogLevel(String loggerName, String newLevel) {
		Logger logger = Logger.getLogger(loggerName);
		Level level = Level.toLevel(newLevel);
		logger.setLevel(level);
		mbeanLogger.info("设置{}级别到{}", loggerName, newLevel);
	}

	@ManagedOperation(description = "Get all appenders of the logger")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "loggerName", description = "Logger name") })
	public List<String> getAppenders(String loggerName) {
		List<String> appenderNameList = new ArrayList<String>();
		Logger logger = Logger.getLogger(loggerName);

		//循环加载logger及其parent的appenders
		for (Category c = logger; c != null; c = c.getParent()) {
			Enumeration e = c.getAllAppenders();
			while (e.hasMoreElements()) {
				Appender appender = (Appender) e.nextElement();
				String appenderName = appender.getName();
				if (c != logger)
					appenderName += "(parent)";
				appenderNameList.add(appenderName);
			}

			//如果logger不继承parent的appender,则停止向上循环.
			if (!c.getAdditivity()) {
				break;
			}
		}

		return appenderNameList;
	}
}
