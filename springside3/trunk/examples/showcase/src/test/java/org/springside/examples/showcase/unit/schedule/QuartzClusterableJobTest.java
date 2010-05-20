package org.springside.examples.showcase.unit.schedule;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.QuartzClusterableJob;
import org.springside.modules.test.mock.MockLog4jAppender;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;
import org.springside.modules.test.utils.TimeUtils;

/**
 * Quartz可集群Timer Job测试.
 * 
 * @author calvin
 */
@DirtiesContext
@ContextConfiguration(locations = { "/applicationContext-test.xml",
		"/schedule/applicationContext-quartz-timer-cluster.xml" })
public class QuartzClusterableJobTest extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSourceHolder, "/data/default-data.xml");
	}

	@Test
	public void test() {
		//加载测试用logger appender
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(QuartzClusterableJob.class);

		//等待任务启动
		TimeUtils.sleep(3000);

		//验证任务已执行
		assertEquals(1, appender.getAllLogs().size());

		assertEquals("There are 6 user in database, print by default's job.", appender.getFirstLog().getMessage());
	}
}
