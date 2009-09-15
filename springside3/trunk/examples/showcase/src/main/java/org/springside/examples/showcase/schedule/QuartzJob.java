package org.springside.examples.showcase.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被Quartz MethodInvokingJobDetailFactoryBean定时执行的普通Spring Bean.
 */
public class QuartzJob {

	private static Logger logger = LoggerFactory.getLogger(QuartzJob.class);

	@Autowired
	private UserManager userManager;

	/**
	 * 定时打印当前用户数到日志.
	 */
	public void execute() {
		long userCount = userManager.getUserCount();
		logger.info("There are {} user in database.", userCount);
	}
}
