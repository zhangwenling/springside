/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: GroupsUtils.java 516 2009-10-02 13:55:33Z calvinxiu $
 */
package org.springside.modules.test.groups;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 实现TestNG Groups分组执行用例功能的TestRunner函数.
 * 
 * Runner会只执行测试类的@Groups定义，与在-Dtest.groups=xxx 或 application.test.properties的test.groups=xxx相吻合的测试类及测试方法.
 * 另提供独立判断的工具方法供其他的Runner调用.
 *  
 * @author freeman
 * @author calvin
 */
public class GroupsTestRunner extends JUnit4ClassRunner {

	/** 在Properties文件或JVM参数-D中定义执行分组的变量名称. */
	public static final String PROPERTY_NAME = "test.groups";
	/** 定义了分组变量的Properties文件名. */
	public static final String PROPERTY_FILE = "application.test.properties";

	private static Logger logger = LoggerFactory.getLogger(GroupsTestRunner.class);

	private static List<String> groups;

	public GroupsTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * 重载加入Class级别控制.
	 */
	@Override
	public void run(RunNotifier notifier) {
		if (!isTestClassInGroups(getTestClass().getJavaClass())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		super.run(notifier);
	}

	/**
	 * 重载加入方法级别控制.
	 */
	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {
		if (!isTestMethodInGroups(method)) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		super.invokeTestMethod(method, notifier);
	}

	/**
	 * 判断测试类是否符合分组要求.
	 * 如果@Groups符合定义或无@Groups定义，且至少含有一个符合定义的测试方法时返回true.
	 */
	public static boolean isTestClassInGroups(Class<?> testClass) {
		//初始化Groups定义
		if (groups == null) {
			initGroups();
		}
		//如果定义全部执行则返回true
		if (groups.contains(Groups.ALL))
			return true;

		//取得类上的Groups annotation, 如果有Groups注解且注解不符合分组要求则返回false.
		Groups annotationGroup = testClass.getAnnotation(Groups.class);
		if ((annotationGroup != null) && !groups.contains(annotationGroup.value()))
			return false;

		//继续检查测试方法是否符合Groups定义,如果有一个方法符合定义则返回true.
		Method[] methods = testClass.getMethods();
		for (Method method : methods) {
			if (method.getAnnotation(Test.class) != null && method.getAnnotation(Ignore.class) == null
					&& isTestMethodInGroups(method))
				return true;
		}

		return false;
	}

	/**
	 * 判断测试方法是否符合分组要求.
	 * 如果@Groups符合定义或无@Groups定义返回true.
	 */
	public static boolean isTestMethodInGroups(Method testMethod) {
		//初始化Groups定义
		if (groups == null) {
			initGroups();
		}
		//如果groups定义为全部执行则返回true
		if (groups.contains(Groups.ALL))
			return true;

		//取得方法上的Groups annotation, 如果无Groups注解或注解符合分组要求则返回true.
		Groups annotationGroup = testMethod.getAnnotation(Groups.class);
		if ((annotationGroup == null) || groups.contains(annotationGroup.value()))
			return true;

		return false;
	}

	/**
	 * 从系统变量或Properties文件初始化运行的groups.
	 * 如果均无定义则返回ALL.
	 */
	protected static void initGroups() {

		String groupsDef = getGroupsFromSystemProperty();

		//如果环境变量未定义test.groups,尝试从property文件读取.
		if (groupsDef == null) {
			groupsDef = getGroupsFromPropertyFile();
			//如果仍未定义,设为全部运行
			if (groupsDef == null) {
				groupsDef = Groups.ALL;
			}
		}

		groups = Arrays.asList(groupsDef.split(","));
	}

	/**
	 * 从环境变量读取test.groups定义, 多个group用逗号分隔.
	 * eg.
	 * java -Dtest.groups=basic,extension
	 */
	protected static String getGroupsFromSystemProperty() {
		return System.getProperty(PROPERTY_NAME);
	}

	/**
	 * 从Classpath中的application.test.properties文件读取test.groups定义, 多个group用逗号分隔.
	 */
	protected static String getGroupsFromPropertyFile() {
		Properties p;
		try {
			p = PropertiesLoaderUtils.loadAllProperties(PROPERTY_FILE);
			return p.getProperty(PROPERTY_NAME);
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
}
