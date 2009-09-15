package org.springside.examples.showcase.integration.schedule;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.schedule.ExecutorJob;
import org.springside.modules.log.InMemoryAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/schedule/applicationContext-executor.xml" })
public class ExecutorJobTest extends SpringContextTestCase {
	private static InMemoryAppender appender = new InMemoryAppender();

	@BeforeClass
	public static void setupBeforeClass() {
		appender.addToLogger(ExecutorJob.class);
	}

	@Test
	public void run() {
		sleep(4000);
		assertEquals(1, appender.getLogs().size());
		assertEquals("There are 6 user in database.", appender.getLogs().get(0).getMessage());
	}
}
