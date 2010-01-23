package org.springside.examples.showcase.unit.schedule;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.QuartzClusterableJob;
import org.springside.examples.showcase.unit.common.BaseTxTestCase;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.utils.TimeUtils;

/**
 * Quartz可集群Timer Job测试.
 * 
 * 
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/schedule/applicationContext-quartz-timer-cluster.xml" })
@Ignore("wait Spring3 memory Cache")
public class QuartzClusterableJobTest extends BaseTxTestCase {
	
	@Override
	protected void createSchema() throws IOException {
		super.createSchema();
		runSql("/sql/h2/quartz.sql",true);
	}

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
