package org.springside.examples.showcase.integration.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.Producer;
import org.springside.examples.showcase.jms.QueueConsumer;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/jms/applicationContext-common.xml", "/jms/applicationContext-producer.xml",
		"/jms/applicationContext-consumer.xml" }, inheritLocations = false)
public class JmsQueueTest extends SpringContextTestCase {

	@Autowired
	private Producer producer;
	@Autowired
	private QueueConsumer consumer;

	@Test
	public void test() {
		MockAppender appender = new MockAppender();
		appender.addToLogger(QueueConsumer.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		producer.sendQueue(user);
		logger.info("sended message");

		consumer.receive();
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn", appender.getFirstLog().getMessage());
	}
}
