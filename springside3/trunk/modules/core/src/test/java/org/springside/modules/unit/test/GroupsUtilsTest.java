package org.springside.modules.unit.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsUtils;
import org.springside.modules.utils.ReflectionUtils;

public class GroupsUtilsTest extends Assert {

	@SuppressWarnings("unchecked")
	@Test
	public void groupsInit() {
		GroupsUtils groupsUtils = new GroupsUtils();

		//从application-test.properties中取出test.groups值
		ReflectionUtils.invokeMethod(groupsUtils, "initGroups", null, null);
		assertEquals("extension", ((List<String>) ReflectionUtils.getFieldValue(groupsUtils, "groups")).get(0));

		//设置系统值,覆盖application-test.properties中的值		
		System.setProperty(GroupsUtils.PROPERTY_NAME, "base,exception");
		ReflectionUtils.invokeMethod(groupsUtils, "initGroups", null, null);
		assertEquals("base", ((List<String>) ReflectionUtils.getFieldValue(groupsUtils, "groups")).get(0));
		assertEquals("exception", ((List<String>) ReflectionUtils.getFieldValue(groupsUtils, "groups")).get(1));
	}

	@Test
	public void isTestClassInGroups() {
		//设置groups为base,exception
		GroupsUtils groupsUtils = new GroupsUtils();
		System.setProperty(GroupsUtils.PROPERTY_NAME, "base,exception");
		ReflectionUtils.invokeMethod(groupsUtils, "initGroups", null, null);

		assertEquals(true, GroupsUtils.isTestClassInGroups(TestClassBean1.class));
		assertEquals(false, GroupsUtils.isTestClassInGroups(TestClassBean2.class));
		assertEquals(true, GroupsUtils.isTestClassInGroups(TestClassBean3.class));
	}

	@Test
	public void isTestMethodInGroups() {
		//设置groups为base,exception
		GroupsUtils groupsUtils = new GroupsUtils();
		System.setProperty(GroupsUtils.PROPERTY_NAME, "base,exception");
		ReflectionUtils.invokeMethod(groupsUtils, "initGroups", null, null);

		assertEquals(true, GroupsUtils.isTestMethodInGroups(TestClassBean1.class.getMethods()[0]));
		assertEquals(false, GroupsUtils.isTestMethodInGroups(TestClassBean2.class.getMethods()[0]));
		assertEquals(true, GroupsUtils.isTestMethodInGroups(TestClassBean3.class.getMethods()[0]));
	}

	@Groups("exception")
	public static class TestClassBean1 {
		@Groups("exception")
		public void test() {
		}

	}

	@Groups("foo")
	public static class TestClassBean2 {

		@Groups("foo")
		public void test() {
		}

	}

	public static class TestClassBean3 {
		public void test() {
		}

	}

}
