package org.springside.modules.security.jcaptcha;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.AuthenticationException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.ui.AbstractProcessingFilter;

/**
 * 与SpringSecurity紧耦合的JCaptchaFilter,返回SpringSecurty异常.
 * 
 * @author calvin
 */
public class JCaptchaSpringFilter extends JCaptchaFilter {

	@Override
	protected void redirectFailureUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//在session中放入SpringSecurity的Exception.
		AuthenticationException exception = new BadCredentialsException("验证码失败");
		request.getSession(true).setAttribute(AbstractProcessingFilter.SPRING_SECURITY_LAST_EXCEPTION_KEY, exception);

		super.redirectFailureUrl(request, response);
	}
}
