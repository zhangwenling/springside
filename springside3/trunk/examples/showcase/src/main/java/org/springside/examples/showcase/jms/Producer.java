package org.springside.examples.showcase.jms;

import java.util.HashMap;
import java.util.Map;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springside.examples.showcase.common.entity.User;

@SuppressWarnings("unchecked")
public class Producer {

	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;
	private Destination notifyTopic;

	public void sendQueue(final User user) {
		Map map = new HashMap();
		map.put("userName", user.getName());
		map.put("email", user.getEmail());

		jmsTemplate.convertAndSend(notifyQueue, map);
	}

	public void sendTopic(final User user) {
		jmsTemplate.send(notifyTopic, new MessageCreator() {
			public Message createMessage(Session session) throws JMSException {

				MapMessage message = session.createMapMessage();
				message.setString("userName", user.getName());
				message.setString("email", user.getEmail());

				message.setIntProperty("foo", 1234);
				message.setJMSCorrelationID("123-00001");

				return message;
			}
		});
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}

	public void setNotifyTopic(Destination nodifyTopic) {
		this.notifyTopic = nodifyTopic;
	}

}
