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

/**
 * 采用定时批量读取Queue中消息策略的Consumer.
 */
public abstract class PeriodConsumerTask extends QueueConsumerTask {

	protected int batchSize = 10;
	protected int period = 1000;

	/**
	* 批量定时读取消息的队列大小, 默认为10.
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
	 * 线程执行函数,定期批量获取消息并调用processMessageList()处理.
	 */
	@SuppressWarnings("unchecked")
	public void run() {
		try {
			while (!Thread.currentThread().isInterrupted()) {
				List list = new ArrayList(batchSize);
				queue.drainTo(list, batchSize);
				processMessageList(list);
				if (!Thread.currentThread().isInterrupted()) {
					Thread.sleep(period);
				}
			}
		} catch (InterruptedException e) {
			logger.debug("消费线程阻塞被中断");
		}

		//在线程被中断后,退出线程前调用清理函数.
		clean();
	}

	/**
	 * 批量消息处理函数.
	 */
	protected abstract void processMessageList(List<?> messageList);

	/**
	 * 退出清理函数.
	 */
	protected abstract void clean();

}
