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
import org.springside.modules.utils.ThreadUtils.CustomizableThreadFactory;

/**
 * 被ScheduledThreadPoolExecutor定时执行的任务类.
 */
public class JdkExecutorJob implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(JdkExecutorJob.class);

	private int period = 0;

	private long shutdownTimeout = Integer.MAX_VALUE;

	private ScheduledExecutorService scheduledExecutorService;

	private AccountManager accountManager;

	@PostConstruct
	public void start() throws Exception {
		Assert.isTrue(period > 0);

		//任何异常不会中断执行schedule执行
		Runnable task = new DelegatingErrorHandlingRunnable(this, TaskUtils.LOG_AND_SUPPRESS_ERROR_HANDLER);

		scheduledExecutorService = Executors.newSingleThreadScheduledExecutor(new CustomizableThreadFactory(
				"JdkExecutorJob"));

		//scheduleAtFixedRatefixRate() 固定任务两次启动之间的时间间隔.
		//scheduleAtFixedDelay()      固定任务结束后到下一次启动间的时间间隔.
		scheduledExecutorService.scheduleAtFixedRate(task, 0, period, TimeUnit.SECONDS);
	}

	@PreDestroy
	public void stop() {
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

	/**
	 * 设置任务间隔时间,单位秒.
	 */
	public void setPeriod(int period) {
		this.period = period;
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
