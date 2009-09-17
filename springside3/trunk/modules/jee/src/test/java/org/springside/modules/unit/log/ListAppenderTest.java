package org.springside.modules.unit.log;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.log.ListAppender;

public class ListAppenderTest extends Assert {

	@Test
	public void test() {
		ListAppender appender = new ListAppender();
		appender.addToLogger("org.springside");

	}
}
