/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.queue;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedOperationParameter;
import org.springframework.jmx.export.annotation.ManagedResource;

/**
 * 管理BlockingQueue Map与消费它们的任务.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
@ManagedResource(objectName = "Custom:type=QueueManagement,name=queueManagement", description = "Queue Managed Bean")
public class QueueManager {

	protected static Logger logger = LoggerFactory.getLogger(QueueManager.class);

	protected static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<String, BlockingQueue>();//消息队列
	protected static List<ExecutorService> executorList = new ArrayList();//执行任务的线程池列表

	protected static boolean persistence = true;
	protected int shutdownWait = 10000;

	/**
	 * 根据queueName获得消息队列的静态函数.
	 * 如消息队列还不存在, 会自动进行创建.
	 */
	public static BlockingQueue getQueue(String queueName) {
		BlockingQueue queue = queueMap.get(queueName);

		if (queue == null) {
			queue = new LinkedBlockingQueue();

			//从文件中恢复消息到队列.
			if (persistence) {
				try {
					restore(queueName);
				} catch (Exception e) {
					logger.error("载入队列" + queueName + "时出错", e);
				}
			}

			queueMap.put(queueName, queue);
		}

		return queue;
	}

	/**
	 * 根据queueName获得消息队列中未处理事件的大小.
	 */
	@ManagedOperation(description = "Get event count in queue")
	public static int getQueueSize(
			@ManagedOperationParameter(name = "queueName", description = "queue name") String queueName) {
		return getQueue(queueName).size();
	}

	public static List<ExecutorService> getExecutorList() {
		return executorList;
	}

	/**
	 * 停止每个队列时最多等待的时间, 单位为毫秒, 默认为10秒.
	 */
	public void setShutdownWait(int shutdownWait) {
		this.shutdownWait = shutdownWait;
	}

	/**
	 * 是否将队列中为处理的消息持久化到文件, 默认为true.
	 */
	public void setPersistence(boolean persistence) {
		QueueManager.persistence = persistence;
	}

	@PreDestroy
	public void stop() {
		//停止所有线程, 每个任务最多等待stopWait秒
		for (ExecutorService executor : executorList) {
			try {
				executor.shutdownNow();
				executor.awaitTermination(shutdownWait, TimeUnit.MILLISECONDS);
			} catch (InterruptedException e) {
				logger.debug("awaitTermination被中断", e);
			}
		}

		//持久化所有队列的未处理消息到文件
		if (persistence) {
			for (Entry<String, BlockingQueue> entry : queueMap.entrySet()) {
				try {
					backup(entry.getKey());
				} catch (IOException e) {
					logger.error("持久化" + entry.getKey() + "队列时出错", e);
				}
			}
		}

		//清除queueMap
		queueMap.clear();
	}

	/**
	 * 持久化队列中未处理的消息到文件中.
	 * 当持久化文件已存在时会进行追加.
	 * 当队列中消息过多时, 客户端代码亦可调用本函数进行持久化, 等高峰期过后重新执行restore().
	 */
	public static void backup(String queueName) throws IOException {
		BlockingQueue queue = queueMap.get(queueName);
		List list = new ArrayList();
		queue.drainTo(list);

		if (!list.isEmpty()) {
			ObjectOutputStream oos = null;
			try {
				String filePath = getPersistenceFilePath(queueName);
				oos = new ObjectOutputStream(new FileOutputStream(filePath));
				for (Object event : list) {
					oos.writeObject(event);
				}
				logger.info("队列{}已持久化{}个事件到{}", new Object[] { queueName, list.size(), filePath });
			} finally {
				if (oos != null) {
					oos.close();
				}
			}
		} else {
			logger.debug("队列为空,不需要持久化 .");
		}
	}

	/**
	 * 载入持久化文件中的消息到队列中.
	 */
	public static void restore(String queueName) throws ClassNotFoundException, IOException {
		ObjectInputStream ois = null;
		String filePath = getPersistenceFilePath(queueName);
		File file = new File(filePath);

		if (file.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				BlockingQueue queue = queueMap.get(queueName);
				int i = 0;
				while (true) {
					try {
						Object event = ois.readObject();
						queue.offer(event);
						i++;
					} catch (EOFException e) {
						break;
					}
				}
				logger.info("队列{}已从{}中恢复{}个事件.", new Object[] { queueName, filePath, i });
			} finally {
				if (ois != null) {
					ois.close();
				}
			}
			file.delete();
		} else {
			logger.debug("队列{}不存在", queueName);
		}
	}

	/**
	 * 获取持久化文件路径.
	 * 持久化文件默认路径为java.io.tmpdir/queue/队列名.
	 * 如果java.io.tmpdir/queue/目录不存在,会进行创建.
	 */
	protected static String getPersistenceFilePath(String queueName) {
		File parentDir = new File(System.getProperty("java.io.tmpdir") + File.separator + "queue" + File.separator);
		if (!parentDir.exists()) {
			parentDir.mkdirs();
		}
		return parentDir.getAbsolutePath() + File.separator + queueName;
	}
}