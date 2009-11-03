package org.springside.modules.unit.web;

import java.io.IOException;

import javax.servlet.ServletException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springside.modules.web.ResponseHeaderFilter;

public class RequestHeaderFilterTest extends Assert {
	@Test
	public void test() throws IOException, ServletException {
		MockFilterConfig config = new MockFilterConfig();
		MockFilterChain chain = new MockFilterChain();
		MockHttpServletRequest request = new MockHttpServletRequest();
		MockHttpServletResponse response = new MockHttpServletResponse();
		config.addInitParameter("foo", "1");
		config.addInitParameter("bar", "2");
		
		ResponseHeaderFilter filter = new ResponseHeaderFilter();
		filter.init(config);
		filter.doFilter(request, response, chain);
		
		assertEquals("1",response.getHeader("foo"));
		assertEquals("2",response.getHeader("bar"));
	}

}
