package org.springside.examples.showcase.integration.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.Producer;
import org.springside.examples.showcase.jms.TopicListener;
import org.springside.modules.log.MockAppender;
import org.springside.modules.test.spring.SpringContextTestCase;

@ContextConfiguration(locations = { "/jms/applicationContext-common.xml", "/jms/applicationContext-producer.xml",
		"/jms/applicationContext-consumer.xml" }, inheritLocations = false)
public class JmsTopicTest extends SpringContextTestCase {

	@Autowired
	private Producer producer;

	@Test
	public void test() {

		MockAppender appender = new MockAppender();
		appender.addToLogger(TopicListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		producer.sendTopic(user);
		logger.info("sended message");

		sleep(1000);
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn, Property:1234", appender.getFirstLog()
				.getMessage());
	}

}
