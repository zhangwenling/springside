/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.utils;

import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * Selenium工具类.
 *
 * @author calvin
 */
public abstract class SeleniumUtils {
	public enum BrowserType {
		firefox, ie, chrome, remote
	}

	private static Logger logger = LoggerFactory.getLogger(SeleniumUtils.class);

	/**
	 * 根据driverName创建各种WebDriver的简便方法.
	 * 
	 * 当持续集成服务器安装在非Windows机器上, 没有IE浏览器与XWindows时, 需要使用remote dirver调用远程的Windows机器.
	 * drivername如remote:192.168.0.2:3000:firefox, 此时要求远程服务器在http://192.168.0.2:3000/wd上启动selnium remote服务.
	 */
	public static WebDriver buildDriver(String driverName) throws Exception {
		WebDriver driver = null;

		if (BrowserType.firefox.name().equals(driverName)) {
			driver = new FirefoxDriver();
		} else if (BrowserType.ie.name().equals(driverName)) {
			driver = new InternetExplorerDriver();
		} else if (BrowserType.chrome.name().equals(driverName)) {
			driver = new ChromeDriver();
		} else if (driverName.startsWith(BrowserType.remote.name())) {
			String[] params = driverName.split(":");
			Assert.isTrue(params.length == 4,
					"Remote driver is not right, accept format is \"remote:localhost:3000:firefox\", but the input is\""
							+ driverName + "\"");

			String remoteHost = params[1];
			String remotePort = params[2];
			String driverType = params[3];
			DesiredCapabilities cap = null;

			if (BrowserType.firefox.name().equals(driverType)) {
				cap = DesiredCapabilities.firefox();
			} else if (BrowserType.ie.name().equals(driverType)) {
				cap = DesiredCapabilities.internetExplorer();
			} else if (BrowserType.chrome.name().equals(driverType)) {
				cap = DesiredCapabilities.chrome();
			}

			driver = new RemoteWebDriver(new URL("http://" + remoteHost + ":" + remotePort + "/wd"), cap);
		}

		Assert.notNull(driver, "No driver could be found by name:" + driverName);

		return driver;
	}

	/**
	 * 兼容Selnium1.0的常用函数, 判断页面内是否存在文本内容.
	 */
	public static boolean isTextPresent(WebDriver driver, String text) {
		return StringUtils.contains(driver.findElement(By.tagName("body")).getText(), text);
	}

	/**
	 * 兼容Selnium1.0的常用函数, 在element中输入文本内容.
	 */
	public static void type(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * 兼容Selnium1.0的常用函数, 取消element的选择.
	 */
	public static void uncheck(WebElement element) {
		if (element.isSelected()) {
			element.toggle();
		}
	}

	/**
	 * 兼容Selnium1.0的常用函数, 取得单元格的内容, 序列从0开始.
	 */
	public static String getTable(WebElement table, int rowIndex, int columnIndex) {
		return table.findElement(By.xpath("//tr[" + (rowIndex + 1) + "]//td[" + (columnIndex + 1) + "]")).getText();
	}

	/**
	 * 兼容Selnium1.0的常用函数, 等待element的内容展现, timeout单位为毫秒.
	 */
	public static void waitForDisplay(WebElement element, int timeout) {
		long timeoutTime = System.currentTimeMillis() + timeout;
		while (System.currentTimeMillis() < timeoutTime) {
			if (((RenderedWebElement) element).isDisplayed()) {
				return;
			}
		}
		logger.warn("waitForDisplay timeout");
	}
}
