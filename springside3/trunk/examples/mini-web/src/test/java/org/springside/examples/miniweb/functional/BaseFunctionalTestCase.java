package org.springside.examples.miniweb.functional;

import java.util.Properties;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.mortbay.jetty.Server;
import org.openqa.selenium.WebDriver;
import org.springside.examples.miniweb.tools.Start;
import org.springside.modules.test.groups.GroupsTestRunner;
import org.springside.modules.test.utils.DBUnitUtils;
import org.springside.modules.test.utils.JettyUtils;
import org.springside.modules.test.utils.SeleniumUtils;
import org.springside.modules.utils.PropertyUtils;
import org.springside.modules.utils.SpringContextHolder;

@Ignore
@RunWith(GroupsTestRunner.class)
public class BaseFunctionalTestCase extends Assert {

	protected final static String BASE_URL = Start.BASE_URL;

	protected static Server server;

	protected static DataSource dataSource;

	protected static WebDriver driver;

	@BeforeClass
	public static void startServersAndLoadDefaultData() throws Exception {

		// Start Jetty Server
		if (server == null) {
			server = JettyUtils.buildServer(Start.PORT, Start.CONTEXT);
			server.start();
			dataSource = SpringContextHolder.getBean("dataSource");
		}

		// Load default data
		DBUnitUtils.loadDbUnitData(dataSource, "/data/default-data.xml");

		// Load Testing Properties;
		Properties props = PropertyUtils.loadProperties("application.test.properties",
				"application.test-local.properties");

		driver = SeleniumUtils.buildDriver(props.getProperty("selenium.driver"));

	}

	@AfterClass
	public static void stopWebDriver() {
		driver.close();
	}

}