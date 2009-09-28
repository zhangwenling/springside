package org.springside.examples.showcase.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TopicListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(TopicListener.class);

	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;
		try {
			logger.info("UserName:" + mapMessage.getString("userName") + ", Email:" + mapMessage.getString("email")
					+ ", Property:" + mapMessage.getIntProperty("foo"));
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}

	}
}
