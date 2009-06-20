package org.springside.examples.showcase.unit.xml;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springside.examples.showcase.xml.jaxb.JaxbUtil;
import org.springside.examples.showcase.xml.jaxb.Role;
import org.springside.examples.showcase.xml.jaxb.User;

/**
 * 演示基于JAXB的Java对象-XML转换
 * @author calvin
 */
@Ignore("For manual test")
public class JaxbTest {
	JaxbUtil jaxbUtil;

	@Before
	public void setUp() {
		jaxbUtil = new JaxbUtil(User.class);
	}

	@Test
	public void objectToXml() {
		User user = new User();
		user.setId(1L);
		user.setName("calvin");
		user.setPassword("calvin");
		user.setEmail("calvin@abc.com");

		user.getRoles().add(new Role(1L, "admin"));
		user.getRoles().add(new Role(1L, "user"));
		user.getInterests().add("movie");
		user.getInterests().add("sports");

		user.getHouses().put("gz", "house1");
		user.getHouses().put("bj", "house2");

		String xml = jaxbUtil.marshal(user);
		System.out.println(xml);
	}

	@Test
	public void xmlToObject() {
		String xml = "<user id=\"1\"><name>calvin</name><email>calvin@abc.com</email><roles>"
				+ "<role name=\"admin\" id=\"1\"/><role name=\"user\" id=\"1\"/></roles><interests>"
				+ "<interest>movie</interest><interest>sports</interest></interests><houses>"
				+ "<item key=\"bj\"><value>house2</value></item><item key=\"gz\">"
				+ "<value>house1</value></item></houses></user>";
		User user = jaxbUtil.unmarshal(xml);
		System.out.println(user);
	}

}
