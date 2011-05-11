package org.springside.modules.unit.test.data;

import static org.junit.Assert.*;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.data.Fixtures;
import org.springside.modules.test.spring.SpringTxTestCase;

@ContextConfiguration(locations = { "/applicationContext-core-test.xml" })
public class DbUnitUtilsTest extends SpringTxTestCase {

	@Test
	public void normal() throws BeansException, Exception {
		simpleJdbcTemplate.update("drop all objects");

		executeSqlScript("classpath:/schema.sql", false);

		Fixtures.appendData((DataSource) applicationContext.getBean("dataSource"), "classpath:/test-data.xml");
		assertEquals(6, countRowsInTable("SS_USER"));

		Fixtures.loadData((DataSource) applicationContext.getBean("dataSource"), "classpath:/test-data.xml");
		assertEquals(6, countRowsInTable("SS_USER"));

		Fixtures.removeData((DataSource) applicationContext.getBean("dataSource"), "classpath:/test-data.xml");
		assertEquals(0, countRowsInTable("SS_USER"));
	}
}
