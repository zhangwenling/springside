package org.springside.examples.showcase.schedule;

import java.io.Serializable;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.modules.utils.SpringContextUtils;

/**
 * 被Quartz JobDetailBean定时执行的Job类,支持持久化到数据库实现Quartz集群.
 */
public class QuartzClusterableJob extends QuartzJobBean implements Serializable {
	private static final long serialVersionUID = 628727256400306220L;

	private static Logger logger = LoggerFactory.getLogger(QuartzClusterableJob.class);

	private String greet;

	/**
	 * JobDetailBean每次创建本类的实例时,将从jobDataMap中读出数据注入本类的属性中.
	 */
	public void setGreet(String greet) {
		this.greet = greet;
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		UserManager userManager = SpringContextUtils.getBean("userManager");
		long userCount = userManager.getUserCount();
		logger.info("{}, now is {},  there are {} user in table.", new Object[] { greet, new Date(), userCount });
	}
}
