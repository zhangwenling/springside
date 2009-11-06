package org.springside.examples.showcase.jms;

import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springside.examples.showcase.email.MimeMailService;

/**
 * Topic消息的异步被动接收者.
 * 
 * 使用Spring的MessageListenerContainer侦听消息并调用本Listener进行处理.
 * 
 * @author calvin
 *
 */
public class NotifyTopicListener implements MessageListener {

	private static Logger logger = LoggerFactory.getLogger(NotifyTopicListener.class);

	@Autowired(required = false)
	private MimeMailService mimeMailService;

	/**
	 * MessageListener回调函数.
	 */
	public void onMessage(Message message) {
		MapMessage mapMessage = (MapMessage) message;

		//发送邮件
		if (mimeMailService != null) {
			try {
				mimeMailService.sendNotificationMail(mapMessage.getString("userName"));
			} catch (Exception e) {
				logger.error("邮件发送失败", e);
			}
		}

		//打印消息详情
		try {
			logger.info("UserName:" + mapMessage.getString("userName") + ", Email:" + mapMessage.getString("email")
					+ ", Property:" + mapMessage.getIntProperty("foo"));
		} catch (JMSException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
