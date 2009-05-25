package org.springside.modules.integration.security.jcaptcha;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.web.context.ContextLoader;
import org.springside.modules.security.jcaptcha.JCaptchaFilter;

public class JCaptchaFilterTest extends Assert {

	private MockFilterConfig config = new MockFilterConfig();;
	private MockFilterChain chain = new MockFilterChain();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();
	
	private JCaptchaFilter filter = new JCaptchaFilter();
	
	private String failUrl = "403.jsp";
	
	@Before
	public void setUp() {
		config.addInitParameter(JCaptchaFilter.FAILURE_URL_PARAM, failUrl);

		MockServletContext servletContext = (MockServletContext) config.getServletContext();
		servletContext.addInitParameter(ContextLoader.CONFIG_LOCATION_PARAM,
				"/applicationContext.xml,/security/applicationContext-security.xml");
		
		new ContextLoader().initWebApplicationContext(servletContext);
	}

	@Test
	public void displayImage() throws ServletException, IOException {
		request.setServletPath("/img/capatcha.jpg");
	
		filter.init(config);
		filter.doFilter(request, response, chain);
		
		assertEquals("image/jpeg", response.getContentType());
		assertEquals(true, response.getContentAsByteArray().length > 0);
	}
	
	@Test
	public void validateWithErrorCaptcha() throws ServletException, IOException {
		request.setServletPath(JCaptchaFilter.DEFAULT_FILTER_PROCESSES_URL);
		request.setParameter(JCaptchaFilter.CAPTCHA_PARAMTER_NAME_PARAM, "12345678");
		
		filter.init(config);
		filter.doFilter(request, response, chain);
		
		assertEquals(failUrl, response.getRedirectedUrl());
		
	}
	
	@Test
	public void validateWithAutoPass() throws ServletException, IOException {
		String autoPassValue = "1234";
		config.addInitParameter(JCaptchaFilter.AUTO_PASS_VALUE_PARAM, autoPassValue);
		
		request.setServletPath(JCaptchaFilter.DEFAULT_FILTER_PROCESSES_URL);
		request.setParameter(JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME, autoPassValue);
		
		filter.init(config);
		filter.doFilter(request, response, chain);

		assertEquals(null, response.getRedirectedUrl());
		

	}

}
