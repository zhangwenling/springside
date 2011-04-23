package org.springside.modules.unit.test.utils;

import static org.junit.Assert.assertEquals;

import org.apache.struts2.ServletActionContext;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springside.modules.test.utils.Struts2TestUtils;

public class Struts2TestUtilsTest {

	@Test
	public void setToStruts2() {
		MockHttpServletRequest request = new MockHttpServletRequest();
		Struts2TestUtils.setRequestToStruts2(request);
		assertEquals(request, ServletActionContext.getRequest());

		MockHttpServletResponse response = new MockHttpServletResponse();
		Struts2TestUtils.setResponseToStruts2(response);
		assertEquals(request, ServletActionContext.getRequest());
	}
}
