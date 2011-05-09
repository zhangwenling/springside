package org.springside.examples.miniweb.functional;

import java.util.Properties;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springside.examples.miniweb.tools.Start;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.test.utils.DbUnitUtils;
import org.springside.modules.test.utils.JettyUtils;
import org.springside.modules.test.utils.SeleniumUtils;
import org.springside.modules.utils.PropertiesUtils;
import org.springside.modules.utils.spring.SpringContextHolder;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase执行前重新载入默认数据, 创建WebDriver.
 * 
 * @author calvin
 */
@Ignore
@RunWith(GroupsTestRunner.class)
public class BaseFunctionalTestCase {

	//Test Groups define
	public final static String DAILY = "DAILY";
	public final static String NIGHTLY = "NIGHTLY";

	protected final static String BASE_URL = Start.BASE_URL;

	protected static Server jettyServer;

	protected static DataSource dataSource;

	protected static WebDriver driver;

	@BeforeClass
	public static void startAll() throws Exception {
		startJetty();
		loadDefaultData();
		createWebDriver();
		loginAsAdmin();
	}

	@AfterClass
	public static void stopAll() throws Exception {
		stopWebDriver();
		cleanDefaultData();
	}

	/**
	 * 启动Jetty服务器, 仅启动一次.
	 */
	protected static void startJetty() throws Exception {
		if (jettyServer == null) {
			jettyServer = JettyUtils.buildTestServer(Start.PORT, Start.CONTEXT);
			jettyServer.start();
			dataSource = SpringContextHolder.getBean("dataSource");
		}
	}

	/**
	 * 载入默认数据.
	 */
	protected static void loadDefaultData() throws Exception {
		DbUnitUtils.loadData(dataSource, "/data/sample-data.xml");
	}

	/**
	 * 删除默认数据.
	 */
	protected static void cleanDefaultData() throws Exception {
		DbUnitUtils.removeData(dataSource, "/data/sample-data.xml");
	}

	/**
	 * 创建WebDriver.
	 */
	protected static void createWebDriver() throws Exception {
		Properties props = PropertiesUtils.loadProperties("classpath:/application.test.properties",
				"classpath:/application.test-local.properties");

		driver = SeleniumUtils.buildDriver(props.getProperty("selenium.driver"));
	}

	/**
	 * 关闭WebDriver
	 */
	protected static void stopWebDriver() {
		driver.close();
	}

	/**
	 * 登录管理员權限組.
	 */
	protected static void loginAsAdmin() {
		driver.get(BASE_URL + "/login.action?logout");

		driver.findElement(By.name("username")).sendKeys("admin");
		driver.findElement(By.name("password")).sendKeys("admin");
		driver.findElement(By.xpath(Gui.BUTTON_LOGIN)).click();
	}
}