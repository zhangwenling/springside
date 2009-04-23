package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springside.examples.showcase.common.dao.UserDao;

/**
 * 被Quartz定时执行的任务类.
 */
@Transactional(readOnly = true)
public class QuartzJob {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Autowired
	private UserDao userDao;

	protected void executeCronLog() {
		long userCount = userDao.getUserCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz cron job", new Date(), userCount);
	}

	protected void executeTimerLog() {
		long userCount = userDao.getUserCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz timer job", new Date(), userCount);
	}
}
