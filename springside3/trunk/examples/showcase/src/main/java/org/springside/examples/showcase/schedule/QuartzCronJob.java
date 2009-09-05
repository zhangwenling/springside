package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被Quartz定时执行的普通Spring Bean.
 * 
 * 不支持持久化到数据库实现Quartz集群.
 */
public class QuartzCronJob {

	private static Logger logger = LoggerFactory.getLogger(QuartzCronJob.class);

	@Autowired
	private UserManager userManager;

	/**
	 * 定时打印当前用户数到日志.
	 */
	public void execute() {
		long userCount = userManager.getUserCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz cron job", new Date(), userCount);
	}
}
