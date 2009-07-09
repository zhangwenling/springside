package org.springside.modules.test.selenium;

import java.io.FileWriter;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.thoughtworks.selenium.Selenium;

public class SeleniumTestListener extends RunListener {

	@Override
	public void testFailure(Failure failure) {
		Selenium selenium = SeleniumTestCase.selenium;
		String filePathPrefix = System.getProperty("java.io.tmpdir") + failure.getDescription().getDisplayName();
		String imgFilePath = filePathPrefix + ".png";
		String htmFilePath = filePathPrefix + ".htm";
		try {
			selenium.captureScreenshot(imgFilePath);
			System.err.println("Saved screenshot " + imgFilePath);

		} catch (Exception e) {
			System.err.println("Couldn't save screenshot " + imgFilePath + ": " + e.getMessage());
		}

		try {
			String html = selenium.getHtmlSource();
			FileWriter writer = new FileWriter(htmFilePath);
			writer.write(html);
			writer.close();

			System.err.println("Saved html " + htmFilePath);

		} catch (Exception e) {
			System.err.println("Couldn't save html " + htmFilePath + ": " + e.getMessage());
		}
	}

}
