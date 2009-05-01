package org.springside.modules.log.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.log4j.spi.LoggingEvent;

/**
 * 管理BlockingQueue Map与消费它们的任务.
 * 
 * @author ehuaxio
 */
public class QueueManager {

	@SuppressWarnings("unchecked")
	protected static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<String, BlockingQueue>();

	protected List<QueueConsumerTask> taskList;

	protected List<ExecutorService> executorList = new ArrayList<ExecutorService>();

	public void setTasks(List<QueueConsumerTask> taskList) {
		this.taskList = taskList;
	}

	@PostConstruct
	void init() {
		initQueues();
		runTasks();
	}

	@PreDestroy
	void close() {
		stopTasks();
	}

	@SuppressWarnings("unchecked")
	public static BlockingQueue getQueue(String queueName) {
		return queueMap.get(queueName);
	}

	protected void initQueues() {
		for (QueueConsumerTask task : taskList) {
			String queueName = task.getQueueName();
			BlockingQueue<LoggingEvent> queue = new LinkedBlockingQueue<LoggingEvent>();

			task.setQueue(queue);
			queueMap.put(queueName, queue);
		}
	}

	protected void runTasks() {
		for (QueueConsumerTask task : taskList) {
			ExecutorService executor = Executors.newCachedThreadPool();
			executorList.add(executor);

			for (int i = 0; i < task.getThreadCount(); i++) {
				executor.execute(task);
			}
		}
	}

	protected void stopTasks() {
		for (QueueConsumerTask task : taskList) {
			task.stopTask();
		}
	}
}