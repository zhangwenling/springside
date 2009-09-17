package org.springside.examples.showcase.integration.schedule;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.ExecutorJob;
import org.springside.modules.log.ListAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/schedule/applicationContext-executor.xml" })
public class ExecutorJobTest extends SpringContextTestCase {
	@Test
	public void test() {
		//加载测试用logger appender
		ListAppender appender = new ListAppender();
		appender.addToLogger(ExecutorJob.class);

		//等待任务启动
		sleep(4000);

		//验证任务已执行
		assertEquals(1, appender.getLogs().size());
		assertEquals("There are 6 user in database.", appender.getLogs().get(0).getMessage());
	}
}
