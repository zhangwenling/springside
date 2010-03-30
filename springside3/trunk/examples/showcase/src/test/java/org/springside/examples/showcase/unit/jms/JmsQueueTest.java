package org.springside.examples.showcase.unit.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.NotifyMessageProducer;
import org.springside.examples.showcase.jms.NotifyQueueListener;
import org.springside.modules.test.mock.MockLog4jAppender;
import org.springside.modules.test.spring.SpringContextTestCase;
import org.springside.modules.test.utils.TimeUtils;

@ContextConfiguration(locations = { "/applicationContext-test.xml", "/jms/applicationContext-common.xml",
		"/jms/applicationContext-simple.xml" }, inheritLocations = false)
public class JmsQueueTest extends SpringContextTestCase {

	@Autowired
	private NotifyMessageProducer notifyMessageProducer;

	@Test
	public void test() {
		TimeUtils.sleep(1000);
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(NotifyQueueListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendQueue(user);
		logger.info("sended message");

		TimeUtils.sleep(1000);
		assertNotNull(appender.getFirstLog());
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn", appender.getFirstLog().getMessage());
	}
}
