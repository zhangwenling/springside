package org.springside.modules.queue;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 管理BlockingQueue Map与消费它们的任务.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class QueueManager {

	protected static Logger logger = LoggerFactory.getLogger(QueueManager.class);

	protected static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<String, BlockingQueue>();//消息队列

	protected List<QueueConsumerTask> taskList; //任务列表

	public void setTaskList(List<QueueConsumerTask> taskList) {
		this.taskList = taskList;
	}

	/**
	 * 根据queueName获得消息队列的静态函数.
	 * 如消息队列还不存在, 会自动进行创建.
	 */
	public static BlockingQueue getQueue(String queueName) {
		BlockingQueue queue = queueMap.get(queueName);

		if (queue == null) {
			try {
				queue = new LinkedBlockingQueue();
				queueMap.put(queueName, queue);
				restore(queueName);
			} catch (Exception e) {
				logger.error("载入队列" + queueName + "时出错", e);
			}
		}
		return queue;
	}

	@PostConstruct
	public void start() {
		for (QueueConsumerTask task : taskList) {
			String queueName = task.getQueueName();
			try {
				//初始化任务消费的队列
				BlockingQueue queue = getQueue(queueName);
				task.setQueue(queue);

				//多线程运行任务
				ExecutorService executor = Executors.newFixedThreadPool(task.getThreadCount());
				for (int i = 0; i < task.getThreadCount(); i++) {
					executor.execute(task);
				}
			} catch (Exception e) {
				logger.error("启动任务" + queueName + "时出错", e);
			}
		}
	}

	@PreDestroy
	public void stop() {

		//停止所有线程不再从队列读取内容.
		if (taskList != null) {
			for (QueueConsumerTask task : taskList) {
				task.stop();
			}
		}

		//持久化所有队列到文件.
		for (Entry<String, BlockingQueue> entry : queueMap.entrySet()) {
			try {
				backup(entry.getKey());
			} catch (IOException e) {
				logger.error("持久化" + entry.getKey() + "队列时出错", e);
			}
		}

		//清除queueMap
		queueMap.clear();
	}

	/**
	 * 持久化队列中未处理的对象到文件中.
	 */
	protected static void backup(String queueName) throws IOException {
		BlockingQueue queue = getQueue(queueName);
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
				logger.info("队列{}已持久化到{}", queueName, filePath);
			} finally {
				if (oos != null) {
					oos.close();
				}
			}
		}
	}

	/**
	 * 载入持久化文件中的对象到队列中.
	 */
	protected static void restore(String queueName) throws ClassNotFoundException, FileNotFoundException, IOException {
		ObjectInputStream ois = null;
		String filePath = getPersistenceFilePath(queueName);
		File file = new File(filePath);

		if (file.exists()) {
			try {
				ois = new ObjectInputStream(new FileInputStream(file));
				BlockingQueue queue = getQueue(queueName);
				while (true) {
					try {
						Object event = ois.readObject();
						queue.offer(event);
					} catch (EOFException e) {
						break;
					}
				}
				logger.info("队列{}已从{}中恢复.", queueName, filePath);
			} finally {
				if (ois != null) {
					ois.close();
				}
			}
			file.delete();
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