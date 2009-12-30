package org.springside.modules.unit.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.model.InitializationError;
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

		//清理设置
		ReflectionUtils.setFieldValue(groupsTestRunner, "groups", null);
	}

	@Test
	public void isTestClassShouldRun() throws InitializationError {

		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean1.class));
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean2.class));
		assertEquals(false, GroupsTestRunner.shouldRun(TestClassBean3.class));

	}

	@Test
	public void isTestMethodShouldRun() throws InitializationError {

		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean1.class.getMethods()[0]));
		assertEquals(true, GroupsTestRunner.shouldRun(TestClassBean2.class.getMethods()[0]));
		assertEquals(false, GroupsTestRunner.shouldRun(TestClassBean3.class.getMethods()[0]));

	}

	@Test
	@Groups("foo")
	public void shouldNeverRun() {
		fail("the method in strange group should never run");
	}

	@Test
	@Groups("MAJOR")
	public void shouldRun() {
	}

	@RunWith(GroupsTestRunner.class)
	static class TestClassBean1 {
		@Test
		@Groups("MAJOR")
		public void shouldRun() {
		}

		@Test
		@Groups("foo")
		public void shouldNeverRun() {
			fail("the method in foo group should never run");
		}
	}

	@RunWith(GroupsTestRunner.class)
	static class TestClassBean2 {
		@Test
		public void shouldRun() {
		}
	}

	@RunWith(GroupsTestRunner.class)
	static class TestClassBean3 {
		@BeforeClass
		public static void shuoldNeverRunBeforeClass() {
			fail("the method in foo group should never run");
		}

		@Before
		public void shuoldNeverRunBefore() {
			fail("the method in foo group should never run");
		}

		@Test
		@Groups("foo")
		public void shouldNeverRun() {
			fail("the method in foo group should never run");
		}
	}
}
