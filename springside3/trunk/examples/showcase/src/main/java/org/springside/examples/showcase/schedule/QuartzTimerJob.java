package org.springside.examples.showcase.schedule;

import java.io.Serializable;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springside.examples.showcase.common.service.UserManager;
import org.springside.modules.utils.SpringContextUtil;

/**
 * 被Quartz定时执行的Job类.
 * 
 * 支持持久化到数据库实现Quartz集群.
 */
public class QuartzTimerJob extends QuartzJobBean implements Serializable {

	private static final long serialVersionUID = 628727256400306220L;

	private static Logger logger = LoggerFactory.getLogger(QuartzTimerJob.class);

	private String serverName;

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	@Override
	protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
		UserManager userManager = SpringContextUtil.getBean("userManager");
		long userCount = userManager.getUserCount();
		logger.info("Hello, now is {}, there is {} user in table, print by Quartz timer job at {}", new Object[] {
				new Date(), userCount, serverName });
	}
}
