package org.springside.examples.showcase.unit.xml;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springside.examples.showcase.xml.xstream.Role;
import org.springside.examples.showcase.xml.xstream.User;
import org.springside.modules.xml.XStreamBinder;
import org.springside.modules.xml.XmlBinder;

/**
 * 演示基于XStream的Java对象-XML转换.
 * 
 * @author calvin
 * 
 * 演示用xml如下
 * 
 * <?xml version="1.0" encoding="UTF-8"?>
 * <user id="1">
 * 	<name>calvin</name>
 * 	<roles>
 * 		<role id="1" name="admin"/>
 * 		<role id="2" name="user"/>
 * 	</roles>
 * 	<interest>movie</interest>
 * 	<interest>sports</interest>
 * 	<houses>
 * 		<house key="bj">house</house>
 * 		<house key="gz">house2</house>
 * 	</houses>
 * </user>
 */

public class XStreamTest extends Assert {

	private XmlBinder binder;

	@Before
	public void setUp() {
		binder = new XStreamBinder(User.class, Role.class);
	}

	@Test
	public void objectToXml() throws DocumentException {
		User user = new User();
		user.setId(1L);
		user.setName("calvin");
		user.setPassword("calvin");

		user.getRoles().add(new Role(1L, "admin"));
		user.getRoles().add(new Role(2L, "user"));
		user.getInterests().add("movie");
		user.getInterests().add("sports");

		user.getHouses().put("bj", "house1");
		user.getHouses().put("gz", "house2");

		String xml = binder.toXml(user);
		System.out.println("XStream Object to Xml result:\n" + xml);
		assertXmlByDom4j(xml);
	}

	@Test
	public void xmlToObject() {
		String xml = generateXmlByDom4j();
		User user = binder.fromXml(xml);

		System.out.println("XStream Xml to Object result:" + user);

		assertEquals(Long.valueOf(1L), user.getId());
		assertEquals(2, user.getRoles().size());
		assertEquals("admin", user.getRoles().get(0).getName());

		assertEquals(2, user.getInterests().size());
		assertEquals("movie", user.getInterests().get(0));

		assertEquals(2, user.getHouses().size());
		assertEquals("house", user.getHouses().get("bj"));
	}

	@Test
	public void listAsRoot() {
		User user1 = new User();
		user1.setId(1L);
		user1.setName("calvin");

		User user2 = new User();
		user2.setId(2L);
		user2.setName("kate");

		List userList = new ArrayList();
		userList.add(user1);
		userList.add(user2);

		String xml = binder.toXml(userList);
		System.out.println("XStream Object List to Xml result:\n" + xml);
	}

	private String generateXmlByDom4j() {
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("user").addAttribute("id", "1");
		root.addElement("name").addText("calvin");

		//List<Role>
		Element roles = root.addElement("roles");
		roles.addElement("role").addAttribute("id", "1").addAttribute("name", "admin");
		roles.addElement("role").addAttribute("id", "2").addAttribute("name", "user");

		//List<String>
		root.addElement("interest").addText("movie");
		root.addElement("interest").addText("sports");

		//Map<String,String>
		Element houses = root.addElement("houses");
		houses.addElement("house").addAttribute("key", "bj").addText("house");
		houses.addElement("house").addAttribute("key", "gz").addText("house2");

		return document.asXML();
	}

	private void assertXmlByDom4j(String xml) throws DocumentException {
		Document doc = DocumentHelper.parseText(xml);
		Element user = doc.getRootElement();
		assertEquals("1", user.attribute("id").getValue());

		Element adminRole = (Element) doc.selectSingleNode("//roles/role[@id=1]");
		assertEquals(2, adminRole.getParent().elements().size());
		assertEquals("admin", adminRole.attribute("name").getValue());

		List<Element> interests = doc.selectNodes("//interest");
		assertEquals(2, interests.size());
		assertEquals("movie", interests.get(0).getText());

		Element house1 = (Element) doc.selectSingleNode("//houses/house[@key='bj']");
		assertEquals("house1", house1.getText());
	}

}
