package org.springside.examples.showcase.intergration.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.junit38.SpringContextTestCase;

/**
 * sprinside-jee中ClientMBeanProxyFactory的测试用例.
 * 
 * @author calvin
 */
//载入/log/applicationContext-logger.xml和父类中定义的applicationContext.xml.
@ContextConfiguration(locations = { "/log/applicationContext-log.xml" })
public class AsyncLoggerTest extends SpringContextTestCase {


	public void testDBLogger() throws Exception {
		Logger logger = LoggerFactory.getLogger("org.springside.examples.showcase.log.dbLogExample");
		int logCount = 10;
		for (int i = 1; i <= logCount; i++) {
			logger.info("helloworld {}", i);
		}
	}
}
