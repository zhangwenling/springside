package org.springside.modules.unit.log;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.log.MockAppender;

public class MockAppenderTest extends Assert {

	@Test
	public void test() {
		MockAppender appender = new MockAppender();
		appender.addToLogger("org.springside");

	}
}
