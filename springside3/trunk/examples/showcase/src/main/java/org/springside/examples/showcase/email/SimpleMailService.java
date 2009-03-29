package org.springside.examples.showcase.email;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * 文本邮件服务类.
 * 
 * 演示多线程群发文本邮件.
 * 
 * @author calvin
 */
public class SimpleMailService {

	private static Logger logger = LoggerFactory.getLogger(SimpleMailService.class);

	private JavaMailSender mailSender;

	private Executor executor;

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	public void setExecutor(Executor executor) {
		this.executor = executor;
	}

	/**
	 * 发送纯文本的用户修改通知邮件.
	 */
	public void sendNotifyMail(String userName) {

		//演示用Executor多线程群发邮件
		for (int i = 0; i < 2; i++) {
			//简化演示,发送两封地址相同的邮件.
			String to = "springside3.demo@gmail.com";
			String text = userName + "被修改.";
			executor.execute(new MailTask(mailSender, to, text));
		}
	}

	/**
	 * 群发邮件任务类.
	 */
	private static class MailTask implements Runnable {

		private JavaMailSender mailSender;
		private String to;
		private String text;

		public MailTask(JavaMailSender mailSender, String to, String text) {
			this.mailSender = mailSender;
			this.to = to;
			this.text = text;
		}

		public void run() {
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setFrom("springside3.demo@gmail.com");
				msg.setSubject("用户修改通知");
				msg.setText(text);
				msg.setTo(to);

				mailSender.send(msg);
				logger.info("纯文本邮件已发送至" + to);
			} catch (MailException e) {
				logger.error("发送邮件失败", e);
			}
		}
	}
}
