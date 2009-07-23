/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.queue;

import java.util.concurrent.BlockingQueue;

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
	protected int threadCount = 1;

	/**
	 * 任务所消费的队列.
	 */
	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}

	/**
	 * 可配置的任的并发处理线程数.
	 */
	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
}
