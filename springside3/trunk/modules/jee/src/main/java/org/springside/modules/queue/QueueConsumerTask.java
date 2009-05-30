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

	protected volatile boolean stopping = false; //停止标志

	// 流程控制函数 //

	public void run() {
		try {
			while (!stopping) {
				Object event = queue.take();
				processEvent(event);
			}
		} catch (InterruptedException e) {
			logger.error("interrupted happen", e);
		}
	}

	public void stop() {
		stopping = true;
	}

	// 子类重载函数 //

	/**
	 * 处理event的方法.
	 * 注意除非确定要终止处理线程,否则不能抛出RuntimeException.
	 */
	protected abstract void processEvent(Object eventObject);

	//属性访问函数//	

	@Required
	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}
}
