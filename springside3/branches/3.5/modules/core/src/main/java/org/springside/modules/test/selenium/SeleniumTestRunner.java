/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id: SeleniumTestRunner.java 671 2009-11-26 17:28:59Z calvinxiu $
 */
package org.springside.modules.test.selenium;

import org.junit.internal.runners.InitializationError;
import org.junit.runner.notification.RunNotifier;
import org.springside.modules.test.groups.GroupsTestRunner;

/**
 * Selenium的Runner, 支持在Assert出错时截图, 并支持TestNG Groups式分组执行用例.
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
