package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类.
 */
public class ExecutorJob implements Runnable {
	
	private static Logger logger = LoggerFactory.getLogger(ExecutorJob.class);
	
	private UserManager manager;

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

	public void run() {
		long userCount = manager.getUsersCount();
		logger.info("Hello, now is {}, there is {} user in table, print by ScheduledExecutor Job.",new Date(),userCount);
	}
}
