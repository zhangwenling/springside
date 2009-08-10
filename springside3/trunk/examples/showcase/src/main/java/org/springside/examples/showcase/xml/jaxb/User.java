package org.springside.examples.showcase.xml.jaxb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 使用JAXB2.0标注的待转换Java Bean.
 */
//根节点
@XmlRootElement
//指定子节点的顺序
@XmlType(propOrder = { "name", "email", "roles", "interests", "houses" })
public class User {

	private Long id;
	private String name;
	private String password;
	private String email;

	private List<Role> roles = new ArrayList<Role>();
	private List<String> interests = new ArrayList<String>();
	private Map<String, String> houses = new HashMap<String, String>();

	//设置转换为xml节点中的属性
	@XmlAttribute
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

	//设置不转换为xml
	@XmlTransient
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//设置xml为<roles><role id="1"/><role id="2"/></roles>
	@XmlElementWrapper(name = "roles")
	@XmlElement(name = "role")
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<String> getInterests() {
		return interests;
	}

	@XmlElementWrapper(name = "interests")
	@XmlElement(name = "interest")
	public void setInterests(List<String> interests) {
		this.interests = interests;
	}

	//设置对Map的转换
	@XmlJavaTypeAdapter(MapAdapter.class)
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

	public static class MapAdapter extends XmlAdapter<MapEntry[], Map<String, String>> {

		@Override
		public MapEntry[] marshal(Map<String, String> map) throws Exception {
			List<MapEntry> list = new ArrayList<MapEntry>();
			for (Map.Entry<String, String> e : map.entrySet()) {
				list.add(new MapEntry(e));
			}
			return list.toArray(new MapEntry[list.size()]);
		}

		@Override
		public Map<String, String> unmarshal(MapEntry[] array) throws Exception {
			Map<String, String> map = new HashMap<String, String>();
			for (MapEntry e : array) {
				map.put(e.key, e.value);
			}
			return map;
		}
	}

	public static class MapEntry {
		@XmlAttribute
		public String key;

		public String value;

		public MapEntry() {
		}

		public MapEntry(Map.Entry<String, String> e) {
			key = e.getKey();
			value = e.getValue();
		}
	}
}
