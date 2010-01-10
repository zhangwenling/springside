package org.springside.examples.showcase.unit.json;

import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Lists;
/**
 * 测试Jackson对对象,List,数组等的持久化.
 * 
 * @author calvin
 */
public class JacksonTest extends Assert{
	
	private ObjectMapper mapper = new ObjectMapper();
	
	@Test
	public void writeObject() throws Exception{
		List<String> stringList=Lists.newArrayList("A","B","C");
		String listString = mapper.writeValueAsString(stringList);
		System.out.println(listString);
		
		List<TestBean> beanList = Lists.newArrayList(new TestBean("A"),new TestBean("B"));
		String beanListString = mapper.writeValueAsString(beanList);
		System.out.println(beanListString);
		
		TestBean[] beanArray= new TestBean[]{new TestBean("A"),new TestBean("B")};
		String beanArrayString = mapper.writeValueAsString(beanArray);
		System.out.println(beanArrayString);
		
		String beanString = mapper.writeValueAsString(new TestBean("A"));
		System.out.println(beanString);
	}
	
	@Test
	public void readObject() throws Exception{
		String listString ="[\"A\",\"B\",\"C\"]";
		
		List<String> stringList = mapper.readValue(listString,new TypeReference<List<String>>() { });
		for(String element:stringList){
			System.out.println(element);
		}
		
		String beanListString = "[{\"name\":\"A\"},{\"name\":\"B\"}]";
		List<TestBean> beanList = mapper.readValue(beanListString,new TypeReference<List<TestBean>>() { });
		for(Object element:beanList){
			System.out.println(element);
		}
	}

	private static class TestBean{
		private String name;
		
		public TestBean(){		
		}
		
		public TestBean(String name){
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
