package org.springside.examples.showcase.unit.jms;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springside.examples.showcase.common.entity.User;
import org.springside.examples.showcase.jms.NotifyMessageProducer;
import org.springside.examples.showcase.jms.NotifyTopicListener;
import org.springside.modules.test.mock.MockAppender;
import org.springside.modules.test.spring.SpringContextTestCase;
import org.springside.modules.test.utils.TimeUtils;

@ContextConfiguration(locations = { "/applicationContext-test.xml", "/jms/applicationContext-common.xml",
		"/jms/applicationContext-producer.xml", "/jms/applicationContext-consumer.xml" }, inheritLocations = false)
public class JmsTopicTest extends SpringContextTestCase {

	@Autowired
	private NotifyMessageProducer notifyMessageProducer;

	@Test
	public void test() {
		TimeUtils.sleep(1000);
		MockAppender appender = new MockAppender();
		appender.addToLogger(NotifyTopicListener.class);

		User user = new User();
		user.setName("calvin");
		user.setEmail("calvin@sringside.org.cn");

		notifyMessageProducer.sendTopic(user);
		logger.info("sended message");

		TimeUtils.sleep(1000);
		assertNotNull(appender.getFirstLog());
		assertEquals("UserName:calvin, Email:calvin@sringside.org.cn, Property:1234", appender.getFirstLog()
				.getMessage());
	}
}
