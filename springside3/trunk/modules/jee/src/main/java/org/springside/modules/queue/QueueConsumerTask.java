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

	// 流程控制函数 //

	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				Object event = queue.take();
				processEvent(event);
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断", e);
		}
		clean();
	}

	// 子类重载函数 //

	/**
	 * 处理event的方法.
	 * 注意除非确定要终止处理线程,否则不能抛出RuntimeException.
	 */
	protected abstract void processEvent(Object eventObject) throws InterruptedException;

	/**
	 * 退出任务时的清理函数.
	 */
	protected abstract void clean();

	//属性访问函数//

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
