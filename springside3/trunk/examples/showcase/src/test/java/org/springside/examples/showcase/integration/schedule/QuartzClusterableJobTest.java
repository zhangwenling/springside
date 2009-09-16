package org.springside.examples.showcase.integration.schedule;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.QuartzClusterableJob;
import org.springside.modules.log.InMemoryAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * Quartz可集群Timer Job测试.
 * 
 * 注意因为任务会被持久化,因此本用例仅在清空数据库QRTZ_*表后的第一次运行可通过.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/schedule/applicationContext-quartz-timer-cluster.xml" })
public class QuartzClusterableJobTest extends SpringContextTestCase {

	@Test
	public void test() {
		//等待任务启动
		sleep(4000);

		//加载测试用logger appender
		InMemoryAppender appender = new InMemoryAppender();
		appender.addToLogger(QuartzClusterableJob.class);

		//验证任务已执行
		assertEquals(1, appender.getLogs().size());
		assertEquals("There are 6 user in database, print by default's job.", appender.getLogs().get(0).getMessage());
	}
}
