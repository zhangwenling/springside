package org.springside.modules.unit.log;

import junit.framework.Assert;

import org.apache.log4j.MDC;
import org.apache.log4j.NDC;
import org.junit.Test;
import org.slf4j.Logger;
import org.springside.modules.log.MockAppender;
import org.springside.modules.log.TraceUtils;

public class TraceUtilsTest extends Assert {

	@Test
	public void test() {
		MockAppender appender = new MockAppender();
		appender.addToLogger(TraceUtils.LOGGER_NAME);
		Logger logger = TraceUtils.getLogger();

		//begin trace
		TraceUtils.beginTrace("trace");
		assertEquals("-> trace", appender.getAllLogs().get(0).getMessage());
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		assertEquals(TraceUtils.INDENT, NDC.get());

		//begin subtrace
		TraceUtils.beginSubTrace("subtrace");
		assertEquals("-> subtrace", appender.getAllLogs().get(1).getMessage());
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		assertEquals(TraceUtils.INDENT + " " + TraceUtils.INDENT, NDC.get());

		//log message
		logger.trace("message");
		assertEquals("message", appender.getAllLogs().get(2).getMessage());
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		assertEquals(TraceUtils.INDENT + " " + TraceUtils.INDENT, NDC.get());

		//end subtrace
		TraceUtils.endSubTrace();
		assertEquals("<- subtrace", appender.getAllLogs().get(3).getMessage());
		assertNotNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		assertEquals(TraceUtils.INDENT, NDC.get());

		//end trace
		TraceUtils.endTrace();
		assertEquals("<- trace", appender.getAllLogs().get(4).getMessage());
		assertNull(MDC.get(TraceUtils.TRACE_ID_KEY));
		assertEquals(null, NDC.get());
	}
}
