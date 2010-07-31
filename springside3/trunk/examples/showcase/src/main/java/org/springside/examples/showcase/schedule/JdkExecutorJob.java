package org.springside.examples.showcase.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.DelegatingErrorHandlingRunnable;
import org.springframework.scheduling.support.TaskUtils;
import org.springframework.util.Assert;
import org.springside.examples.showcase.common.service.AccountManager;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类.
 */
public class JdkExecutorJob implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(JdkExecutorJob.class);

	private int initialDelay = 0;

	private int period = 0;

	private long shutdownTimeout = 0;

	private ScheduledExecutorService scheduledExecutorService;

	private AccountManager accountManager;

	@PostConstruct
	public void start() throws Exception {
		Assert.isTrue(period > 0);

		//任何异常不会中断执行schedule执行
		Runnable task = new DelegatingErrorHandlingRunnable(this, TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
		scheduledExecutorService.scheduleAtFixedRate(task, initialDelay, period, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void stop() {
		scheduledExecutorService.shutdownNow();
		if (shutdownTimeout > 0) {
			try {
				scheduledExecutorService.awaitTermination(shutdownTimeout, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
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

	public void setInitialDelay(int initialDelay) {
		this.initialDelay = initialDelay;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}

	@Autowired
	public void setAccountManager(AccountManager accountManager) {
		this.accountManager = accountManager;
	}
}
