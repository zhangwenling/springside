package org.springside.modules.log.queue;

import java.util.concurrent.BlockingQueue;

import org.apache.log4j.spi.LoggingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;

/**
 * 消费Queue中事件的任务基类.
 * 
 * @author calvin
 */
public abstract class QueueConsumerTask implements Runnable {

	protected Logger logger = LoggerFactory.getLogger(getClass());
	protected BlockingQueue<LoggingEvent> queue;

	protected String queueName; //任务对应的queue名称
	protected int threadCount = 1;//任务的并发线程数

	protected volatile boolean stopping = false;

	public void run() {
		try {
			while (!stopping) {
				Object event = queue.take();
				logger.debug("get event blocking, {}", convertEventToString(event));
				processEvent(event);
			}
		} catch (InterruptedException e) {
			logger.error("interrupted happen", e);
		}
	}

	/**
	 * 可被主进程调用的停止方法,用非blocking的形式处理完Queue中所有Event后退出.
	 */
	public void stopTask() {

		logger.debug("stopping task");

		stopping = true;

		LoggingEvent event;
		while ((event = queue.poll()) != null) {
			logger.debug("get event non-blocking,{}", convertEventToString(event));
			processEvent(event);
		}

		logger.debug("stopped task");
	}

	/**
	 * 子类必须实现的处理event的方法.注意除非确定要终止处理线程,否则不能抛出RuntimeException.
	 */
	protected abstract void processEvent(Object eventObject);

	/**
	 * 打印debug信息时使用的方法,可进行重载.
	 */
	protected String convertEventToString(Object eventObject) {
		return eventObject.toString();
	}

	@SuppressWarnings("unchecked")
	public void setQueue(BlockingQueue queue) {
		this.queue = queue;
	}

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
}
