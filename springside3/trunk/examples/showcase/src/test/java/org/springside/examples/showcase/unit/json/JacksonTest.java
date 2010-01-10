package org.springside.examples.showcase.unit.json;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 测试Jackson对Object,Map,List,数组等的持久化.
 * 
 * @author calvin
 */
public class JacksonTest extends Assert {

	private ObjectMapper mapper = new ObjectMapper();

	@Test
	public void writeObject() throws Exception {
		TestBean bean = new TestBean("A");
		String beanString = mapper.writeValueAsString(bean);
		System.out.println("Bean:" + beanString);

		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("name", "A");
		map.put("age", 2);
		String mapString = mapper.writeValueAsString(map);
		System.out.println("Map:" + mapString);

		List<String> stringList = Lists.newArrayList("A", "B", "C");
		String listString = mapper.writeValueAsString(stringList);
		System.out.println("String List:" + listString);

		List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
		String beanListString = mapper.writeValueAsString(beanList);
		System.out.println("Bean List:" + beanListString);

		TestBean[] beanArray = new TestBean[] { new TestBean("A"), new TestBean("B") };
		String beanArrayString = mapper.writeValueAsString(beanArray);
		System.out.println("Array List:" + beanArrayString);
	}

	@Test
	public void readObject() throws Exception {
		String beanString = "{\"name\":\"A\"}";
		TestBean bean = mapper.readValue(beanString, TestBean.class);
		System.out.println("Bean:" + bean);

		String mapString = "{\"name\":\"A\",\"age\":2}";
		Map<String, Object> map = mapper.readValue(mapString, new TypeReference<Map<String, Object>>() {
		});
		System.out.println("Map:");
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		String listString = "[\"A\",\"B\",\"C\"]";
		List<String> stringList = mapper.readValue(listString, new TypeReference<List<String>>() {
		});
		System.out.println("List:");
		for (String element : stringList) {
			System.out.println(element);
		}

		String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
		List<TestBean> beanList = mapper.readValue(beanListString, new TypeReference<List<TestBean>>() {
		});
		System.out.println("List:");
		for (Object element : beanList) {
			System.out.println(element);
		}
	}

	private static class TestBean {
		private String name;

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

		@Override
		public String toString() {
			return "TestBean [name=" + name + "]";
		}
	}
}
