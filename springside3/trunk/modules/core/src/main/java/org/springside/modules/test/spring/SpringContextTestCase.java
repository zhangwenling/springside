/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.spring;

import org.junit.Assert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.unitils.reflectionassert.ReflectionAssert;
import org.unitils.reflectionassert.ReflectionComparatorMode;

/**
 * Spring的支持依赖注入的JUnit 4 TestCase基类简写.
 * 
 * @author calvin
 */
//默认载入applicationContext-test.xml,子类中的@ContextConfiguration定义将合并父类的定义.
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class SpringContextTestCase extends AbstractJUnit4SpringContextTests {

	// Assert 函数 //

	/**
	 * 反射比较对象间的所有属性,忽略expected对象的Null对象和集合中对象的次序.
	 */
	protected void assertReflectionEquals(Object expected, Object actual) {
		ReflectionAssert.assertReflectionEquals(expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS,
				ReflectionComparatorMode.LENIENT_ORDER);
	}

	/**
	 * @see #assertReflectionEquals(Object, Object)
	 */
	protected void assertReflectionEquals(String message, Object expected, Object actual) {
		ReflectionAssert.assertReflectionEquals(message, expected, actual, ReflectionComparatorMode.IGNORE_DEFAULTS,
				ReflectionComparatorMode.LENIENT_ORDER);
	}

	protected void assertEquals(Object expected, Object actual) {
		Assert.assertEquals(expected, actual);
	}

	protected void assertEquals(String message, Object expected, Object actual) {
		Assert.assertEquals(message, expected, actual);
	}

	protected void assertTrue(boolean condition) {
		Assert.assertTrue(condition);
	}

	protected void assertTrue(String message, boolean condition) {
		Assert.assertTrue(message, condition);
	}

	protected void assertFalse(boolean condition) {
		Assert.assertFalse(condition);
	}

	protected void assertFalse(String message, boolean condition) {
		Assert.assertFalse(message, condition);
	}

	protected void assertNull(Object object) {
		Assert.assertNull(object);
	}

	protected void assertNull(String message, Object object) {
		Assert.assertNull(message, object);
	}

	protected void assertNotNull(Object object) {
		Assert.assertNotNull(object);
	}

	protected void assertNotNull(String message, Object object) {
		Assert.assertNotNull(message, object);
	}
}
