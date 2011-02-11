package org.springside.modules.unit.log;

import static org.junit.Assert.*;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.springside.modules.log.Log4jMBean;

public class Log4jMBeanTest {

	@Test
	public void testLoggerLevel() {

		String loggerName = "org.springside.modules";
		Log4jMBean mbean = new Log4jMBean();
		String orgLevel = mbean.getLoggerLevel(loggerName);

		Logger.getLogger(loggerName).setLevel(Level.FATAL);
		assertEquals("FATAL", mbean.getLoggerLevel(loggerName));

		mbean.setLoggerLevel(loggerName, "TRACE");
		assertEquals("TRACE", mbean.getLoggerLevel(loggerName));

		mbean.setLoggerLevel(loggerName, "WRONG_LEVEL_NAME");
		assertEquals("DEBUG", mbean.getLoggerLevel(loggerName));

		mbean.setLoggerLevel(loggerName, orgLevel);
	}
}
