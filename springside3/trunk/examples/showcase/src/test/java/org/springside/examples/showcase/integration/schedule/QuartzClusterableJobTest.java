package org.springside.examples.showcase.integration.schedule;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.QuartzClusterableJob;
import org.springside.modules.log.InMemoryAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@Ignore("sleep() didn't work")
@ContextConfiguration(locations = { "/schedule/applicationContext-quartz-timer-cluster.xml" })
public class QuartzClusterableJobTest extends SpringContextTestCase {

	private static InMemoryAppender appender = new InMemoryAppender();

	@BeforeClass
	public static void setupBeforeClass() {
		appender.addToLogger(QuartzClusterableJob.class);
	}

	@Test
	public void execute() {
		sleep(5000);
		assertEquals(1, appender.getLogs().size());
		assertEquals("There are 6 user in database, print by default's job.", appender.getLogs().get(0).getMessage());
	}
}
