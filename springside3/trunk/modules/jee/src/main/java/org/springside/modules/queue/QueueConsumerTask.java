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

import javax.annotation.PostConstruct;

/**
 * 消费Queue中消息的任务基类.
 * 
 * @see QueueManager
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public abstract class QueueConsumerTask implements Runnable {

	protected BlockingQueue queue;

	protected String queueName;

	/**
	 * 任务所消费的队列.
	 */
	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}

	/**
	 * 任务所消费的队列名称.
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	@PostConstruct
	public void start() {
		queue = QueueManager.getQueue(queueName);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(this);
		QueueManager.getExecutorList().add(executor);

	}
}
