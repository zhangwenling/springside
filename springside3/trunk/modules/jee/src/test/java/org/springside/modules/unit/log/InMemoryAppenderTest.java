package org.springside.modules.unit.log;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.log.InMemoryAppender;

public class InMemoryAppenderTest extends Assert {

	@Test
	public void test() {
		InMemoryAppender appender = new InMemoryAppender();
		appender.addToLogger("org.springside");

	}
}
