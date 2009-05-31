package org.springside.modules.integration.log;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.junit4.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/log/applicationContext-log.xml" })
public class AsyncLoggerTest extends SpringContextTestCase {

	@Test
	public void dBLogger() throws InterruptedException {
		Logger dbLogger = LoggerFactory.getLogger("org.springside.examples.showcase.log.dbLogExample");
		int logCount = 10;
		for (int i = 1; i <= logCount; i++) {
			dbLogger.info("helloworld {}", i);
		}
	}
}
