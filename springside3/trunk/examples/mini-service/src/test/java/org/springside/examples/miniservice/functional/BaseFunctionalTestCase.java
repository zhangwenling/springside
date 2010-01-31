package org.springside.examples.miniservice.functional;

import javax.sql.DataSource;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.mortbay.jetty.Server;
import org.springside.modules.spring.SpringContextHolder;
import org.springside.modules.test.utils.DBUnitUtils;
import org.springside.modules.test.utils.JettyUtils;

@Ignore
public class BaseFunctionalTestCase extends Assert {

	private static Server server;

	@BeforeClass
	public static void startJettyAndLoadDefaultData() throws Exception {
		
		if (server == null) {
			server = JettyUtils.buildServer(8080, "/mini-service");
			server.start();
		}
		
		DataSource dataSource = SpringContextHolder.getBean("dataSource");
		DBUnitUtils.loadDbUnitData(dataSource, BaseFunctionalTestCase.class
				.getResourceAsStream("/data/default-data.xml"));

	}
}
