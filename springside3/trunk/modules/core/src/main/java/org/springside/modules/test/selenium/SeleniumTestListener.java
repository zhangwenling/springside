package org.springside.modules.test.selenium;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.thoughtworks.selenium.Selenium;

/**
 * Selenium在Assert出错时屏幕截图的Listener.
 * 
 * @author calvin
 */
public class SeleniumTestListener extends RunListener {

	@Override
	public void testFailure(Failure failure) {
		Selenium selenium = SeleniumTestCase.getSelenium();
		String filePathPrefix = System.getProperty("java.io.tmpdir") + failure.getDescription().getDisplayName();
		String imgFilePath = filePathPrefix + ".png";
		try {
			selenium.captureScreenshot(imgFilePath);
			System.err.println("Saved screenshot " + imgFilePath);

		} catch (Exception e) {
			System.err.println("Couldn't save screenshot " + imgFilePath + ": " + e.getMessage());
		}
	}

}
