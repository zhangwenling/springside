package org.springside.modules.integration.log;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/log/applicationContext-log.xml" })
public class AsyncLoggerTest extends SpringContextTestCase {

	@Test
	public void dbLogger() {
		Logger dbLogger = LoggerFactory.getLogger("org.springside.examples.showcase.log.dbLogExample");
		int logCount = 10;
		for (int i = 1; i <= logCount; i++) {
			dbLogger.info("helloworld {}", i);
		}
	}

	@Test
	@Ignore("Only for performance Test")
	public void performanceTest() throws InterruptedException {
		Logger dbLogger = LoggerFactory.getLogger("org.springside.examples.showcase.log.dbLogExample");
		int totalCount = 20000;
		int tps = 4000;
		Date batchBegin = new Date();
		for (int i = 1; i <= totalCount; i++) {
			dbLogger.info("helloworld {}", i);
			if (i % tps == 0) {
				Date batchEnd = new Date();
				long sleepMillSecs = 1000 - (batchEnd.getTime() - batchBegin.getTime());
				if (sleepMillSecs > 0) {
					Thread.sleep(sleepMillSecs);
				}
				batchBegin = new Date();
			}
		}
	}
}
