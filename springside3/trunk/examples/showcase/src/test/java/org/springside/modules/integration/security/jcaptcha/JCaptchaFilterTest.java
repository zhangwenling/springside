package org.springside.modules.integration.security.jcaptcha;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springside.modules.security.jcaptcha.JCaptchaFilter;
import org.springside.modules.test.spring.SpringWebMockUtils;

/**
 * sprinside-jee中JCaptchaFilter的测试用例.
 * 
 * @author calvin
 */
public class JCaptchaFilterTest extends Assert {

	private MockFilterConfig config = new MockFilterConfig();
	private MockFilterChain chain = new MockFilterChain();
	private MockHttpServletRequest request = new MockHttpServletRequest();
	private MockHttpServletResponse response = new MockHttpServletResponse();

	private JCaptchaFilter filter = new JCaptchaFilter();

	private String failUrl = "403.jsp";

	@Before
	public void setUp() throws ServletException {
		MockServletContext context = (MockServletContext) config.getServletContext();
		SpringWebMockUtils.initWebApplicationContext(context, "/applicationContext.xml",
				"/security/applicationContext-security.xml");
		config.addInitParameter(JCaptchaFilter.PARAM_FAILURE_URL, failUrl);

		filter.init(config);
	}

	@After
	public void tearDown() {
		SpringWebMockUtils.closeWebApplicationContext(config.getServletContext());
	}

	@Test
	public void displayImage() throws ServletException, IOException {
		//非 filterProcessesUrl 均为图片生成URL.
		request.setServletPath("/img/capatcha.jpg");

		filter.doFilter(request, response, chain);

		assertEquals("image/jpeg", response.getContentType());
		assertEquals(true, response.getContentAsByteArray().length > 0);
	}

	@Test
	public void validateWithErrorCaptcha() throws ServletException, IOException {
		request.setServletPath(JCaptchaFilter.DEFAULT_FILTER_PROCESSES_URL);
		request.setParameter(JCaptchaFilter.PARAM_CAPTCHA_PARAMTER_NAME, "12345678ABCDEFG");

		filter.doFilter(request, response, chain);

		assertEquals(failUrl, response.getRedirectedUrl());
	}

	@Test
	public void validateWithAutoPass() throws ServletException, IOException {
		//在config中设定自动通过密码
		String autoPassValue = "1234";
		config.addInitParameter(JCaptchaFilter.PARAM_AUTO_PASS_VALUE, autoPassValue);

		request.setServletPath(JCaptchaFilter.DEFAULT_FILTER_PROCESSES_URL);
		request.setParameter(JCaptchaFilter.DEFAULT_CAPTCHA_PARAMTER_NAME, autoPassValue);

		filter.doFilter(request, response, chain);

		assertEquals(null, response.getRedirectedUrl());
	}
}
