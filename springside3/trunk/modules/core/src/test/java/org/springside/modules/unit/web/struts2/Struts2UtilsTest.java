package org.springside.modules.unit.web.struts2;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.struts2.ServletActionContext;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springside.modules.web.struts2.Struts2Utils;

import com.opensymphony.xwork2.ActionContext;

public class Struts2UtilsTest extends Assert {

	@Test
	public void render() throws UnsupportedEncodingException {
		ActionContext.setContext(new ActionContext(new HashMap()));
		MockHttpServletResponse response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);

		Struts2Utils.render("text/plain", "hello");
		assertEquals("text/plain;charset=UTF-8", response.getContentType());
		assertEquals("No-cache", response.getHeader("Pragma"));
		assertEquals("hello", response.getContentAsString());

		response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);
		Struts2Utils.render("text/plain", "hello", "encoding:GBK");
		assertEquals("text/plain;charset=GBK", response.getContentType());
		assertEquals("No-cache", response.getHeader("Pragma"));
		assertEquals("hello", response.getContentAsString());

		response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);
		Struts2Utils.render("text/plain", "hello", "no-cache:false");
		assertEquals("text/plain;charset=UTF-8", response.getContentType());
		assertEquals(null, response.getHeader("Pragma"));
		assertEquals("hello", response.getContentAsString());

		response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);
		Struts2Utils.render("text/plain", "hello", "encoding:GBK", "no-cache:false");
		assertEquals("text/plain;charset=GBK", response.getContentType());
		assertEquals(null, response.getHeader("Pragma"));
		assertEquals("hello", response.getContentAsString());
	}

	@Test
	public void renderJson() throws UnsupportedEncodingException {
		ActionContext.setContext(new ActionContext(new HashMap()));
		MockHttpServletResponse response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);

		Map map = new LinkedHashMap();
		map.put("age", 10);
		map.put("name", "foo");
		Struts2Utils.renderJson(map);
		assertEquals("{\"age\":10,\"name\":\"foo\"}", response.getContentAsString());

		response = new MockHttpServletResponse();
		ServletActionContext.setResponse(response);
		Struts2Utils.renderJson(new TestBean());
		assertEquals("{\"age\":10,\"name\":\"foo\"}", response.getContentAsString());
	}

	public class TestBean {
		int age = 10;
		String name = "foo";

		public int getAge() {
			return age;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

	}

}
