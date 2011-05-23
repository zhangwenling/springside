package org.springside.modules.unit.utils;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.springside.modules.mapper.CollectionMapper;
import org.springside.modules.unit.utils.ReflectionUtilsTest.TestBean3;

import com.google.common.collect.Lists;

public class CollecitonUtilsTest {

	@Test
	public void convertElementPropertyToString() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = Lists.newArrayList(bean1, bean2);

		assertEquals("1,2", CollectionMapper.extractToString(list, "id", ","));
	}

	@Test
	public void convertElementPropertyToList() {
		TestBean3 bean1 = new TestBean3();
		bean1.setId(1);
		TestBean3 bean2 = new TestBean3();
		bean2.setId(2);

		List list = Lists.newArrayList(bean1, bean2);
		List<String> result = CollectionMapper.extractToList(list, "id");
		assertEquals(2, result.size());
		assertEquals(1, result.get(0));
	}

}
