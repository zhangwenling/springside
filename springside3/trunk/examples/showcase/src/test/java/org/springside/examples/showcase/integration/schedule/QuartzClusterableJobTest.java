package org.springside.examples.showcase.integration.schedule;

import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.QuartzClusterableJob;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * Quartz可集群Timer Job测试.
 * 
 * 注意因为任务会被持久化,因此本用例仅在清空数据库QRTZ_*表后的第一次运行或距上一次运行5分钟方可通过.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/schedule/applicationContext-quartz-timer-cluster.xml" })
public class QuartzClusterableJobTest extends SpringContextTestCase {

	@Test
	public void test() {
		//加载测试用logger appender
		MockAppender appender = new MockAppender();
		appender.addToLogger(QuartzClusterableJob.class);

		//等待任务启动
		sleep(3000);

		//验证任务已执行
		assertEquals(1, appender.getLogs().size());
		assertEquals("There are 6 user in database, print by default's job.", appender.getLogs().get(0).getMessage());
	}
}
