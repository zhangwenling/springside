package org.springside.modules.unit.log;

import junit.framework.Assert;

import org.junit.Test;
import org.springside.modules.log.JulToSlf4jInit;
import org.springside.modules.log.MockAppender;

public class JulToSlf4jInitTest extends Assert {

	@Test
	public void test() {
		JulToSlf4jInit init = new JulToSlf4jInit();
		init.init();

		java.util.logging.Logger julLogger = java.util.logging.Logger.getLogger("TestLogger");
		MockAppender appender = new MockAppender();
		appender.addToLogger("TestLogger");

		julLogger.warning("test");
		assertEquals("test", appender.getFirstLog().getMessage());
	}
}
