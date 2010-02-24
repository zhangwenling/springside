package org.springside.modules.unit.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.test.mock.MockAppender;

public class MockAppenderTest extends Assert {

	@Test
	public void test() {
		String testString1 = "Hello";
		String testString2 = "World";
		MockAppender appender = new MockAppender();
		appender.addToLogger("org.springside");

		Logger logger = LoggerFactory.getLogger(MockAppenderTest.class);
		logger.warn(testString1);
		logger.warn(testString2);
		assertEquals(testString1, appender.getFirstLog().getMessage());

		assertEquals(testString2, appender.getLastLog().getMessage());
		assertEquals(2, appender.getAllLogs().size());
		assertEquals(testString2, appender.getAllLogs().get(1).getMessage());

		appender.clearLogs();
		assertNull(appender.getFirstLog());
	}

}
