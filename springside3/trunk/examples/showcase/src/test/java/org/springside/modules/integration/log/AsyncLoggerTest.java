package org.springside.modules.integration.log;

import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.log.web.LogAction;
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
		Logger dbLogger = Logger.getLogger(LogAction.DB_LOGGER_NAME);
		int logCount = 10;
		for (int i = 1; i <= logCount; i++) {
			dbLogger.info("helloworld" + i);
		}
		//TODO: sleep then check database
	}

	@Test
	@Ignore("Only for performance Test")
	public void performanceTest() throws InterruptedException {
		Logger dbLogger = Logger.getLogger(LogAction.DB_LOGGER_NAME);
		int totalCount = 20000;
		int tps = 4000;
		Date batchBegin = new Date();
		for (int i = 1; i <= totalCount; i++) {
			dbLogger.info("helloworld " + i);
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
