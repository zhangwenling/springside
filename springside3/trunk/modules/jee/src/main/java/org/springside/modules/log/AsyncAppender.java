/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.log;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.queue.QueueManager;

/**
 * 轻量级的Log4j异步Appender.
 * 
 * 将所有消息放入QueueManager所管理的Blocking Queue中.
 * 
 * @see QueueManager
 * 
 * @author calvin
 */
public class AsyncAppender extends org.apache.log4j.AppenderSkeleton {
	private static Logger logger = LoggerFactory.getLogger(AsyncAppender.class);

	protected String queueName;

	@Override
	public void append(LoggingEvent event) {
		boolean sucess = getQueue().offer(event);
		if (sucess) {
			if (logger.isDebugEnabled()) {
				logger.debug("put event ,{}", Log4jUtils.convertEventToString(event));
			}
		} else {
			logger.error("Put event in queue fail ,{}", Log4jUtils.convertEventToString(event));
		}
	}

	public void close() {
	}

	public boolean requiresLayout() {
		return false;
	}

	@SuppressWarnings("unchecked")
	protected BlockingQueue<LoggingEvent> getQueue() {
		return QueueManager.getQueue(queueName);
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}
}
