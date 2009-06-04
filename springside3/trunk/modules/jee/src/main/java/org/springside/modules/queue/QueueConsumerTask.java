package org.springside.modules.queue;

import java.util.concurrent.BlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * 消费Queue中事件的任务基类.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public abstract class QueueConsumerTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	protected BlockingQueue queue; //任务消费的队列

	protected String queueName; //可配置的任务对应队列名称
	protected int threadCount = 1;//可配置的任务并发线程数

	public String getQueueName() {
		return queueName;
	}

	/**
	 * 任务对应的队列名称.
	 */
	@Required
	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * 任务并发的线程数.
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}
}
