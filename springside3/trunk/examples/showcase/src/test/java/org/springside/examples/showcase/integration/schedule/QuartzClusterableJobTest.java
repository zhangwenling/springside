package org.springside.examples.showcase.integration.schedule;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.QuartzClusterableJob;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.TimeUtils;

/**
 * Quartz可集群Timer Job测试.
 * 
 * 
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/schedule/applicationContext-quartz-timer-cluster.xml" })
@Ignore("注意因为任务会被持久化到数据库, 因此本用例仅在清空数据库QRTZ_*表后的第一次运行或距上一次运行5分钟方可通过.")
public class QuartzClusterableJobTest extends SpringTxTestCase {

	@Test
	public void test() {
		//加载测试用logger appender
		MockAppender appender = new MockAppender();
		appender.addToLogger(QuartzClusterableJob.class);

		//等待任务启动
		TimeUtils.sleep(3000);

		//验证任务已执行
		assertEquals(1, appender.getAllLogs().size());
	
		assertEquals("There are 6 user in database, print by default's job.", appender.getFirstLog().getMessage());
	}
}
