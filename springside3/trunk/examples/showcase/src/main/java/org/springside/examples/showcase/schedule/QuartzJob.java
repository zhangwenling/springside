package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被Quartz定时执行的任务类.
 */
public class QuartzJob {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	private UserManager manager;

	public void setManager(UserManager manager) {
		this.manager = manager;
	}

	protected void executeCronLog() {
		long userCount = manager.getUsersCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz cron job", new Date(), userCount);
	}

	protected void executeTimerLog() {
		long userCount = manager.getUsersCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz timer job", new Date(), userCount);
	}
}
