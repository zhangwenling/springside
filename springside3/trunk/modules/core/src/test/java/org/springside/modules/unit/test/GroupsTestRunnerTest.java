package org.springside.modules.unit.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.utils.ReflectionUtils;

public class GroupsTestRunnerTest extends Assert {

	@SuppressWarnings("unchecked")
	@Test
	public void groupsInit() throws InitializationError {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);

		//从application-test.properties中取出test.groups值
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);
		assertEquals("extension", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));

		//设置系统变量值, 覆盖application-test.properties中的值		
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "base,exception");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);
		assertEquals("base", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));
		assertEquals("exception", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(1));
	}

	@Test
	public void isTestClassInGroups() throws InitializationError {
		//设置groups为base,exception
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "base,exception");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);

		assertEquals(true, GroupsTestRunner.isTestClassInGroups(TestClassBean1.class));
		assertEquals(true, GroupsTestRunner.isTestClassInGroups(TestClassBean2.class));
		assertEquals(true, GroupsTestRunner.isTestClassInGroups(TestClassBean3.class));
		assertEquals(false, GroupsTestRunner.isTestClassInGroups(TestClassBean4.class));
		assertEquals(false, GroupsTestRunner.isTestClassInGroups(TestClassBean5.class));
		assertEquals(false, GroupsTestRunner.isTestClassInGroups(TestClassBean6.class));
	}

	@Test
	public void isTestMethodInGroups() throws InitializationError {
		//设置groups为base,exception
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "base,exception");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);

		assertEquals(true, GroupsTestRunner.isTestMethodInGroups(TestMethodBean1.class.getMethods()[0]));
		assertEquals(true, GroupsTestRunner.isTestMethodInGroups(TestMethodBean2.class.getMethods()[0]));
		assertEquals(false, GroupsTestRunner.isTestMethodInGroups(TestMethodBean3.class.getMethods()[0]));
	}

	@Groups("exception")
	public static class TestClassBean1 {
		@Test
		public void test() {
		}
	}

	public static class TestClassBean2 {
		@Test
		public void test() {
		}
	}

	public static class TestClassBean3 {
		public void foo() {
		}

		@Test
		@Groups("exception")
		public void test() {
		}
	}

	@Groups("foo")
	public static class TestClassBean4 {
		@Test
		public void test() {
		}
	}

	public static class TestClassBean5 {
		@Test
		@Groups("foo")
		public void test() {
		}
	}

	public static class TestClassBean6 {
		public void foo() {
		}
	}

	public static class TestMethodBean1 {
		@Test
		@Groups("base")
		public void test() {
		}
	}

	public static class TestMethodBean2 {
		@Test
		public void test() {
		}
	}

	public static class TestMethodBean3 {
		@Test
		@Groups("foo")
		public void test() {
		}
	}
}
