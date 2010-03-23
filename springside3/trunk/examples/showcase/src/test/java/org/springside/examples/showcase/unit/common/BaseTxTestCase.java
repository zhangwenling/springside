package org.springside.examples.showcase.unit.common;

import org.junit.Before;
import org.junit.Ignore;
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
@Ignore
public class BaseTxTestCase extends SpringTxTestCase {

	@Before
	public void loadDefaultData() throws Exception {
		DBUnitUtils.loadDbUnitData(dataSource, "/data/default-data.xml");
	}
}
