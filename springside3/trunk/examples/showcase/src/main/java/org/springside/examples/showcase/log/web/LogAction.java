package org.springside.examples.showcase.log.web;

import org.apache.struts2.convention.annotation.Namespace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.ActionSupport;

/**
 * FlashChart演示生成Amcharts所需 CVS/XML格式数据的Action.
 * 
 * @author calvin
 */

@Namespace("/log")
@SuppressWarnings("serial")
public class LogAction extends ActionSupport {

	/**
	 * 	在log4j.properties中,本logger已被指定使用asyncAppender
	 */
	public static final String DB_LOGGER = "org.springside.examples.showcase.log.dbLogExample";

	@Override
	public String execute() {
		Logger logger = LoggerFactory.getLogger(DB_LOGGER);
		logger.info("helloworld!!");
		return SUCCESS;
	}

}
