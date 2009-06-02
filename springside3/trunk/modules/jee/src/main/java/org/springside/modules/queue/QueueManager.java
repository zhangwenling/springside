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
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 管理BlockingQueue Map与消费它们的任务.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class QueueManager implements ApplicationContextAware {

	protected static Logger logger = LoggerFactory.getLogger(QueueManager.class);

	//可配置属性//
	protected List<String> taskNameList; //任务名称列表
	protected int stopWait = 10; //停止每个队列时最多等待的时间.
	protected boolean persistence = true; //是否将队列中为处理的消息持久化到文件.

	//内部属性//
	protected ApplicationContext ac;
	protected static Map<String, BlockingQueue> queueMap = new ConcurrentHashMap<String, BlockingQueue>();//消息队列
	protected List<ExecutorService> executorList = new ArrayList<ExecutorService>();//执行任务的线程池列表

	/**
	 * 根据queueName获得消息队列的静态函数.
	 * 如消息队列还不存在, 会自动进行创建.
	 */
	public static BlockingQueue getQueue(String queueName) {
		BlockingQueue queue = queueMap.get(queueName);

		if (queue == null) {
			queue = new LinkedBlockingQueue();
			queueMap.put(queueName, queue);
		}

		return queue;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ac = applicationContext;
	}

	@Required
	public void setTaskNameList(List<String> taskNameList) {
		this.taskNameList = taskNameList;
	}

	public void setStopWait(int stopWait) {
		this.stopWait = stopWait;
	}

	public void setPersistence(boolean persistence) {
		this.persistence = persistence;
	}

	@PostConstruct
	public void start() {
		//运行任务
		for (String taskName : taskNameList) {
			QueueConsumerTask task = (QueueConsumerTask) ac.getBean(taskName);
			String queueName = task.getQueueName();
			int threadCount = task.getThreadCount();

			try {
				//初始化任务消费的队列
				BlockingQueue queue = getQueue(queueName);

				//多线程运行任务
				ExecutorService executor = Executors.newCachedThreadPool();
				for (int i = 0; i < threadCount; i++) {
					QueueConsumerTask runTask = (i == 0) ? task : (QueueConsumerTask) ac.getBean(taskName);
					runTask.setQueue(queue);
					executor.execute(runTask);
					logger.debug("启动任务" + queueName);
				}
				executorList.add(executor);
			} catch (Exception e) {
				logger.error("启动任务" + queueName + "时出错", e);
			}

			if (persistence) {
				//从文件中恢复内容到队列.
				for (Entry<String, BlockingQueue> entry : queueMap.entrySet()) {
					try {
						restore(entry.getKey());
					} catch (Exception e) {
						logger.error("载入队列" + queueName + "时出错", e);
					}
				}
			}
		}
	}

	@PreDestroy
	public void stop() {
		//停止所有线程, 每个任务最多等待stopWait秒
		for (ExecutorService executor : executorList) {
			try {
				executor.shutdownNow();
				executor.awaitTermination(stopWait, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				logger.debug("awaitTermination被中断", e);
			}
		}

		//持久化所有未完成队列内容到文件
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