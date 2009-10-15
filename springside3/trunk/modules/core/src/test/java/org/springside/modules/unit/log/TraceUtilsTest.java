package org.springside.modules.unit.log;

import junit.framework.Assert;

import org.apache.log4j.MDC;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.log.MockAppender;
import org.springside.modules.log.TraceUtils;

public class TraceUtilsTest extends Assert {

	Logger logger = LoggerFactory.getLogger(TraceUtilsTest.class);

	@Test
	public void test() {
		MockAppender appender = new MockAppender();
		appender.addToLogger(TraceUtilsTest.class);

		//begin trace
		TraceUtils.beginTrace();
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));

		//log message
		logger.trace("message");
		assertEquals("message", appender.getAllLogs().get(0).getMessage());
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));

		//end trace
		TraceUtils.endTrace();
		assertNull(MDC.get(TraceUtils.TRACE_ID_KEY));
	}
}
