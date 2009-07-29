/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 消费Queue中消息的任务基类.
 * 
 * 定义了任务的初始化流程及循环运行框架,支持单条阻塞读取消息与定期批量读取消息两种策略.
 * 
 * @see QueueManager
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public abstract class QueueConsumerTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(QueueConsumerTask.class);

	protected String queueName;
	protected boolean blockingFetch = true;
	protected int batchSize = 10;
	protected int period = 1000;

	protected BlockingQueue queue;

	/**
	 * 任务所消费的队列名称.
	 */
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	/**
	 * 循环读取消息的策略,为true时采用单条阻塞读取策略,false时采用定期批量读取策略.
	 * @param blockingFetch
	 */
	public void setBlockingFetch(boolean blockingFetch) {
		this.blockingFetch = blockingFetch;
	}

	/**
	 * 批量定时读取的队列大小, 默认为10.
	 */
	public void setBatchSize(int batchSize) {
		this.batchSize = batchSize;
	}

	/**
	 * 批量定时读取的时间间隔,单位为毫秒,默认为1秒.
	 */
	public void setPeriod(int period) {
		this.period = period;
	}

	/**
	 * 任务初始化函数.
	 */
	@PostConstruct
	public void start() {
		queue = QueueManager.getQueue(queueName);
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.execute(this);
		QueueManager.registerTaskExecutor(executor);
	}

	/**
	 * 线程执行函数.
	 */
	public void run() {
		if (blockingFetch) {
			blockingFetch();
		} else {
			periodFetch();
		}
	}

	/**
	 * 阻塞获取事件并调用processMessage()进行处理.
	 * 
	 * @see #processMessage(Object)
	 */
	protected void blockingFetch() {
		try {
			//循环阻塞获取消息
			while (!Thread.currentThread().isInterrupted()) {
				Object message = queue.take();
				processMessage(message);
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断");
		}
		blockingFetchClean();
	}

	/**
	 * 定期批量获取事件并调用processMessageList()处理.
	 * 
	 * @see #processMessageList(List)
	 */
	protected void periodFetch() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				List list = new ArrayList(batchSize);
				queue.drainTo(list, batchSize);
				processMessageList(list);
				Thread.sleep(period);
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断");
		}
		periodFetchClean();
	}

	/**
	 * 单个消息处理函数,在子类实现.
	 */
	protected abstract void processMessage(Object message);

	/**
	 * 批量消息处理函数,在子类实现.
	 */
	protected abstract void processMessageList(List messageList);

	/**
	 * 退出循环时的清理函数,在子类实现.
	 */
	protected abstract void blockingFetchClean();

	/**
	 * 退出循环时的清理函数,在子类实现.
	 */
	protected abstract void periodFetchClean();
}
