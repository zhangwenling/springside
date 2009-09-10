package org.springside.examples.showcase.xml.xstream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 使用XStream标注的待转换Java Bean.
 */
//指定User类的别名
@XStreamAlias("user")
public class User {

	//设置转换为xml节点属性
	@XStreamAsAttribute
	private Long id;

	private String name;

	//设置不转换为xml
	@XStreamOmitField
	private String password;

	//List<Object>的映射按默认, xml为<roles><role id="1" name="admin"/></roles>
	private List<Role> roles = new ArrayList<Role>();

	//设置List<String>的映射, xml为<interest>movie</interest><interest>sports</interest>
	@XStreamImplicit(itemFieldName = "interest")
	private List<String> interests = new ArrayList<String>();

	//设置对Map的映射, xml为<houses><house key="bj">house1</house></houses>
	@XStreamConverter(HouseMapConverter.class)
	private Map<String, String> houses = new HashMap<String, String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<String> getInterests() {
		return interests;
	}

	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	public Map<String, String> getHouses() {
		return houses;
	}

	public void setHouses(Map<String, String> houses) {
		this.houses = houses;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
