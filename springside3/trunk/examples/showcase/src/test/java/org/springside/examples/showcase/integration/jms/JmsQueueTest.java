package org.springside.examples.showcase.integration.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.NotifyMessageProducer;
import org.springside.examples.showcase.jms.NotifyQueueConsumer;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/applicationContext-test.xml", "/jms/applicationContext-common.xml",
		"/jms/applicationContext-producer.xml", "/jms/applicationContext-consumer.xml" }, inheritLocations = false)
public class JmsQueueTest extends SpringContextTestCase {

	@Autowired
	private NotifyMessageProducer notifyMessageProducer;

	@Test
	public void test() {
		MockAppender appender = new MockAppender();
		appender.addToLogger(NotifyQueueConsumer.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendQueue(user);
		logger.info("sended message");
		sleep(1000);
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn", appender.getFirstLog().getMessage());
		logger.info("received message");
	}
}
