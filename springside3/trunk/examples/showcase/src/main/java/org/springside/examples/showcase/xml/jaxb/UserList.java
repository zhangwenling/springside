package org.springside.examples.showcase.xml.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Root级别的List<User>的Wrapper类.
 * 
 * @author calvin
 */
@XmlRootElement(name = "userList")
public class UserList {

	private List<User> list = new ArrayList<User>();

	@XmlElement(name = "user")
	public List<User> getList() {
		return list;
	}
}
