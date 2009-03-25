package org.springside.examples.showcase.schedule;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被定时执行的任务类.
 */
public class ExampleJob {
	Logger logger = LoggerFactory.getLogger(ExampleJob.class);

	/**
	 * 被按固定时间间隔执行的业务方法.
	 */
	public void executeTimerLog() {
		logger.info("Hello, now is " + new Date() + ".This is print by quartz timer job.");
	}

	/**
	 * 被按Cron表达式定时执行的业务方法.
	 */
	public void executeCronLog() {
		logger.info("Hello, now is " + new Date() + ".This is print by quartz cron job.");
	}
}
