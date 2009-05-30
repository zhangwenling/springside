package org.springside.modules.unit.queue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.queue.QueueManager;

@SuppressWarnings("unchecked")
public class QueueManagerTest extends Assert {

	@Test
	public void backup() throws FileNotFoundException, IOException, ClassNotFoundException {
		String queueName = "test";
		BlockingQueue queue = QueueManager.getQueue(queueName);
		Date date1 = new Date();
		Date date2 = new Date();
		queue.offer(date1);
		queue.offer(date2);

		QueueManager manager = new QueueManager();
		manager.stopTasks();

		String filePath = System.getProperty("java.io.tmpdir") + File.separator + "queue" + File.separator + queueName;
		ObjectInputStream oos = new ObjectInputStream(new FileInputStream(filePath));
		List list = new ArrayList();
		while (true) {
			Object obj = oos.readObject();
			if (obj instanceof QueueManager.EndOfStream) {
				break;
			}
			list.add(obj);
		}
		oos.close();
		assertEquals(2, list.size());
		assertEquals(date1, list.get(0));
	}
}
