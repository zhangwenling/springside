package org.springside.modules.unit.utils.mapper;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springside.modules.unit.utils.ReflectionUtilsTest.TestBean3;
import org.springside.modules.utils.mapper.ConvertUtils;

import com.google.common.collect.Lists;

public class CovertUtilsTest {

	
	@Test
	public void convertElementPropertyToString() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = Lists.newArrayList(bean1, bean2);

		assertEquals("1,2", ConvertUtils.extractElementPropertyToString(list, "id", ","));
	}

	@Test
	
	
	public void convertElementPropertyToList() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = Lists.newArrayList(bean1, bean2);
		List<String> result = ConvertUtils.extractElementPropertyToList(list, "id");
		assertEquals(2, result.size());
		assertEquals(1, result.get(0));
	}

	@Test
	public void convertStringToObject() {
		assertEquals(1, ConvertUtils.convertStringToObject("1", Integer.class));

		Date date = (Date) ConvertUtils.convertStringToObject("2010-06-01", Date.class);
		assertEquals(2010, new DateTime(date).getYear());

		Date dateTime = (Date) ConvertUtils.convertStringToObject("2010-06-01 12:00:04", Date.class);
		assertEquals(12, new DateTime(dateTime).getHourOfDay());
	}
}
