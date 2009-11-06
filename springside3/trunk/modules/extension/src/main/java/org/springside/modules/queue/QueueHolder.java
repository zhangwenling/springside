/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.queue;

import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedOperationParameters;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 管理BlockingQueue Map.
 * 
 * 当Queue初始化时,负责restore持久化文件中的消息.
 * 当系统关闭时,负责停止Task, 并将未完成的消息持久化到文件.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
@ManagedResource(objectName = QueueHolder.QUEUEHOLDER_MBEAN_NAME, description = "Queue Holder Bean")
public class QueueHolder {
	/**
	 * QueueManager注册的名称.
	 */
	public static final String QUEUEHOLDER_MBEAN_NAME = "SpringSide:type=QueueManagement,name=queueHolder";

	private static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<String, BlockingQueue>();//消息队列
	private static int queueSize = Integer.MAX_VALUE;

	/**
	 * 每个队列的长度大小,默认为Integer最大值.
	 */
	public void setQueueSize(int queueSize) {
		QueueHolder.queueSize = queueSize;
	}

	/**
	 * 根据queueName获得消息队列的静态函数.
	 * 如消息队列还不存在, 会自动进行创建.
	 */
	public static <T> BlockingQueue<T> getQueue(String queueName) {
		BlockingQueue queue = queueMap.get(queueName);

		if (queue == null) {
			queue = new LinkedBlockingQueue(queueSize);
			queueMap.put(queueName, queue);
		}

		return queue;
	}

	/**
	 * 根据queueName获得消息队列中未处理消息的数量,支持基于JMX查询.
	 */
	@ManagedOperation(description = "Get message count in queue")
	@ManagedOperationParameters( { @ManagedOperationParameter(name = "queueName", description = "Queue name") })
	public static int getQueueLength(String queueName) {
		return getQueue(queueName).size();
	}

}