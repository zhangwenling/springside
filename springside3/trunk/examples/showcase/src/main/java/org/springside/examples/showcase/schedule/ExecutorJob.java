package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类.
 */
public class ExecutorJob implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ExecutorJob.class);

	@Autowired
	private UserManager userManager;

	/**
	 * 定时打印当前用户数到日志.
	 */
	public void run() {
		long userCount = userManager.getUserCount();
		logger.info("Hello, now is {}, there is {} user in database.", new Date(), userCount);
	}
}
