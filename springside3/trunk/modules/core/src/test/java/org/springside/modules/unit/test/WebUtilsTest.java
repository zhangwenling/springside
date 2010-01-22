package org.springside.modules.unit.test;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springside.modules.test.utils.WebUtils;

public class WebUtilsTest extends Assert {

	@Test
	public void initByPaths() {
		MockServletContext servletContext = new MockServletContext();
		WebUtils.initWebApplicationContext(servletContext, "classpath:/applicationContext-test.xml");
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertNotNull(context);
		assertNotNull(context.getBean("springContextHolder"));
	}

	@Test
	public void initByApplicationConetxt() {
		ClassPathXmlApplicationContext ac = new ClassPathXmlApplicationContext("classpath:/applicationContext-test.xml");
		MockServletContext servletContext = new MockServletContext();
		WebUtils.initWebApplicationContext(servletContext, ac);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertNotNull(context);
		assertNotNull(context.getBean("springContextHolder"));
	}

	@Test
	public void closeApplicationContext() {
		MockServletContext servletContext = new MockServletContext();
		WebUtils.initWebApplicationContext(servletContext, "classpath:/applicationContext-test.xml");

		WebUtils.closeWebApplicationContext(servletContext);
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
		assertNull(context);
	}
}
