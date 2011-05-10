/**
 * Copyright (c) 2005-2010 springside.org.cn
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * 
 * $Id$
 */
package org.springside.modules.test.functional;

import org.openqa.selenium.By;
import org.openqa.selenium.RenderedWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springside.modules.utils.ThreadUtils;

import com.thoughtworks.selenium.Selenium;

/**
 * Selenium工具类.
 *
 * @author calvin
 */
public class SeleniumHolder {

	public static final int DEFAULT_TIMEOUT = 5000;

	private static Logger logger = LoggerFactory.getLogger(SeleniumHolder.class);
	private WebDriver driver;
	private Selenium selenium;

	public SeleniumHolder(String driverName) {
		this.driver = WebDriverFactory.createDriver(driverName);
		this.selenium = new WebDriverBackedSelenium(driver, "");
	}

	public void open(String url) {
		driver.get(url);
		waitForPageToLoad();
	}

	public String getTitle() {
		return driver.getTitle();
	}

	public WebElement findElement(By by) {
		return driver.findElement(by);
	}

	/**
	 * 在Element中输入文本内容.
	 */
	public void type(By by, String text) {
		WebElement element = driver.findElement(by);
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * 点击Element.
	 */
	public void click(By by) {
		driver.findElement(by).click();
	}

	/**
	 * 点击Element,跳转到新页面.
	 */
	public void clickTo(By by) {
		driver.findElement(by).click();
		waitForPageToLoad();
	}

	/**
	 * 选择Element
	 */
	public void check(By by) {
		WebElement element = driver.findElement(by);
		element.setSelected();
	}

	/**
	 * 取消Element的选择.
	 */
	public void uncheck(By by) {
		WebElement element = driver.findElement(by);
		if (element.isSelected()) {
			element.toggle();
		}
	}

	/**
	 * 判断Element有否被选中.
	 */
	public boolean isChecked(By by) {
		WebElement element = driver.findElement(by);
		return element.isSelected();
	}

	public String getValue(By by) {
		return driver.findElement(by).getValue();
	}

	public String getText(By by) {
		return driver.findElement(by).getText();
	}

	/**
	 * 判断页面内是否存在文本内容.
	 */
	public boolean isTextPresent(String text) {
		return selenium.isTextPresent(text);
	}

	/**
	 * 取得单元格的内容, 序列从0开始, Selnium1.0的常用函数.
	 */
	public String getTable(WebElement table, int rowIndex, int columnIndex) {
		return table.findElement(By.xpath("//tr[" + (rowIndex + 1) + "]//td[" + (columnIndex + 1) + "]")).getText();
	}

	/**
	 * 取得单元格的内容, 序列从0开始, Selnium1.0的常用函数.
	 */
	public String getTable(By by, int rowIndex, int columnIndex) {
		return getTable(driver.findElement(by), rowIndex, columnIndex);
	}

	/**
	 * 等待页面载入完成.
	 */
	public void waitForPageToLoad() {
		waitForPageToLoad(DEFAULT_TIMEOUT);
	}

	/**
	 * 等待页面载入完成, timeout单位为毫秒.
	 */
	public void waitForPageToLoad(int timeout) {
		selenium.waitForPageToLoad(String.valueOf(timeout));
	}

	/**
	 * 等待element的内容展现, timeout单位为毫秒.
	 */
	public void waitForDisplay(By by, int timeout) {
		long timeoutTime = System.currentTimeMillis() + timeout;
		while (System.currentTimeMillis() < timeoutTime) {
			RenderedWebElement element = (RenderedWebElement) driver.findElement(by);
			if (element.isDisplayed()) {
				return;
			}
			ThreadUtils.sleep(250);
		}
		logger.warn("waitForDisplay timeout");
	}

	public void quit() {
		driver.close();
		driver.quit();
	}
}
