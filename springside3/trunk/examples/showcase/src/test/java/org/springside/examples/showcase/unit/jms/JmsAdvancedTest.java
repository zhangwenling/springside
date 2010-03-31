package org.springside.examples.showcase.unit.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.advanced.AdvancedNotifyMessageListener;
import org.springside.examples.showcase.jms.advanced.AdvancedNotifyMessageProducer;
import org.springside.modules.test.mock.MockLog4jAppender;
import org.springside.modules.test.spring.SpringContextTestCase;
import org.springside.modules.test.utils.TimeUtils;

@ContextConfiguration(locations = { "/applicationContext-test.xml", "/jms/applicationContext-common.xml",
		"/jms/applicationContext-simple.xml", "/jms/applicationContext-advanced.xml" })
public class JmsAdvancedTest extends SpringContextTestCase {

	@Autowired
	private AdvancedNotifyMessageProducer notifyMessageProducer;

	@Test
	public void queueMessage() {
		TimeUtils.sleep(1000);
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendQueue(user);
		logger.info("sended message");

		TimeUtils.sleep(1000);
		assertNotNull(appender.getFirstLog());
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn, ObjectType:User", appender.getFirstLog()
				.getMessage());
	}

	@Test
	public void topicMessage() {
		TimeUtils.sleep(1000);
		MockLog4jAppender appender = new MockLog4jAppender();
		appender.addToLogger(AdvancedNotifyMessageListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendTopic(user);
		logger.info("sended message");

		TimeUtils.sleep(1000);
		assertNotNull(appender.getFirstLog());
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn, ObjectType:User", appender.getFirstLog()
				.getMessage());
	}
}
