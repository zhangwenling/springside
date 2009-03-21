package org.springside.examples.showcase.security;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.octo.captcha.service.CaptchaServiceException;
import com.octo.captcha.service.image.DefaultManageableImageCaptchaService;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/**
 * 集成JCaptcha的Filter.
 * 
 * 通过与SpringSecurity处理相同的filterProcessesUrl实现集成.
 * filterProcessesUrl的默认值为/j_spring_security_check
 * 
 * @author calvin
 */
public class CaptchaFilter implements Filter {

	private static final String DEFAULT_FILTER_PROCESSES_URL = "/j_spring_security_check";
	private static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
	private static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

	private String filterProcessesUrl;
	private String failureUrl;
	private String captchaServiceId;
	private String captchaParamterName;

	private DefaultManageableImageCaptchaService captchaService = null;

	public void init(FilterConfig fConfig) throws ServletException {
		initParameters(fConfig);
		initCaptchaService(fConfig);
	}

	/**
	 * 初始化web.xml中定义的filter init-param.
	 */
	private void initParameters(FilterConfig fConfig) {
		failureUrl = fConfig.getInitParameter("failureUrl");
		if (StringUtils.isBlank(failureUrl)) {
			throw new IllegalArgumentException("CaptchaFilter缺少failureUrl参数");
		}
		failureUrl = fConfig.getServletContext().getContextPath() + failureUrl;

		filterProcessesUrl = fConfig.getInitParameter("filterProcessesUrl");
		if (StringUtils.isBlank(filterProcessesUrl)) {
			filterProcessesUrl = DEFAULT_FILTER_PROCESSES_URL;
		}

		captchaServiceId = fConfig.getInitParameter("captchaServiceId");
		if (StringUtils.isBlank(captchaServiceId)) {
			captchaServiceId = DEFAULT_CAPTCHA_SERVICE_ID;
		}

		captchaParamterName = fConfig.getInitParameter("captchaParamterName");
		if (StringUtils.isBlank(captchaParamterName)) {
			captchaParamterName = DEFAULT_CAPTCHA_PARAMTER_NAME;
		}
	}

	/**
	 * 从ApplicatonContext获取CaptchaService实例.
	 */
	private void initCaptchaService(FilterConfig fConfig) {
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(fConfig.getServletContext());
		captchaService = (DefaultManageableImageCaptchaService) context.getBean(captchaServiceId);
	}

	public void destroy() {
	}

	public void doFilter(ServletRequest theRequest, ServletResponse theResponse, FilterChain chain) throws IOException,
			ServletException {
		HttpServletRequest request = (HttpServletRequest) theRequest;
		HttpServletResponse response = (HttpServletResponse) theResponse;

		String path = request.getServletPath();
		if (StringUtils.startsWith(path, filterProcessesUrl)) {
			verifyCaptchaChallenge(request, response, chain);
		} else {
			genCaptchaImage(request, response);
		}
	}

	/**
	 * 生成验证码图片.
	 */
	public void genCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

		ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
		try {
			String captchaId = request.getSession().getId();
			BufferedImage challenge = captchaService.getImageChallengeForID(captchaId, request.getLocale());
			JPEGImageEncoder jpegEncoder = JPEGCodec.createJPEGEncoder(jpegOutputStream);
			jpegEncoder.encode(challenge);
		} catch (IllegalArgumentException e) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		} catch (CaptchaServiceException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");
		ServletOutputStream responseOutputStream = response.getOutputStream();
		responseOutputStream.write(captchaChallengeAsJpeg);
		responseOutputStream.flush();
		responseOutputStream.close();
	}

	/**
	 * 验证验证码. 
	 */
	private boolean verifyCaptchaChallenge(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String captchaID = request.getSession().getId();
		String challengeResponse = request.getParameter(captchaParamterName);

		boolean passed = captchaService.validateResponseForID(captchaID, challengeResponse);
		if (passed) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(failureUrl);
		}
		return false;
	}
}
