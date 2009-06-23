package org.springside.examples.showcase.unit.xml;

import org.junit.Before;
import org.junit.Test;
import org.springside.examples.showcase.xml.xstream.Role;
import org.springside.examples.showcase.xml.xstream.User;
import org.springside.examples.showcase.xml.xstream.XStreamUtil;

/**
 * 演示基于JAXB的Java对象-XML转换
 * @author calvin
 */
public class XStreamTest {
	
	private XStreamUtil xstream;

	@Before
	public void setUp() {
		xstream = new XStreamUtil(User.class, Role.class);
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

		String xml = xstream.toXml(user);
		System.out.println(xml);
	}

	@Test
	public void xmlToObject() {
		String xml = "<user id=\"1\"><name>calvin</name><email>calvin@abc.com</email><roles>"
				+ "<role id=\"1\" name=\"admin\"/><role id=\"1\" name=\"user\"/></roles>"
				+ "<interest>movie</interest><interest>sports</interest><houses><house key=\"bj\">"
				+ "house1</house><house key=\"gz\">house2</house></houses></user>";

		User user = xstream.fromXml(xml);
		System.out.println("User:" + user);
	}
}
