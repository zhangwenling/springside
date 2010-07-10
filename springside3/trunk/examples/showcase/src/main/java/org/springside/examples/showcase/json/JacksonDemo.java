package org.springside.examples.showcase.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.type.TypeReference;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 测试Jackson对Object,Map,List,数组等的持久化.
 * 
 * @author calvin
 */
public class JacksonDemo {

	private ObjectMapper mapper;

	public static void main(String[] args) throws Exception {
		JacksonDemo demo = new JacksonDemo();
		demo.initMapper();
		demo.writeData();
		demo.readData();
	}

	public void initMapper() {
		mapper = new ObjectMapper();

		//忽略等于Null的值,节省空间.
		//mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

		//忽略Default值木有变化的属性,更节省空间,用于接收方有相同的Class, 用于对象->字符串->对象的转换过程.
		mapper.getSerializationConfig().setSerializationInclusion(JsonSerialize.Inclusion.NON_DEFAULT);
	}

	public void writeData() throws Exception {
		//Bean
		TestBean bean = new TestBean("A");
		String beanString = mapper.writeValueAsString(bean);
		System.out.println("Bean:" + beanString);

		//Map
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("name", "A");
		map.put("age", 2);
		String mapString = mapper.writeValueAsString(map);
		System.out.println("Map:" + mapString);

		//List<String>
		List<String> stringList = Lists.newArrayList("A", "B", "C");
		String listString = mapper.writeValueAsString(stringList);
		System.out.println("String List:" + listString);

		//List<Bean>
		List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
		String beanListString = mapper.writeValueAsString(beanList);
		System.out.println("Bean List:" + beanListString);

		//Bean[]
		TestBean[] beanArray = new TestBean[] { new TestBean("A"), new TestBean("B") };
		String beanArrayString = mapper.writeValueAsString(beanArray);
		System.out.println("Array List:" + beanArrayString);
	}

	@SuppressWarnings("unchecked")
	public void readData() throws Exception {
		//Bean
		String beanString = "{\"name\":\"A\"}";
		TestBean bean = mapper.readValue(beanString, TestBean.class);
		System.out.println("Bean:" + bean);

		//Map
		String mapString = "{\"name\":\"A\",\"age\":2}";
		Map<String, Object> map = mapper.readValue(mapString, HashMap.class);
		System.out.println("Map:");
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		//List<String>
		String listString = "[\"A\",\"B\",\"C\"]";
		List<String> stringList = mapper.readValue(listString, new TypeReference<List<String>>() {
		});
		System.out.println("List:");
		for (String element : stringList) {
			System.out.println(element);
		}

		//List<Bean>
		String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
		List<TestBean> beanList = mapper.readValue(beanListString, new TypeReference<List<TestBean>>() {
		});
		System.out.println("List:");
		for (Object element : beanList) {
			System.out.println(element);
		}
	}

	public static class TestBean {
		private String name;
		private String defaultValue = "hello";
		private String nullValue = null;

		public TestBean() {
		}

		public TestBean(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDefaultValue() {
			return defaultValue;
		}

		public void setDefaultValue(String defaultValue) {
			this.defaultValue = defaultValue;
		}

		public String getNullValue() {
			return nullValue;
		}

		public void setNullValue(String nullValue) {
			this.nullValue = nullValue;
		}

		@Override
		public String toString() {
			return "TestBean [defaultValue=" + defaultValue + ", name=" + name + ", nullValue=" + nullValue + "]";
		}
	}
}
