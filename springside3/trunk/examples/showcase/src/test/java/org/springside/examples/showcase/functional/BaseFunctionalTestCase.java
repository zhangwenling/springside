package org.springside.examples.showcase.functional;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.mortbay.jetty.Server;
import org.springside.examples.showcase.Start;
import org.springside.modules.test.utils.DBUnitUtils;
import org.springside.modules.test.utils.JettyUtils;
import org.springside.modules.utils.SpringContextHolder;

/**
 * 功能测试基类.
 * 
 * 在整个测试期间启动一次Jetty Server, 并在每个TestCase执行前中重新载入默认数据.
 * 
 * @author calvin
 */
@Ignore
public class BaseFunctionalTestCase extends Assert {

	protected static final String BASE_URL = Start.BASE_URL;

	private static Server server;

	private static DataSource dataSource;

	@BeforeClass
	public static void startJettyAndLoadDefaultData() throws Exception {
		if (server == null) {
			server = JettyUtils.buildServer(Start.PORT, Start.CONTEXT);
			server.start();
			dataSource = SpringContextHolder.getBean("dataSource");
		}

		DBUnitUtils.loadDbUnitData(dataSource, "/data/default-data.xml");
	}
}
