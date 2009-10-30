package org.springside.modules.unit.log;

import junit.framework.Assert;

import org.junit.Test;
import org.springside.modules.log.JulToSlf4jHandler;
import org.springside.modules.log.MockAppender;

public class JulToSlf4jHandlerTest extends Assert {

	@Test
	public void test() {
		JulToSlf4jHandler init = new JulToSlf4jHandler();
		init.init();

		java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("TestLogger");
		MockAppender appender = new MockAppender();
		appender.addToLogger("TestLogger");

		julLogger.warning("test");
		assertEquals("test", appender.getFirstLog().getMessage());
	}
}
