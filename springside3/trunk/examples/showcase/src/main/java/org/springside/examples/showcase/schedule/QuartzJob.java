package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被Quartz定时执行的任务类.
 */
public class QuartzJob {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Autowired
	private UserManager userManager;

	/**
	 * 被Quartz以cron trigger调用的方法,定时打印当前用户数到日志.
	 */
	public void cronExecuteLog() {
		long userCount = userManager.getUserCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz cron job", new Date(), userCount);
	}

	/**
	 * 被Quartz以simmpler trigger调用的方法,定时打印当前用户数到日志.
	 */
	public void timerExecuteLog() {
		long userCount = userManager.getUserCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz timer job", new Date(), userCount);
	}
}
