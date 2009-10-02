package org.springside.modules.unit.orm;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.orm.PropertyFilter;
import org.springside.modules.orm.PropertyFilter.MatchType;

/**
 * PropertyFilter的测试类
 * 
 * @author calvin
 */
public class PropertyFilterTest extends Assert {

	@Test
	public void test() {
		//Boolean EQ filter
		PropertyFilter booleanEqFilter = new PropertyFilter("EQB_foo", "true");
		assertEquals(MatchType.EQ, booleanEqFilter.getMatchType());
		assertEquals(Boolean.class, booleanEqFilter.getPropertyType());
		assertEquals(true, booleanEqFilter.getPropertyValue());

		//Date LT filter
		PropertyFilter dateLtFilter = new PropertyFilter("LTD_foo", "2046-01-01");
		assertEquals(MatchType.LT, dateLtFilter.getMatchType());
		assertEquals(Date.class, dateLtFilter.getPropertyType());
		assertEquals("foo", dateLtFilter.getPropertyName());
		assertEquals(new Date(146, 0, 1), dateLtFilter.getPropertyValue());

		//Integer GT filter
		PropertyFilter intGtFilter = new PropertyFilter("GTI_foo", "123");
		assertEquals(MatchType.GT, intGtFilter.getMatchType());
		assertEquals(Integer.class, intGtFilter.getPropertyType());
		assertEquals("foo", intGtFilter.getPropertyName());
		assertEquals(123, intGtFilter.getPropertyValue());

		//Double LE filter
		PropertyFilter doubleLeFilter = new PropertyFilter("LEN_foo", "12.33");
		assertEquals(MatchType.LE, doubleLeFilter.getMatchType());
		assertEquals(Double.class, doubleLeFilter.getPropertyType());
		assertEquals("foo", doubleLeFilter.getPropertyName());
		assertEquals(12.33, doubleLeFilter.getPropertyValue());

		//Long GE filter
		PropertyFilter longGeFilter = new PropertyFilter("GEL_foo", "123456789");
		assertEquals(MatchType.GE, longGeFilter.getMatchType());
		assertEquals(Long.class, longGeFilter.getPropertyType());
		assertEquals("foo", longGeFilter.getPropertyName());
		assertEquals(123456789L, longGeFilter.getPropertyValue());

		//Like OR filter
		PropertyFilter likeOrFilter = new PropertyFilter("LIKES_foo_OR_bar", "hello");
		assertEquals(MatchType.LIKE, likeOrFilter.getMatchType());
		assertEquals(String.class, likeOrFilter.getPropertyType());
		assertArrayEquals(new String[] { "foo", "bar" }, likeOrFilter.getPropertyNames());
		assertEquals("hello", likeOrFilter.getPropertyValue());
	}

	@Test
	public void errorFilterName() throws Exception {
		int exceptionCount = 0;
		PropertyFilter filter = null;
		try {
			filter = new PropertyFilter("ABS_foo", "hello");
		} catch (IllegalArgumentException e) {
			assertEquals("filter名称ABS_foo没有按规则编写,无法得到属性比较类型.", e.getMessage());
			exceptionCount++;
		}

		try {
			filter = new PropertyFilter("GEX_foo", "hello");
		} catch (IllegalArgumentException e) {
			assertEquals("filter名称GEX_foo没有按规则编写,无法得到属性值类型.", e.getMessage());
			exceptionCount++;
		}

		try {
			filter = new PropertyFilter("EQS_", "hello");
		} catch (IllegalArgumentException e) {
			assertEquals("filter名称EQS_没有按规则编写,无法得到属性名称.", e.getMessage());
			exceptionCount++;
		}

		assertEquals(3, exceptionCount);
	}
}
