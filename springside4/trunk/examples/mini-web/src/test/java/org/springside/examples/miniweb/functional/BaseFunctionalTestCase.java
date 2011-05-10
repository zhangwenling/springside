package org.springside.examples.miniweb.functional;

import java.util.Properties;

import javax.sql.DataSource;

import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.springside.examples.miniweb.tools.Start;
import org.springside.modules.test.data.DbUnitUtils;
import org.springside.modules.test.functional.JettyUtils;
import org.springside.modules.test.functional.SeleniumHolder;
import org.springside.modules.test.groups.GroupsTestRunner;
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

	protected static SeleniumHolder selenium;

	@BeforeClass
	public static void startAll() throws Exception {
		startJetty();
		loadDefaultData();
		createSelenium();
		loginAsAdminIfNecessary();
	}

	@AfterClass
	public static void stopAll() throws Exception {
		quitSelenium();
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
	protected static void createSelenium() throws Exception {
		Properties props = PropertiesUtils.loadProperties("classpath:/application.test.properties",
				"classpath:/application.test-local.properties");

		selenium = new SeleniumHolder(props.getProperty("selenium.driver"));
	}

	protected static void quitSelenium() {
		selenium.quit();
	}

	/**
	 * 登录管理员权限组.
	 */
	protected static void loginAsAdminIfNecessary() {
		selenium.open(BASE_URL + "/account/user.action");

		if ("Mini-Web 登录页".equals(selenium.getTitle())) {
			selenium.type(By.name("username"), "admin");
			selenium.type(By.name("password"), "admin");
			selenium.clickTo(By.xpath(Gui.BUTTON_LOGIN));
		}
	}
}