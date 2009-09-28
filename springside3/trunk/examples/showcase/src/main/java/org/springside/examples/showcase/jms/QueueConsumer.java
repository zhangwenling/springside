package org.springside.examples.showcase.jms;

import java.util.Map;

import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.core.JmsTemplate;

@SuppressWarnings("unchecked")
public class QueueConsumer {

	private static Logger logger = LoggerFactory.getLogger(QueueConsumer.class);

	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;

	public void receive() {
		Map message = (Map) jmsTemplate.receiveAndConvert(notifyQueue);
		logger.info("UserName:" + message.get("userName") + ", Email:" + message.get("email"));
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}
}
