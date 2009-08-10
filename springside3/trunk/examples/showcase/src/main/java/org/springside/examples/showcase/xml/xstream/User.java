package org.springside.examples.showcase.xml.xstream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.converters.collections.MapConverter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.mapper.Mapper;

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

	private String email;

	private List<Role> roles = new ArrayList<Role>();

	//设置xml为<interest/><interest/>
	@XStreamImplicit(itemFieldName = "interest")
	private List<String> interests = new ArrayList<String>();

	//设置对Map的转换
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	/**
	 * 将key转换为entry节点属性的Converter.
	 */
	public static class HouseMapConverter extends MapConverter {

		private static final String ENTRY_NAME = "house";

		private static final String KEY_NAME = "key";

		public HouseMapConverter(Mapper mapper) {
			super(mapper);
		}

		@Override
		public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
			Map<?, ?> map = (Map<?, ?>) source;
			for (Entry<?, ?> entry : map.entrySet()) {
				writer.startNode(ENTRY_NAME);
				writer.addAttribute(KEY_NAME, entry.getKey().toString());
				writer.setValue(entry.getValue().toString());
				writer.endNode();
			}
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void populateMap(HierarchicalStreamReader reader, UnmarshallingContext context, Map map) {
			while (reader.hasMoreChildren()) {
				reader.moveDown();
				Object key = reader.getAttribute(KEY_NAME);
				Object value = reader.getValue();
				reader.moveUp();
				map.put(key, value);
			}
		}
	}

}
