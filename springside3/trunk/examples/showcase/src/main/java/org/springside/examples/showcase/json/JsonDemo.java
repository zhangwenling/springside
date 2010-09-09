package org.springside.examples.showcase.json;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.binder.JsonBinder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 测试Jackson对Object,Map,List,数组等的持久化.
 * 
 * @author calvin
 */
public class JsonDemo extends Assert {

	private static JsonBinder binder = JsonBinder.buildNonDefaultBinder();

	public static void main(String[] args) {
		try {
			JsonDemo demo = new JsonDemo();
			demo.toJson();
			demo.fromJson();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void toJson() throws Exception {
		//Bean
		TestBean bean = new TestBean("A");
		String beanString = binder.toJson(bean);
		System.out.println("Bean:" + beanString);
		assertEquals("{\"name\":\"A\"}", beanString);

		//Map
		Map<String, Object> map = Maps.newLinkedHashMap();
		map.put("name", "A");
		map.put("age", 2);
		String mapString = binder.toJson(map);
		System.out.println("Map:" + mapString);
		assertEquals("{\"name\":\"A\",\"age\":2}", mapString);

		//List<String>
		List<String> stringList = Lists.newArrayList("A", "B", "C");
		String listString = binder.toJson(stringList);
		System.out.println("String List:" + listString);
		assertEquals("[\"A\",\"B\",\"C\"]", listString);

		//List<Bean>
		List<TestBean> beanList = Lists.newArrayList(new TestBean("A"), new TestBean("B"));
		String beanListString = binder.toJson(beanList);
		System.out.println("Bean List:" + beanListString);
		assertEquals("[{\"name\":\"A\"},{\"name\":\"B\"}]", beanListString);

		//Bean[]
		TestBean[] beanArray = new TestBean[] { new TestBean("A"), new TestBean("B") };
		String beanArrayString = binder.toJson(beanArray);
		System.out.println("Array List:" + beanArrayString);
		assertEquals("[{\"name\":\"A\"},{\"name\":\"B\"}]", beanArrayString);

		//Null Bean
		TestBean nullBean = null;
		String nullBeanString = binder.toJson(nullBean);
		assertNull(nullBeanString);

		//Empty List
		List<String> emptyList = Lists.newArrayList();
		String emptyListString = binder.toJson(emptyList);
		assertEquals("[]", emptyListString);
	}

	@Test
	@SuppressWarnings("unchecked")
	public void fromJson() throws Exception {
		//Bean
		String beanString = "{\"name\":\"A\"}";
		TestBean bean = binder.fromJson(beanString, TestBean.class);
		System.out.println("Bean:" + bean);

		//Map
		String mapString = "{\"name\":\"A\",\"age\":2}";
		Map<String, Object> map = binder.fromJson(mapString, HashMap.class);
		System.out.println("Map:");
		for (Entry<String, Object> entry : map.entrySet()) {
			System.out.println(entry.getKey() + " " + entry.getValue());
		}

		//List<String>
		String listString = "[\"A\",\"B\",\"C\"]";
		List<String> stringList = binder.getMapper().readValue(listString, List.class);
		System.out.println("String List:");
		for (String element : stringList) {
			System.out.println(element);
		}

		//List<Bean>
		String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
		List<TestBean> beanList = binder.getMapper().readValue(beanListString, new TypeReference<List<TestBean>>() {
		});
		System.out.println("Bean List:");
		for (TestBean element : beanList) {
			System.out.println(element);
		}
	}

	//此annoation为了截断对象的循环引用.
	@JsonIgnoreProperties( { "parent" })
	public static class TestBean {

		private String name;
		private String defaultValue = "hello";
		private String nullValue = null;
		private TestBean parent;

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

		public TestBean getParent() {
			return parent;
		}

		public void setParent(TestBean parent) {
			this.parent = parent;
		}

		@Override
		public String toString() {
			return "TestBean [defaultValue=" + defaultValue + ", name=" + name + ", nullValue=" + nullValue + "]";
		}
	}
}
