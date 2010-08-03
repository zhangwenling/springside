package org.springside.examples.showcase.schedule;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;
import org.springside.examples.showcase.common.service.AccountManager;

/**
 * 使用Spring的ThreadPoolTaskScheduler执行Cron式任务的类.
 */
public class SpringCronJob implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(SpringCronJob.class);

	private String cronExpression;

	private long shutdownTimeout = Integer.MAX_VALUE;

	private ThreadPoolTaskScheduler threadPoolTaskScheduler;

	private AccountManager accountManager;

	@PostConstruct
	public void start() {
		Assert.hasText(cronExpression);

		threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
		threadPoolTaskScheduler.setThreadNamePrefix("SpringCronJob");
		threadPoolTaskScheduler.initialize();

		threadPoolTaskScheduler.schedule(this, new CronTrigger(cronExpression));
	}

	@PreDestroy
	public void stop() {
		ScheduledExecutorService scheduledExecutorService = threadPoolTaskScheduler.getScheduledExecutor();

		//shutdownNow(),取消所有等待执行的任务,等待执行中任务执行完毕, 并中断执行中任务的所有阻塞函数.
		//shutdown(), 等待所有已提交任务执行完毕.
		scheduledExecutorService.shutdownNow();

		if (shutdownTimeout > 0) {
			try {
				scheduledExecutorService.awaitTermination(shutdownTimeout, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				// Ignore.
			}
		}
	}

	/**
	 * 定时打印当前用户数到日志.
	 */
	public void run() {
		long userCount = accountManager.getUserCount();
		logger.info("There are {} user in database.", userCount);
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	/**
	 * 设置gracefulShutdown的等待时间,单位秒.
	 */
	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
