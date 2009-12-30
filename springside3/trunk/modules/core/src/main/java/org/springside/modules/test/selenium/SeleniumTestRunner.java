/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.selenium;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.notification.RunNotifier;
import org.springside.modules.test.groups.GroupsTestRunner;

/**
 * Selenium的Runner, 支持在Assert出错时截图, 并支持TestNG Groups式分组执行用例.
 * 
 * 注意, 本类只适用于JUnit 4.4版本.
 * 
 * @author calvin
 */
public class SeleniumTestRunner extends GroupsTestRunner {

	public SeleniumTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
	}

	/**
	 * 重载加入Assert出错时截图的Listener.
	 */
	@Override
	public void run(RunNotifier notifier) {
		notifier.addFirstListener(new SeleniumTestListener());
		super.run(notifier);
	}
}
