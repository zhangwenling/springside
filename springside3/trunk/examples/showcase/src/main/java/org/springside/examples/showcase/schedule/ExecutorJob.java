package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.dao.UserDao;
import org.springside.examples.showcase.common.entity.User;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类.
 */
public class ExecutorJob implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(ExecutorJob.class);

	@Autowired
	private UserDao userDao;

	public void run() {
		long userCount = userDao.findLong(User.COUNT_USER);
		logger.info("Hello, now is {}, there is {} user in table, print by ScheduledExecutor Job.", new Date(),
				userCount);
	}
}
