package org.springside.examples.showcase.schedule;

import java.util.Date;
import java.util.Map;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springside.examples.showcase.common.service.UserManager;

/**
 * 被Quartz JobDetailBean定时执行的Job类,支持持久化到数据库实现Quartz集群.
 */
public class QuartzClusterableJob extends QuartzJobBean {

	private static Logger logger = LoggerFactory.getLogger(QuartzClusterableJob.class);

	private ApplicationContext applicationContext;

	/**
	 * 从SchedulerFactoryBean注入到applicationContext.
	 */
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		UserManager userManager = (UserManager) applicationContext.getBean("userManager");
		Map<?, ?> config = (Map<?, ?>) applicationContext.getBean("timerJobConfig");

		long userCount = userManager.getUserCount();
		logger.info("Hi, now is {}, here is {}, there are {} user in table.", new Object[] { new Date(),
				config.get("nodeName"), userCount });
	}
}
