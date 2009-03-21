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
 * Servlet Filter implementation class CaptchaFilter
 */
public class CaptchaFilter implements Filter {

	private static final String DEFAULT_LOGIN_URL = "/j_spring_security_check";
	private static final String DEFAULT_CAPTCHA_SERVICE_ID = "captchaService";
	private static final String DEFAULT_CAPTCHA_PARAMTER_NAME = "j_captcha";

	private String imageUrl;
	private String loginUrl;
	private String errorUrl;
	private String captchaServiceId;
	private String captchaParamterName;

	private DefaultManageableImageCaptchaService captchaService = null;

	public void init(FilterConfig fConfig) throws ServletException {
		initParameters(fConfig);
		initCaptchaService(fConfig);
	}

	/**
	 * 初始化filter参数
	 */
	private void initParameters(FilterConfig fConfig) {
		imageUrl = fConfig.getInitParameter("imageUrl");
		errorUrl = fConfig.getInitParameter("errorUrl");

		if (StringUtils.isBlank(imageUrl) || StringUtils.isBlank(errorUrl)) {
			throw new IllegalArgumentException("CaptchaFilter缺少imageUrl或errorUrl参数");
		}

		loginUrl = StringUtils.defaultIfEmpty(fConfig.getInitParameter("loginUrl"), DEFAULT_LOGIN_URL);
		captchaServiceId = StringUtils.defaultIfEmpty(fConfig.getInitParameter("captchaServiceId"),
				DEFAULT_CAPTCHA_SERVICE_ID);
		captchaParamterName = StringUtils.defaultIfEmpty(fConfig.getInitParameter("captchaParamterName"),
				DEFAULT_CAPTCHA_PARAMTER_NAME);
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
		if (StringUtils.startsWith(path, imageUrl)) {
			genCaptchaImage(request, response);
		}
		if (StringUtils.startsWith(path, loginUrl)) {
			verifyCaptchaChallenge(request, response, chain);
		}
	}

	/**
	 * 生成验证码图片.
	 */
	public void genCaptchaImage(HttpServletRequest request, HttpServletResponse response) throws IOException {
		byte[] captchaChallengeAsJpeg = null;
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
		captchaChallengeAsJpeg = jpegOutputStream.toByteArray();

		// flush it in the response
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
		boolean passed = false;
		String captchaID = request.getSession().getId();
		String challengeResponse = request.getParameter(captchaParamterName);
		passed = captchaService.validateResponseForID(captchaID, challengeResponse);
		if (passed) {
			chain.doFilter(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + errorUrl);
		}
		return false;
	}
}
