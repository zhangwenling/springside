package org.springside.examples.showcase.integration.schedule;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.ExecutorJob;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/schedule/applicationContext-executor.xml" })
public class ExecutorJobTest extends SpringContextTestCase {
	@Test
	public void test() {
		//加载测试用logger appender
		MockAppender appender = new MockAppender();
		appender.addToLogger(ExecutorJob.class);

		//等待任务启动
		sleep(3000);

		//验证任务已执行
		assertEquals(1, appender.getAllLogs().size());
		assertEquals("There are 6 user in database.", appender.getFirstLog().getMessage());
	}
}
