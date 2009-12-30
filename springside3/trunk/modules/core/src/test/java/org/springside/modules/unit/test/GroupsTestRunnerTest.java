package org.springside.modules.unit.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.runner.RunWith;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.utils.ReflectionUtils;

@RunWith(GroupsTestRunner.class)
public class GroupsTestRunnerTest extends Assert {

	@Test
	@SuppressWarnings("unchecked")
	public void groupsInit() throws InitializationError {
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);

		//从application-test.properties中取出test.groups值, 为MAJOR
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);
		assertEquals("MAJOR", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));

		//设置系统变量值, 覆盖application-test.properties中的值, 为MINI,MAJOR	
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "MINI,MAJOR");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);
		assertEquals("MINI", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(0));
		assertEquals("MAJOR", ((List<String>) ReflectionUtils.getFieldValue(groupsTestRunner, "groups")).get(1));
	}

	@Test
	public void isTestClassInGroups() throws InitializationError {
		//设置groups为MINI,MAJOR
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "MINI,MAJOR");
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
		//设置groups为为MINI,MAJOR
		GroupsTestRunner groupsTestRunner = new GroupsTestRunner(GroupsTestRunnerTest.class);
		System.setProperty(GroupsTestRunner.PROPERTY_NAME, "MINI,MAJOR");
		ReflectionUtils.invokeMethod(groupsTestRunner, "initGroups", null, null);

		assertEquals(true, GroupsTestRunner.isTestMethodInGroups(TestMethodBean1.class.getMethods()[0]));
		assertEquals(true, GroupsTestRunner.isTestMethodInGroups(TestMethodBean2.class.getMethods()[0]));
		assertEquals(false, GroupsTestRunner.isTestMethodInGroups(TestMethodBean3.class.getMethods()[0]));
	}

	@Test
	@Groups("foo")
	public void shouldNeverRun() {
		fail("the method in strange group should never run");
	}

	@Groups("MAJOR")
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
		@Groups("MAJOR")
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
		@Groups("MINI")
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
