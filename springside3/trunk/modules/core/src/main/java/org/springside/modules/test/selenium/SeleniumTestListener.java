/**
 * Copyright (c) 2005-2009 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.selenium;

import java.io.File;

import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

import com.thoughtworks.selenium.Selenium;

/**
 * Selenium在Assert出错时进行屏幕截图的Listener.
 * 
 * @author calvin
 */
public class SeleniumTestListener extends RunListener {

	@Override
	public void testFailure(Failure failure) {
		Selenium selenium = SeleniumTestCase.getSelenium();
		String imgFilePath = System.getProperty("java.io.tmpdir") + File.separator
				+ failure.getDescription().getDisplayName() + ".png";
		try {
			selenium.captureScreenshot(imgFilePath);
			System.err.println("Saved screenshot " + imgFilePath);
		} catch (Exception e) {
			System.err.println("Couldn't save screenshot " + imgFilePath + ": " + e.getMessage());
		}
	}
}
