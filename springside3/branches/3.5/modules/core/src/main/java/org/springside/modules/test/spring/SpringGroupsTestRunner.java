/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SpringGroupsTestRunner.java 667 2009-11-25 15:25:36Z calvinxiu $
 */
package org.springside.modules.test.spring;

import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springside.modules.test.groups.GroupsTestRunner;

/**
 * 实现TestNG Groups功能的Runner,继承于Spring原有的SpringJUnit4ClassRunner.
 * 
 * 使用方法:在TestCase类定义处添加@RunWith(SpringGroupsTestRunner.class)
 * 
 * @author freeman
 * @author calvin
 */
public class SpringGroupsTestRunner extends SpringJUnit4ClassRunner {

	public SpringGroupsTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * 重载加入Class级别过滤.
	 */
	@Override
	public void run(RunNotifier notifier) {
		if (!GroupsTestRunner.shouldRun(getTestClass().getJavaClass())) {
			EachTestNotifier testNotifier = new EachTestNotifier(notifier, getDescription());
			testNotifier.fireTestIgnored();
		}
		super.run(notifier);
	}

	/**
	 * 重载加入方法级别过滤.
	 */
	@Override
	protected void runChild(FrameworkMethod method, RunNotifier notifier) {

		if (!GroupsTestRunner.shouldRun(method.getMethod())) {
			Description description = describeChild(method);
			EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
			eachNotifier.fireTestIgnored();
			return;
		}
		super.runChild(method, notifier);
	}
}
