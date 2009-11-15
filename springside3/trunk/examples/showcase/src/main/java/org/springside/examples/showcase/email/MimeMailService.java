package org.springside.examples.showcase.email;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * MIME邮件服务类.
 * 
 * 演示由Freemarker引擎生成的的html格式邮件, 并带有附件.
 * 
 * @author calvin
 */
public class MimeMailService {

	private static final String ENCODING = "utf-8";

	private static Logger logger = LoggerFactory.getLogger(MimeMailService.class);

	private JavaMailSender mailSender;
	private Template template;

	/**
	 * Spring的MailSender.
	 */
	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	/**
	 * 注入Freemarker引擎配置,构造Freemarker 邮件内容模板.
	 */
	public void setFreemarkerConfiguration(Configuration freemarkerConfiguration) throws IOException {
		//根据freemarkerConfiguration的templateLoaderPath载入文件.
		template = freemarkerConfiguration.getTemplate("mailTemplate.ftl", ENCODING);
	}

	/**
	 * 发送MIME格式的用户修改通知邮件.
	 */
	public void sendNotificationMail(String userName) {
		MimeMessage msg = mailSender.createMimeMessage();

		try {
			MimeMessageHelper helper = new MimeMessageHelper(msg, true, ENCODING);

			helper.setTo("springside3.demo@gmail.com");
			helper.setFrom("springside3.demo@gmail.com");
			helper.setSubject("用户修改通知");

			buildContent(helper, userName);
			buildAttachment(helper);

			mailSender.send(msg);
			logger.info("HTML版邮件已发送至springside3.demo@gmail.com");
		} catch (MessagingException e) {
			logger.error("构造邮件失败", e);
		} catch (Exception e) {
			logger.error("发送邮件失败", e);
		}
	}

	/**
	 * 使用Freemarker生成html格式内容.
	 */
	@SuppressWarnings("unchecked")
	private void buildContent(MimeMessageHelper helper, String userName) throws MessagingException {

		try {
			Map context = new HashMap();
			context.put("userName", userName);
			String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, context);
			helper.setText(content, true);
		} catch (IOException e) {
			logger.error("构造邮件失败,FreeMarker模板不存在", e);
			throw new MessagingException("FreeMarker模板不存在", e);
		} catch (TemplateException e) {
			logger.error("构造邮件失败,FreeMarker处理失败", e);
			throw new MessagingException("FreeMarker处理失败", e);
		}
	}

	/**
	 * 添加附件.
	 */
	private void buildAttachment(MimeMessageHelper helper) throws MessagingException {
		try {
			//使用Spring的Resource Loader获取打包在classpath中的附件.
			ClassPathResource attachment = new ClassPathResource("/email/mailAttachment.txt");
			helper.addAttachment("mailAttachment.txt", attachment.getFile());
		} catch (IOException e) {
			logger.error("构造邮件失败,附件文件不存在", e);
			throw new MessagingException("附件文件不存在", e);
		}
	}
}
