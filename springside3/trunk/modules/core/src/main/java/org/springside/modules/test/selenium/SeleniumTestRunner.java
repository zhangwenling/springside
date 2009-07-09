package org.springside.modules.test.selenium;

import java.lang.reflect.Method;

import org.junit.internal.runners.InitializationError;
import org.junit.internal.runners.JUnit4ClassRunner;
import org.junit.runner.notification.RunNotifier;
import org.springside.modules.test.groups.GroupsUtils;

public class SeleniumTestRunner extends JUnit4ClassRunner {

	SeleniumTestListener listener;

	public SeleniumTestRunner(Class<?> klass) throws InitializationError {
		super(klass);
		listener = new SeleniumTestListener();
	}

	@Override
	public void run(RunNotifier notifier) {
		if (!GroupsUtils.isTestClassInGroups(getTestClass().getJavaClass())) {
			notifier.fireTestIgnored(getDescription());
			return;
		}

		notifier.addFirstListener(listener);

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
