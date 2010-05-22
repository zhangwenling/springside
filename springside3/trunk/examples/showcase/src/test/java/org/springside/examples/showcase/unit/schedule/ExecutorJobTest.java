package org.springside.examples.showcase.unit.schedule;

import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.ExecutorJob;
import org.springside.modules.test.mock.MockLog4jAppender;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;
import org.springside.modules.test.utils.TimeUtils;

@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-test.xml", "/schedule/applicationContext-executor.xml" })
public class ExecutorJobTest extends SpringTxTestCase {

	@Test
	public void scheduleJob() throws Exception {
		DbUnitUtils.loadData(dataSource, "/data/default-data.xml");

		//加载测试用logger appender
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(ExecutorJob.class);

		//等待任务启动
		TimeUtils.sleep(3000);

		//验证任务已执行
		assertEquals(1, appender.getAllLogs().size());
		assertEquals("There are 6 user in database.", appender.getFirstLog().getMessage());

		DbUnitUtils.removeData(dataSource, "/data/default-data.xml");

	}
}
