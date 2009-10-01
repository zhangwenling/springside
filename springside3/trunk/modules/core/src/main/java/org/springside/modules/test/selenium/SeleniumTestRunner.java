/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.selenium;

import java.lang.reflect.Method;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsUtils;

/**
 * Selenium的Runner支持TestNG Groups式分组执行用例并在Assert出错时截图.
 * 
 * @see Groups
 * @see SeleniumTestListener
 * 
 * @author calvin
 */
public class SeleniumTestRunner extends JUnit4ClassRunner {

	public SeleniumTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * 重载加入Class级别控制.
	 */
	@Override
	public void run(RunNotifier notifier) {
		//对Class是否需要执行测试进行Groups判定.
		if (!GroupsUtils.isTestClassInGroups(getTestClass().getJavaClass())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		//加入Assert出错时截图的Listener.
		notifier.addFirstListener(new SeleniumTestListener());

		super.run(notifier);
	}

	/**
	 * 重载加入方法级别控制.
	 */
	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {

		//对方法是否需要执行测试进行Groups判定.
		if (!GroupsUtils.isTestMethodInGroups(method)) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		super.invokeTestMethod(method, notifier);
	}
}
