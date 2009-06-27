package org.springside.examples.showcase.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.util.StringUtils;

/**
 * 纯文本邮件服务类.
 * 
 * @author calvin
 */
public class SimpleMailService {
	private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);
	private JavaMailSender mailSender;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 发送纯文本的用户修改通知邮件.
	 */
	public void sendNotificationMail(String userName) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom("springside3.demo@gmail.com");
		msg.setTo("springside3.demo@gmail.com");
		msg.setSubject("用户修改通知");
		msg.setText(userName + "被修改.");

		try {
			mailSender.send(msg);
			logger.info("纯文本邮件已发送至{}", StringUtils.arrayToCommaDelimitedString(msg.getTo()));
		} catch (MailException e) {
			logger.error("发送邮件失败", e);
		}

	}

}
