package org.springside.modules.test.spring;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 * 对Spring MockRequest/MockReponse的Utils集合.
 * 
 * @author calvin
 */
public class SpringWebMockUtils {
	/**
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param appContext 已创建的ApplicationContext.
	 */
	public static void initWebApplicationContext(ServletContext servletContext, ApplicationContext ac) {
		ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setParent(ac);
		wac.setConfigLocation("");
		wac.setServletContext(servletContext);
		wac.refresh();

		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
	}

	/**
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param contexts 逗号分隔的application context路径列表.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String configLocations) {
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocations);
		new ContextLoader().initWebApplicationContext(servletContext);
	}

	/**
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param contexts application context路径列表.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String... configLocations) {
		initWebApplicationContext(servletContext, StringUtils.join(configLocations, ","));
	}

	/**
	 * 关闭ServletContext中的Spring WebApplicationContext.
	 */
	public static void closeWebApplicationContext(ServletContext servletContext) {
		WebApplicationContext wac = (WebApplicationContext) servletContext
				.getAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		if (wac instanceof ConfigurableWebApplicationContext) {
			((ConfigurableWebApplicationContext) wac).close();
		}
	}
}
