package org.springside.examples.miniweb.unit.dao;

import org.junit.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springside.modules.test.spring.SpringTxTestCase;
import org.springside.modules.test.utils.DBUnitUtils;

/**
 * 数据库访问测试基类。
 * 
 * 继承SpringTxTestCase的所有方法, 并在第一个测试方法前初始化数据.
 * 
 * @see SpringTxTestCase
 * 
 * @author calvin
 */
@ContextConfiguration(locations = { "/applicationContext-test.xml" })
public class BaseTxTestCase extends SpringTxTestCase {

	@Before
	public void loadDefaultDatae() throws Exception {
		DBUnitUtils.loadDbUnitData(dataSource, "/data/default-data.xml");
	}
}
