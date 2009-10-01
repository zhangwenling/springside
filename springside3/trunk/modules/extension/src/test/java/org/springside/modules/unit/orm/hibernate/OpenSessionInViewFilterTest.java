package org.springside.modules.unit.orm.hibernate;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springside.modules.orm.hibernate.OpenSessionInViewFilter;
import org.springside.modules.utils.ReflectionUtils;

public class OpenSessionInViewFilterTest extends Assert {

	@Test
	public void shouldNotFilter() throws ServletException, InvocationTargetException {
		MockFilterConfig config = new MockFilterConfig();
		config.addInitParameter(OpenSessionInViewFilter.EXCLUDE_SUFFIXS_NAME, "jpg,css");

		MockHttpServletRequest request = new MockHttpServletRequest();

		OpenSessionInViewFilter filter = new OpenSessionInViewFilter();
		filter.init(config);

		request.setServletPath("/img/foo.jpg");
		assertEquals(true, ReflectionUtils.invokeMethod(filter, "shouldNotFilter",
				new Class[] { HttpServletRequest.class }, new Object[] { request }));

		request.setServletPath("/foo.action");
		assertEquals(false, ReflectionUtils.invokeMethod(filter, "shouldNotFilter",
				new Class[] { HttpServletRequest.class }, new Object[] { request }));

	}
}
