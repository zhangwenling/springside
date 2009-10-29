package org.springside.modules.test.spring;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

/**
 * 对Spring MockRequest/MockReponse的Utils集合.
 * 
 * @author calvin
 */
public class SpringWebMockUtils {

	/**
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param contexts application context路径列表.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext, String... configLocations) {
		String configLocationsString = StringUtils.join(configLocations, ",");
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM, configLocationsString);
		new ContextLoader().initWebApplicationContext(servletContext);
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
