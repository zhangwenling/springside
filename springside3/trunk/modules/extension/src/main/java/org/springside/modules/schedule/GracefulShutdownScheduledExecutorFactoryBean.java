package org.springside.modules.schedule;

import java.util.concurrent.TimeUnit;

import org.springframework.scheduling.concurrent.ScheduledExecutorFactoryBean;

/**
 * 重载ScheduledExecutorFactoryBean,提供awaitTermination()等待
 * 
 * @author Calvin
 */
public class GracefulShutdownScheduledExecutorFactoryBean extends ScheduledExecutorFactoryBean {

	private long shutdownTimeout = 1000;

	/**
	 * 重载,在shutdown后等待ScheduledExecutor结束若干时间.
	 */
	@Override
	public void shutdown() {
		super.shutdown();
		try {
			this.getObject().awaitTermination(shutdownTimeout, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {

		}
	}

	/**
	 * 等待ScheduledExecutor结束的时间, 单位为毫秒, 默认为1000毫秒.
	 */
	public void setShutdownTimeout(long shutdownTimeout) {
		this.shutdownTimeout = shutdownTimeout;
	}
}
