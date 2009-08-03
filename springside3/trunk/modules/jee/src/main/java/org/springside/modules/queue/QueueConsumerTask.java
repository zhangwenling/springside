/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费Queue中消息的任务基类.
 * 
 * 定义了任务的初始化流程及停止流程.
 * 
 * @see QueueManager
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public abstract class QueueConsumerTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(QueueConsumerTask.class);

	protected String queueName;
	protected BlockingQueue queue;

	protected ExecutorService executor;
	protected int shutdownWait = 10000;

	/**
	 * 任务所消费的队列名称.
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 停止任务时最多等待的时间, 单位为毫秒, 默认为10秒.
	 */
	public void setShutdownWait(int shutdownWait) {
		this.shutdownWait = shutdownWait;
	}

	/**
	 * 任务初始化函数.
	 */
	@PostConstruct
	public void start() {
		queue = QueueManager.getQueue(queueName);
		executor = Executors.newSingleThreadExecutor();
		executor.execute(this);
		QueueManager.registerTask(this);
	}

	/**
	 * 任务结束函数.
	 */
	public void stop() {
		try {
			executor.shutdownNow();
			executor.awaitTermination(shutdownWait, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.debug("awaitTermination被中断", e);
		}
	}
}
