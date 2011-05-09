package org.springside.modules.unit.test.utils;

import static org.junit.Assert.*;

import java.net.MalformedURLException;
import java.net.URL;

import mockit.Mock;
import mockit.MockClass;
import mockit.Mockit;

import org.junit.Test;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springside.modules.test.utils.SeleniumUtils;

public class SeleniumUtilsTest {

	@MockClass(realClass = RemoteWebDriver.class)
	public static class MockRemoteWebDriver {

		@Mock
		public void $init(URL remoteAddress, Capabilities desiredCapabilities) {
			System.out.println("RemoteWebDriver");
			try {
				assertEquals(new URL("http://localhost:3000/wd"), remoteAddress);
			} catch (MalformedURLException e) {
				fail("exception happen");
			}
			assertEquals(DesiredCapabilities.firefox(), desiredCapabilities);
		}
	}

	@MockClass(realClass = FirefoxDriver.class)
	public static class MockFirefoxDriver {
		@Mock
		public void $init() {
			System.out.println("FirefoxDriver");
		}
	}

	@MockClass(realClass = InternetExplorerDriver.class)
	public static class MockInternetExplorerDriver {
		@Mock
		public void $init() {
			System.out.println("InternetExplorerDriver");
		}
	}

	@MockClass(realClass = ChromeDriver.class)
	public static class MockChromeDriver {
		@Mock
		public void $init() {
			System.out.println("ChromeDriver");
		}
	}

	@Test
	public void buildWebDriver() throws Exception {
		Mockit.setUpMocks(MockFirefoxDriver.class, MockInternetExplorerDriver.class, MockChromeDriver.class,
				MockRemoteWebDriver.class);

		WebDriver driver = SeleniumUtils.buildDriver("firefox");
		assertTrue(driver instanceof FirefoxDriver);

		driver = SeleniumUtils.buildDriver("ie");
		assertTrue(driver instanceof InternetExplorerDriver);

		driver = SeleniumUtils.buildDriver("chrome");
		assertTrue(driver instanceof ChromeDriver);

		driver = SeleniumUtils.buildDriver("remote:localhost:3000:firefox");
		assertTrue(driver instanceof RemoteWebDriver);

		Mockit.tearDownMocks();
	}
}
