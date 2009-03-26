package org.springside.examples.showcase.email;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class SimpleMailManager {

	private static Logger logger = LoggerFactory.getLogger(SimpleMailManager.class);

	private JavaMailSender mailSender;
	
	private String from = "springside3.demo@gmail.com";
	private String subject = "User modify notification";
	
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void sendNotifyMail(String to) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setFrom(from);
		msg.setSubject(subject);
		msg.setTo(to);
		msg.setText("最简单中文邮件.");
		try {
			mailSender.send(msg);
			logger.info("邮件已发送至" + to);
		} catch (MailException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
