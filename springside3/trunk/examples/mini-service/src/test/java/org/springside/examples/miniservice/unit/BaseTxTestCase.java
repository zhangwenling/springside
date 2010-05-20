package org.springside.examples.miniservice.unit;

import javax.sql.DataSource;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Ignore;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DbUnitUtils;

/**
 * 继承SpringTxTestCase的所有方法, 并负责默认数据加载与删除.
 * 
 * 在第一个测试方法前初始化数据,在所有方法完毕后删除数据.
 * 
 * @see SpringTxTestCase
 * 
 * @author calvin
 */
@Ignore
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class BaseTxTestCase extends SpringTxTestCase {

	private static DataSource dataSourceHolder = null;

	@Before
	public void loadDefaultData() throws Exception {
		if (dataSourceHolder == null) {
			DbUnitUtils.loadData(dataSource, "/data/default-data.xml");
			dataSourceHolder = dataSource;
		}
	}

	@AfterClass
	public static void cleanDefaultData() throws Exception {
		DbUnitUtils.loadData(dataSourceHolder, "/data/default-data.xml");
	}
}
