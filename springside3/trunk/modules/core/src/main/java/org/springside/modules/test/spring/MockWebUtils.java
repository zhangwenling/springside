package org.springside.modules.test.spring;

import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

import com.opensymphony.xwork2.ActionContext;

/**
 * 对Spring MockRequest/MockReponse的Util方法集合.
 * 对Struts2的ServletActionContext.getRequest()/getResponse()方法的支持.
 * @author calvin
 */
public class MockWebUtils {

	static {
		//初始化 Struts2 ActionContext
		ActionContext.setContext(new ActionContext(new HashMap()));
	}

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
	 * 在ServletContext里初始化Spring WebApplicationContext.
	 * 
	 * @param applicationContext 已创建的ApplicationContext.
	 */
	public static void initWebApplicationContext(MockServletContext servletContext,
			ApplicationContext applicationContext) {
		ConfigurableWebApplicationContext wac = new XmlWebApplicationContext();
		wac.setParent(applicationContext);
		wac.setServletContext(servletContext);
		wac.setConfigLocation("");
		servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, wac);
		wac.refresh();
	}

	/**
	 * 关闭ServletContext中的Spring WebApplicationContext.
	 */
	public static void closeWebApplicationContext(ServletContext servletContext) {
		new ContextLoader().closeWebApplicationContext(servletContext);
	}

	/**
	 * 将request放入Struts2的ServletActionContext,支持待测代码用ServletActionContext.getRequest()取出MockRequest.
	 */
	public static void setRequestToStruts2(HttpServletRequest request) {
		ServletActionContext.setRequest(request);
	}

	/**
	 * 将response放入Struts2的ServletActionContext,支持待测代码用ServletActionContext.getResponse()取出MockResponse.
	 */
	public static void setResponseToStruts2(HttpServletResponse response) {
		ServletActionContext.setResponse(response);
	}
}
