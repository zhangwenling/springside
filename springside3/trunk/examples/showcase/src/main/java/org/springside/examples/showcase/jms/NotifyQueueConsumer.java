package org.springside.examples.showcase.jms;

import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.jms.Destination;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springside.examples.showcase.email.SimpleMailService;

/**
 * Queue消息的同步主动接收者.
 * 
 * 使用jmsTemplate的receiveAndConvert()主动接收消息,可对并发线程,消息接收处理速度等进行控制.
 * 
 * @author calvin
 */
@SuppressWarnings("unchecked")
public class NotifyQueueConsumer implements Runnable {

	private static Logger logger = LoggerFactory.getLogger(NotifyQueueConsumer.class);

	@Autowired(required = false)
	private SimpleMailService simpleMailService;

	private ScheduledExecutorService executor;

	private JmsTemplate jmsTemplate;
	private Destination notifyQueue;

	/**
	 * 在Spring Context初始化时, 启动Schedule每秒运行一次.
	 */
	@PostConstruct
	public void start() {
		executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(this, 0, 1000, TimeUnit.MILLISECONDS);
	}

	/**
	 * 在Spring Context关闭时, 停止Schedule.
	 */
	@PreDestroy
	public void stop() {
		try {
			executor.shutdown();
			executor.awaitTermination(1000, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.debug("awaitTermination被中断", e);
		}
	}

	/**
	 * 每秒定时运行的主动消息接收方法.
	 */
	public void run() {

		Map message = (Map) jmsTemplate.receiveAndConvert(notifyQueue);
		if (message != null) {
			//发送邮件
			if (simpleMailService != null) {
				try {
					simpleMailService.sendNotificationMail((String) message.get("userName"));
				} catch (Exception e) {
					logger.error("邮件发送失败", e);
				}
			}

			//打印消息详情
			logger.info("UserName:" + message.get("userName") + ", Email:" + message.get("email"));
		}
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setNotifyQueue(Destination notifyQueue) {
		this.notifyQueue = notifyQueue;
	}
}
