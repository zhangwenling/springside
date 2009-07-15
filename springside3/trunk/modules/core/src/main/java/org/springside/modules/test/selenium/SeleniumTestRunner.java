package org.springside.modules.test.selenium;

import java.lang.reflect.Method;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.springside.modules.test.groups.Groups;
import org.springside.modules.test.groups.GroupsUtils;

/**
 * Selenium的Runner支持TestNG Groups分组执行用例并在Assert出错时截图.
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

	@Override
	public void run(RunNotifier notifier) {
		if (!GroupsUtils.isTestClassInGroups(getTestClass().getJavaClass())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		notifier.addFirstListener(new SeleniumTestListener());

		super.run(notifier);
	}

	@Override
	protected void invokeTestMethod(Method method, RunNotifier notifier) {

		if (!GroupsUtils.isTestMethodInGroups(method)) {
			notifier.fireTestIgnored(getDescription());
			return;
		}
		super.invokeTestMethod(method, notifier);
	}
}
